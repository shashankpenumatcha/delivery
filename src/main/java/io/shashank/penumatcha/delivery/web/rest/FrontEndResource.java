package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.*;
import io.shashank.penumatcha.delivery.repository.CartItemsRepository;
import io.shashank.penumatcha.delivery.repository.CartRepository;
import io.shashank.penumatcha.delivery.repository.ProductRepository;
import io.shashank.penumatcha.delivery.repository.UserProfileRepository;
import io.shashank.penumatcha.delivery.service.FrontEndService;
import io.shashank.penumatcha.delivery.service.UserService;
import io.shashank.penumatcha.delivery.web.rest.dto.CartItemDTO;
import io.shashank.penumatcha.delivery.web.rest.errors.BadRequestAlertException;
import io.shashank.penumatcha.delivery.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * REST controller for managing CartItems.
 */
@RestController
@RequestMapping("/api")
public class FrontEndResource {

    private final Logger log = LoggerFactory.getLogger(CartItemsResource.class);

    private final CartItemsRepository cartItemsRepository;

    private final UserService userService;

    private final UserProfileRepository userProfileRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final FrontEndService frontEndService;

    public FrontEndResource(CartItemsRepository cartItemsRepository,
                            UserService userService, UserProfileRepository userProfileRepository,
                            CartRepository cartRepository, ProductRepository productRepository, FrontEndService frontEndService) {
        this.cartItemsRepository = cartItemsRepository;
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.frontEndService = frontEndService;
    }

    /**
     * POST  /addToCart : Create a new cartItems.
     *
     * @param cartItems the cartItems to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartItems, or with status 400 (Bad Request) if the cartItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addToCart")
    @Timed
    @Transactional
    public ResponseEntity<Cart> createCartItems(@RequestBody CartItemDTO cartItems) throws URISyntaxException {
        log.debug("REST request to add to cart : {}", cartItems);
        Cart cartItemsList = null;
        final MultiValueMap<String, String> error = new HttpHeaders();
        if (cartItems.getId() != null) {
            throw new BadRequestAlertException("A new cartItems cannot already have an ID", "cart items", "idexists");
        }
        log.debug("Getting userprofile : {}", cartItems);
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        if (userProfile == null) {
            log.debug("UserProfile not found: {}");
            error.put("error", Collections.singletonList("User Profile needs to be created before adding to cart"));
            return new ResponseEntity(cartItemsList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cartItems.getProductId() == null) {
            log.debug("please select a product to add to cart: {}");
            error.put("error", Collections.singletonList("please select a product to add to cart"));
            return new ResponseEntity(cartItemsList, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CartItems cartItem = new CartItems();
        log.debug("Fetching product from repository by id: {}", cartItems.getProductId());
        Product product = productRepository.getOne(cartItems.getProductId());
        log.debug("Checking product to add to cart : {}", cartItems.getProductId());
        if (null == product || null == product.getId()) {
            log.debug("product not found: {}", product.getId());
            error.put("error", Collections.singletonList("product not found"));
            return new ResponseEntity(cartItemsList, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cartItems.getQuantity() == null || cartItems.getQuantity() <= 0) {
            log.debug("Please try increasing quantity : {}");
            error.put("error", Collections.singletonList("please send quantity"));
            return new ResponseEntity(cartItemsList, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CartItems existingCartItem = cartItemsRepository.getProductInCart(product.getId());
        Map<String, Object> productValid = null;
        if(null!=existingCartItem){
           cartItem=existingCartItem;
           productValid = frontEndService.checkProductAvailability(Float.sum(cartItems.getQuantity(),existingCartItem.getQuantity()), product);
        }else{
            productValid = frontEndService.checkProductAvailability(cartItems.getQuantity(), product);
        }
        if (productValid != null) {
            if (productValid.containsKey("error")) {
                log.debug((String) productValid.get("error"));
                error.put("error", Collections.singletonList((String) productValid.get("error")));
                return new ResponseEntity(cartItemsList, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (productValid.containsKey("quantity")) {
                log.debug("setting quantity to the product to add to cart");
                cartItem.setQuantity((Float) productValid.get("quantity"));
            }
        }
        cartItem.setProduct(product);
        log.debug("getting cart for login: {}", userProfile.getId());
        Cart cart = cartRepository.findByLogin(userProfile.getId());
        if (cart != null) {
            log.debug("found cart for login: {}", userProfile.getId());
            cartItem.setCart(cart);
        } else {
            log.debug("creating a cart for login: {}", userProfile.getId());
            cart = new Cart();
            cart.setLastUpdated(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            cart.setUserProfile(userProfile);
        }
        log.debug("saving cart for login: {}", userProfile.getId());
        cart = cartRepository.save(cart);
        if (cart != null) {
            log.debug("cart created for login: {}", userProfile.getId());

            cartItem.setCart(cart);
        } else {
            log.debug("couldn't create cart: {}", userProfile.getId());
            error.put("error", Collections.singletonList("error while creating cart{}"));
            return new ResponseEntity(cartItemsList, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CartItems result = cartItemsRepository.save(cartItem);

        if (result != null) {
            cartItemsList=cartItemsRepository.getCartItemsForUser(userProfile.getId());
            log.debug("item added to card : {}", userProfile.getId());
        } else {
            log.debug("couldn't add product to cart: {}", userProfile.getId());
            error.put("error", Collections.singletonList("error while adding to cart"));
            return new ResponseEntity(cartItemsList, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(cartItemsList, HttpStatus.CREATED);
    }

    /**
     * PUT  /products : Updates an existing product.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartItem,
     * or with status 400 (Bad Request) if the product is not valid,
     * or with status 500 (Internal Server Error) if the product couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/updateCart")
    @Timed
    @Transactional
    public ResponseEntity<CartItemDTO> updateCart(@RequestBody CartItemDTO cartItemDTO) throws URISyntaxException {
        log.debug("REST request to update cart : {}", cartItemDTO);
        final MultiValueMap<String, String> error = new HttpHeaders();
        if (cartItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "cart-items", "idnull");
        }
        if (cartItemDTO.getProductId() != null || cartItemDTO.getCartId() != null) {
            error.put("error", Collections.singletonList("only quantity can be changed"));
            return new ResponseEntity<CartItemDTO>(cartItemDTO, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cartItemDTO.getQuantity() == null || cartItemDTO.getQuantity() <= 0) {
            error.put("error", Collections.singletonList("please pass quantity"));
            return new ResponseEntity<CartItemDTO>(cartItemDTO, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CartItems oldCartItem = cartItemsRepository.getOne(cartItemDTO.getId());
        if (null == oldCartItem) {
            error.put("error", Collections.singletonList("product isnt in your cart anymore"));
            return new ResponseEntity<CartItemDTO>(cartItemDTO, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Product product = oldCartItem.getProduct();
        if (product == null) {
            error.put("error", Collections.singletonList("product is null"));
            return new ResponseEntity<CartItemDTO>(cartItemDTO, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.debug("Checking if product is available in inventory  for: {}");
        Map<String, Object> productValid = frontEndService.checkProductAvailability(cartItemDTO.getQuantity(), product);
        if (productValid != null) {
            if (productValid.containsKey("error")) {
                log.debug((String) productValid.get("error"));
                error.put("error", Collections.singletonList((String) productValid.get("error")));
                return new ResponseEntity<CartItemDTO>(cartItemDTO, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (productValid.containsKey("quantity")) {
                log.debug("setting quantity to the product to add to cart");
                oldCartItem.setQuantity((Float) productValid.get("quantity"));
            }
        }
        CartItems result = cartItemsRepository.save(oldCartItem);
        if (result != null) {
            return new ResponseEntity<CartItemDTO>(cartItemDTO, HttpStatus.OK);
        }
        error.put("error", Collections.singletonList("error while updating the cart"));
        return new ResponseEntity<CartItemDTO>(cartItemDTO, error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * GET  /products : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/activeProducts")
    @Timed
    public List<Product> getActiveProducts() {
        log.debug("REST request to get all Products");
        return productRepository.getActiveProducts();
    }


    /**
     * DELETE  /clearCart/:id : delete the "id" cart.
     *
     * @param id the id of the cart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clearCart/{id}")
    @Timed
    public ResponseEntity<Void> clearCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cartitems", id.toString())).build();
    }

    /**
     * DELETE  /removeFromCart/:ids : delete the "id" cartItems.
     *
     * @param ids the id of the cartItems to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/removeFromCart/{ids}")
    @Timed
    public ResponseEntity<Void> removeFromCart(@PathVariable List<Long> ids) {
        log.debug("REST request to delete CartItems : {}", ids);
        for(Long id:ids){
            cartItemsRepository.deleteById(id);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cartitem", ids.toString())).build();
    }

}
