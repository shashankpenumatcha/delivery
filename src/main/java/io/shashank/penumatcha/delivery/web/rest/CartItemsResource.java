package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.CartItems;
import io.shashank.penumatcha.delivery.repository.CartItemsRepository;
import io.shashank.penumatcha.delivery.web.rest.errors.BadRequestAlertException;
import io.shashank.penumatcha.delivery.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CartItems.
 */
@RestController
@RequestMapping("/api")
public class CartItemsResource {

    private final Logger log = LoggerFactory.getLogger(CartItemsResource.class);

    private static final String ENTITY_NAME = "cartItems";

    private final CartItemsRepository cartItemsRepository;

    public CartItemsResource(CartItemsRepository cartItemsRepository) {
        this.cartItemsRepository = cartItemsRepository;
    }

    /**
     * POST  /cart-items : Create a new cartItems.
     *
     * @param cartItems the cartItems to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartItems, or with status 400 (Bad Request) if the cartItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cart-items")
    @Timed
    public ResponseEntity<CartItems> createCartItems(@RequestBody CartItems cartItems) throws URISyntaxException {
        log.debug("REST request to save CartItems : {}", cartItems);
        if (cartItems.getId() != null) {
            throw new BadRequestAlertException("A new cartItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartItems result = cartItemsRepository.save(cartItems);
        return ResponseEntity.created(new URI("/api/cart-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cart-items : Updates an existing cartItems.
     *
     * @param cartItems the cartItems to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartItems,
     * or with status 400 (Bad Request) if the cartItems is not valid,
     * or with status 500 (Internal Server Error) if the cartItems couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cart-items")
    @Timed
    public ResponseEntity<CartItems> updateCartItems(@RequestBody CartItems cartItems) throws URISyntaxException {
        log.debug("REST request to update CartItems : {}", cartItems);
        if (cartItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartItems result = cartItemsRepository.save(cartItems);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cartItems.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cart-items : get all the cartItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cartItems in body
     */
    @GetMapping("/cart-items")
    @Timed
    public List<CartItems> getAllCartItems() {
        log.debug("REST request to get all CartItems");
        return cartItemsRepository.findAll();
    }

    /**
     * GET  /cart-items/:id : get the "id" cartItems.
     *
     * @param id the id of the cartItems to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartItems, or with status 404 (Not Found)
     */
    @GetMapping("/cart-items/{id}")
    @Timed
    public ResponseEntity<CartItems> getCartItems(@PathVariable Long id) {
        log.debug("REST request to get CartItems : {}", id);
        Optional<CartItems> cartItems = cartItemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cartItems);
    }

    /**
     * DELETE  /cart-items/:id : delete the "id" cartItems.
     *
     * @param id the id of the cartItems to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cart-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteCartItems(@PathVariable Long id) {
        log.debug("REST request to delete CartItems : {}", id);

        cartItemsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
