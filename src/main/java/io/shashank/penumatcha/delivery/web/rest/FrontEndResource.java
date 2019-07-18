package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.*;
import io.shashank.penumatcha.delivery.elastic.InventoryLogDocument;
import io.shashank.penumatcha.delivery.repository.*;
import io.shashank.penumatcha.delivery.service.FrontEndService;
import io.shashank.penumatcha.delivery.service.UserService;
import io.shashank.penumatcha.delivery.web.rest.dto.CartItemDTO;
import io.shashank.penumatcha.delivery.web.rest.dto.ReportDTO;
import io.shashank.penumatcha.delivery.web.rest.errors.BadRequestAlertException;
import io.shashank.penumatcha.delivery.web.rest.util.HeaderUtil;
import io.shashank.penumatcha.delivery.web.websocket.ActivityService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.net.URISyntaxException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * REST controller for managing front end api's.
 * use ProductResource for admin product screens
 * user OrderListResource for admin order(get by id and post) screens
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

    private final InventoryLogRepository inventoryLogRepository;

    private final OrderStatusRepository orderStatusRepository;

    private final OrderListRepository orderRepository;

    private final OrderItemsRepository orderItemsRepository;

    private final OrderTrackerRepository orderTrackerRepository;

    private  final UserAddressRepository userAddressRepository;

    private  final ActivityService activityService;

    private final BacklogRepository backlogRepository;

    //private final InventoryLogSearchRepository inventoryLogSearchRepository;


    public FrontEndResource(CartItemsRepository cartItemsRepository,
                            UserService userService, UserProfileRepository userProfileRepository,
                            CartRepository cartRepository, ProductRepository productRepository, FrontEndService frontEndService,
                            InventoryLogRepository inventoryLogRepository, OrderStatusRepository orderStatusRepository, OrderListRepository orderRepository,
                            OrderItemsRepository orderItemsRepository,OrderTrackerRepository orderTrackerRepository,
                            UserAddressRepository userAddressRepository, ActivityService activityService
                           ,BacklogRepository backlogRepository
                            ) {
        this.cartItemsRepository = cartItemsRepository;
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.frontEndService = frontEndService;
        this.inventoryLogRepository = inventoryLogRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.orderTrackerRepository = orderTrackerRepository;
        this.userAddressRepository=userAddressRepository;
        this.activityService = activityService;
        this.backlogRepository =backlogRepository;
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
        Cart cart = null;
        final MultiValueMap<String, String> error = new HttpHeaders();
        if (cartItems.getId() != null) {
            throw new BadRequestAlertException("A new cartItems cannot already have an ID", "cart items", "idexists");
        }
        log.debug("Getting userprofile : {}", cartItems);
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        if (userProfile == null) {
            log.debug("UserProfile not found: {}");
            error.put("error", Collections.singletonList("User Profile needs to be created before adding to cart"));
            return new ResponseEntity(cart, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cartItems.getProductId() == null) {
            log.debug("please select a product to add to cart: {}");
            error.put("error", Collections.singletonList("please select a product to add to cart"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CartItems cartItem = new CartItems();
        log.debug("Fetching product from repository by id: {}", cartItems.getProductId());
        Product product = productRepository.getOne(cartItems.getProductId());
        log.debug(product.getQuantity().toString());
        log.debug("Checking product to add to cart : {}", cartItems.getProductId());
        if (null == product || null == product.getId()) {
            log.debug("product not found: {}", product.getId());
            error.put("error", Collections.singletonList("product not found"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cartItems.getQuantity() == null || cartItems.getQuantity() <= 0) {
            log.debug("Please try increasing quantity : {}");
            error.put("error", Collections.singletonList("please send quantity"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CartItems existingCartItem = cartItemsRepository.getProductInCart(product.getId(),userProfile.getId());
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
                return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (productValid.containsKey("quantity")) {
                log.debug("setting quantity to the product to add to cart");
                cartItem.setQuantity((Float) productValid.get("quantity"));
            }
        }
        cartItem.setProduct(product);
        log.debug("getting cart for login: {}", userProfile.getId());
        cart = cartRepository.findByLogin(userProfile.getId());
        if (cart != null) {
            log.debug("found cart for login: {}", userProfile.getId());
            cartItem.setCart(cart);
        } else {
            log.debug("creating a cart for login: {}", userProfile.getId());
            cart = new Cart();
            cart.setUserProfile(userProfile);
            cart.setLastUpdated(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            log.debug("saving cart for login: {}", userProfile.getId());
            cart = cartRepository.save(cart);
        }

        if (cart != null) {
            log.debug("cart created for login: {}", userProfile.getId());
            cartItem.setCart(cart);
        } else {
            log.debug("couldn't create cart: {}", userProfile.getId());
            error.put("error", Collections.singletonList("error while creating cart{}"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CartItems result = cartItemsRepository.save(cartItem);

        if (result != null && cart!=null) {
            Set<CartItems> returnCartItems = cartItemsRepository.getCartItemsForUser(userProfile.getId());
            cart.setLastUpdated(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            cart.setCartItems(returnCartItems);
            cart = cartRepository.save(cart);
            log.debug("item added to card : {}", userProfile.getId());
        } else {
            log.debug("couldn't add product to cart: {}", userProfile.getId());
            error.put("error", Collections.singletonList("error while adding to cart"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(cart, HttpStatus.CREATED);
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
    public ResponseEntity<Cart> updateCart(@RequestBody CartItemDTO cartItemDTO) throws URISyntaxException {
        log.debug("REST request to update cart : {}", cartItemDTO);
        final MultiValueMap<String, String> error = new HttpHeaders();
        Cart cart = null;
        if (cartItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "cart-items", "idnull");
        }
        if (cartItemDTO.getProductId() != null || cartItemDTO.getCartId() != null) {
            error.put("error", Collections.singletonList("only quantity can be changed"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cartItemDTO.getQuantity() == null || cartItemDTO.getQuantity() < 0) {
            error.put("error", Collections.singletonList("please pass quantity"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CartItems oldCartItem = cartItemsRepository.getOne(cartItemDTO.getId());
        if (null == oldCartItem) {
            error.put("error", Collections.singletonList("product isnt in your cart anymore"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Product product = oldCartItem.getProduct();
        if (product == null) {
            error.put("error", Collections.singletonList("product is null"));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.debug("Checking if product is available in inventory  for: {}");
        if(cartItemDTO.getQuantity()<product.getMinimumQuantity()){
            cartItemsRepository.deleteById(cartItemDTO.getId());
        }else {
            Map<String, Object> productValid = frontEndService.checkProductAvailability(cartItemDTO.getQuantity(), product);
            if (productValid != null) {
                if (productValid.containsKey("error")) {
                    log.debug((String) productValid.get("error"));
                    error.put("error", Collections.singletonList((String) productValid.get("error")));
                    return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                if (productValid.containsKey("quantity")) {
                    log.debug("setting quantity to the product to add to cart");
                    oldCartItem.setQuantity((Float) productValid.get("quantity"));
                }
                CartItems result = cartItemsRepository.save(oldCartItem);
            }
        }
        Set<CartItems> returnCartItems = cartItemsRepository.getCartItemsByCart(oldCartItem.getCart().getId());
        cart=oldCartItem.getCart();
        cart.setLastUpdated(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
        cart.setCartItems(returnCartItems);
        try{
            cart = cartRepository.save(cart);
        }catch(Exception e){
            error.put("error", Collections.singletonList("error while updating the cart, exception is ::"+e.getMessage()));
            return new ResponseEntity(cart, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(cart, HttpStatus.OK);
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
    public ResponseEntity<Cart> removeFromCart(@PathVariable List<Long> ids) {
        log.debug("REST request to delete CartItems : {}", ids);
        Cart cart = null;
        for(Long id:ids){
            Optional<CartItems> cartItem = cartItemsRepository.findById(id);
            if(cartItem.isPresent()){
                cartItemsRepository.deleteById(id);
                Set<CartItems> returnCartItems = cartItemsRepository.getCartItemsByCart(cartItem.get().getId());
                cart=cartItem.get().getCart();
                cart.setLastUpdated(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
                cart.setCartItems(returnCartItems);
            }
        }
        return new ResponseEntity(cart, HttpStatus.OK);
    }


    /**
     * GET  /getCartForUser : get cart for user
     *

     */
    @GetMapping("/getCartForUser")
    @Timed
    public ResponseEntity<Cart> getCartForUser() {
        log.debug("REST request to get  Cart for User");
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        Cart cart = null;
        cart = cartItemsRepository.getCartForUser(userProfile.getId());
        if(cart==null){
            final MultiValueMap<String, String> error = new HttpHeaders();
            error.put("error", Collections.singletonList("error while updating the cart, exception is ::"));
            return new ResponseEntity(null, HttpStatus.OK);
        }
        return new ResponseEntity(cart, HttpStatus.OK);
    }


    /**
     * POST  /order : place order for existing user from cart.
     *

     */
    @Transactional
    @PostMapping("/placeOrder")
    @Timed
    public ResponseEntity<String> placeOrder(@RequestParam(value = "address") Long address) {
        Float total = 0.0F;
        final MultiValueMap<String, String> error = new HttpHeaders();
        log.info("REST request to get  Cart for User");
        if(address == null){
            error.put("error", Collections.singletonList("order failed, please send an order ::"));
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("getting user address");
        UserAddress userAddress = userAddressRepository.getOne(address);
        if(userAddress == null){
            log.info("error while getting user address");
            error.put("error", Collections.singletonList("order failed, there was a problem with your address ::"));
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("getting cart for user");
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        Cart cart = null;
        cart = cartItemsRepository.getCartForUser(userProfile.getId());
        if(cart==null){
            log.info("error while getting cart for user");
            error.put("error", Collections.singletonList("error while updating the cart, exception is ::"));
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cart.getCartItems()==null){
            log.info("error h=while getting cart items for user");
            error.put("error", Collections.singletonList("couldn't place order as cart is empty"));
            return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("preparing to place order");
        List<InventoryLog> inventoryLogs = new ArrayList<InventoryLog>();
        List<InventoryLogDocument> inventoryLogDocuments = new ArrayList<InventoryLogDocument>();
        Set<OrderItems> orderItems = new HashSet<OrderItems>();
        OrderList order = new OrderList();
        log.info("verifying cart items for user");
        for(CartItems cartItem : cart.getCartItems()){
            if(cartItem == null || cartItem.getProduct() ==null){
                log.info("cart item is missing");
                error.put("error", Collections.singletonList("something wrong with the cart"));
                return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(cartItem.getProduct().getQuantity()==null){
                log.info("quantity not set for a product in cart");
                error.put("error", Collections.singletonList("quantity not set for product: " + cartItem.getProduct().getId()));
                return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, Object> productValid = null;
            productValid = frontEndService.checkProductAvailability(cartItem.getQuantity(), cartItem.getProduct());
            if (productValid.containsKey("error")) {
                log.debug((String) productValid.get("error"));
                error.put("error", Collections.singletonList((String) productValid.get("error")));
                return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            OrderItems orderItem = new OrderItems();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity().longValue());
            orderItem.setOrderList(order);
            orderItem.setPrice(cartItem.getProduct().getPricePerUnit().longValue());
            total += orderItem.getPrice()*orderItem.getQuantity();
            orderItems.add(orderItem);
        }
        if(cart.getCartItems().size() == orderItems.size()) {
            log.info("api/placeOrder","creating an order");
            order.setUserAddress(userAddress.getAddress());
                order.setLastUpdated(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
                order.setOrderItems(orderItems);
                order.setOrderStatus(orderStatusRepository.findOneByName("Received"));
                order.setUserProfile(userProfile);
                order.setCreatedDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
                order.setTotal(total);
                order = orderRepository.save(order);
                if (order == null) {
                    log.info("error while creating order");
                    error.put("error", Collections.singletonList("error while placing the order"));
                    return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                log.info("order created, updating products");
            for (OrderItems orderItem : orderItems) {
                orderItem.setOrderList(order);
                orderItem = orderItemsRepository.save(orderItem);
                if (orderItem == null) {
                    error.put("error", Collections.singletonList("error while saving order item"));
                    return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                orderItem.getProduct().setQuantity(orderItem.getProduct().getQuantity()-orderItem.getQuantity().floatValue());
                Product product = productRepository.save(orderItem.getProduct());
                if (product == null) {
                    error.put("error", Collections.singletonList("error while saving product quantity"));
                    return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                InventoryLog inventoryLog = new InventoryLog();
                inventoryLog.setDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
                inventoryLog.setProduct(product);
                inventoryLog.setUserProfile(userProfile);
                inventoryLog.setRemoved(true);
                inventoryLog.setQuantity(orderItem.getQuantity().floatValue());
                inventoryLog.setOrderList(order);
                inventoryLog.setCurrentTotal(product.getQuantity());
                inventoryLogs.add(inventoryLog);
            }
            log.info("updating inventory logs");
            for(InventoryLog i : inventoryLogs){
                inventoryLogRepository.save(i);
            }
            if (inventoryLogs == null) {
                error.put("error", Collections.singletonList("error while saving inventory logs"));
                return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info("updating order tracker");
            OrderTracker orderTracker = new OrderTracker();
            orderTracker.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            orderTracker.setOrderList(order);
            orderTracker.setOrderStatus(order.getOrderStatus());
            orderTracker.setUserProfile(userProfile);
            orderTracker = orderTrackerRepository.save(orderTracker);
            if (orderTracker == null) {
                error.put("error", Collections.singletonList("error while saving order tracker"));
                return new ResponseEntity(null, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info("api/placeOrder","clearing user cart");
            cartRepository.delete(cart);
        }
        log.info("sending push notifications to user and admin");
        try {
            HttpHeaders headers = this.frontEndService.makeHeaders(true);
            String notificationKey = this.frontEndService.getFcmGroupToken(headers,order.getUserProfile().getId());
            if(notificationKey!=null){
                log.debug(order.getOrderStatus().toString());
                this.frontEndService.sendFCM("Order# "+ order.getId().toString(),
                    "your order is " + order.getOrderStatus().getName().toLowerCase(),
                    notificationKey);
                this.frontEndService.sendFCM("Order# "+ order.getId().toString(),
                    "New order received",
                    "/topics/orders");
            }
        }catch(Error e){
            log.debug(">>>>>>>>>>>>>>>error while sending notification");
        }
        log.info("sending in-app notifications to user and admin");
        this.activityService.sendNewOrder(order);
        this.activityService.sendStatus(userProfile.getId(),order);
        log.info("order placed succesfully");
        error.put("message", Collections.singletonList("order Placed succesfully"));
        return new ResponseEntity(error, HttpStatus.OK);
    }




    /**
     * GET  /orders/active : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/user/orders/active")
    @Timed
    public Page<OrderList> getActiveOrdersForUser(Pageable pageRequest) {
        log.debug("REST request to get active orders");
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        List<Long> orderStatuses = new ArrayList<Long>();
        OrderStatus received = orderStatusRepository.findOneByName("Received");
        OrderStatus confirmed = orderStatusRepository.findOneByName("Confirmed");
        OrderStatus dispatched = orderStatusRepository.findOneByName("Dispatched");
        orderStatuses.add(received.getId());
        orderStatuses.add(confirmed.getId());
        orderStatuses.add(dispatched.getId());
        return orderRepository.getActiveOrPastOrdersForUser(pageRequest,userProfile.getId(),orderStatuses);

    }


    /**
     * GET  /orders/previous : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/user/orders/previous")
    @Timed
    public Page<OrderList> getPreviousOrdersForUser(Pageable pageRequest) {
        log.debug("REST request to get active orders");
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        List<Long> orderStatuses = new ArrayList<Long>();
        OrderStatus cancelled = orderStatusRepository.findOneByName("Cancelled");
        OrderStatus delivered = orderStatusRepository.findOneByName("Delivered");

        orderStatuses.add(cancelled.getId());
        orderStatuses.add(delivered.getId());

        return orderRepository.getActiveOrPastOrdersForUser(pageRequest,userProfile.getId(),orderStatuses);

    }


    /**
     * GET  dashboard/orders/filter : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/dashboard/orders/filter")
    @Timed
    public ResponseEntity getOrdersByStatus(@RequestParam(value="status") String status, Pageable pageRequest) {
        log.debug("REST request to get active orders");
        final MultiValueMap<String, String> error = new HttpHeaders();
        if(status == null){
            error.put("error", Collections.singletonList("please send order status"));
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        OrderStatus orderStatus = orderStatusRepository.findOneByName(status);
        if (orderStatus == null) {
            error.put("error", Collections.singletonList("please send a valid order status"));
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(orderRepository.getOrdersByType(pageRequest,orderStatus.getId()), HttpStatus.OK);

    }





    /**
     * GET  /dashboard/orders/active/count : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/dashboard/orders/active/count")
    @Timed
    public Long getActiveOrdersCount() {
        log.debug("REST request to get active orders count");
        List<Long> orderStatuses = new ArrayList<Long>();
        OrderStatus received = orderStatusRepository.findOneByName("Received");
        OrderStatus confirmed = orderStatusRepository.findOneByName("Confirmed");
        OrderStatus dispatched = orderStatusRepository.findOneByName("Dispatched");
        orderStatuses.add(received.getId());
        orderStatuses.add(confirmed.getId());
        orderStatuses.add(dispatched.getId());
        return orderRepository.getActiveOrdersCount(orderStatuses);

    }





    /**
     * GET  /dashboard/orders/count : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/dashboard/orders/count")
    @Timed
    public Map<String, Long> getOrdersCount() {
        log.debug("REST request to get active orders count");
        Map<String, Long> orderStatusMap = new HashMap<String, Long>();
        orderStatusMap.put("received",orderRepository.getOrdersCount("RECEIVED"));
        orderStatusMap.put("confirmed",orderRepository.getOrdersCount("CONFIRMED"));
        orderStatusMap.put("dispatched",orderRepository.getOrdersCount("DISPATCHED"));
        orderStatusMap.put("delivered",orderRepository.getOrdersCount("DELIVERED"));
        orderStatusMap.put("cancelled",orderRepository.getOrdersCount("CANCELLED"));
        return orderStatusMap;




    }



    /**
     * GET  /dashboard/order/track : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/order/track/{id}")
    @Timed
    public ResponseEntity<List<OrderTracker>> trackOrder(@PathVariable(value="id") Long id) {
        log.debug("REST request to track order");
        final MultiValueMap<String, String> error = new HttpHeaders();
        if(id == null){
            error.put("error", Collections.singletonList("please send order id"));
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<OrderTracker> orderTrackers = orderTrackerRepository.trackOrder(id);
        return new ResponseEntity<List<OrderTracker>>(orderTrackers,HttpStatus.OK);

    }





    @PostMapping("/user-address")
    @Timed
    public ResponseEntity<UserAddress> createUserAddress(@RequestBody UserAddress userAddress) throws URISyntaxException {
        log.debug("REST request to save userAddress : {}", userAddress);
        if (userAddress.getId() != null) {
            throw new BadRequestAlertException("A new orderStatus cannot already have an ID", "user address", "idexists");
        }
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        userAddress.setUserProfile(userProfile);
        UserAddress result = userAddressRepository.save(userAddress);
        return ResponseEntity.created(new URI("/api/user-address/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("user address", result.getId().toString()))
            .body(result);
    }


    @PutMapping("/user-address")
    @Timed
    public ResponseEntity<UserAddress> updateUseraddress(@RequestBody UserAddress userAddress) throws URISyntaxException {
        log.debug("REST request to update userAddress : {}", userAddress);
        if (userAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "user address", "idnull");
        }
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        userAddress.setUserProfile(userProfile);
        UserAddress result = userAddressRepository.save(userAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("user address", result.getId().toString()))
            .body(result);
    }


    @GetMapping("/user/addresses")
    @Timed
    public List<UserAddress> getAllUserAddresses() {
        log.debug("REST request to get all Useraddresses");
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        return userAddressRepository.findAllByUserProfile(userProfile.getId());
    }


    @DeleteMapping("/user-address/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long id) {
        log.debug("REST request to delete UserAddress : {}", id);
        userAddressRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("user address", id.toString())).build();
    }


    @Transactional
    @PostMapping("/fcmToken/update")
    @Timed
    public ResponseEntity<Map<String,String>> updateToken(@RequestParam(value = "token") String token) {
        String result = frontEndService.updateFcmToken(token);
        Map<String,String> response =  new HashMap<String,String>();
        if(result == null){
            response.put("error","error updating token, please check logs");
            return new ResponseEntity<Map<String,String>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success","token updated");

        return new ResponseEntity<Map<String,String>>(response,HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/fcmToken/delete")
    @Timed
    public ResponseEntity<Map<String,String>> removeToken(@RequestParam(value = "token") String token) {
        String result = frontEndService.deleteToken(token);
        Map<String,String> response =  new HashMap<String,String>();
        if(result == null){
            response.put("error","error while deleting token, please check logs");
            return new ResponseEntity<Map<String,String>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success","token deleted");
        return new ResponseEntity<Map<String,String>>(response,HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/userProfile/current")
    @Timed
    public ResponseEntity<UserProfile> getCurrentUser(){
        UserProfile userProfile = this.frontEndService.getCurrentUserProfile();
        return new ResponseEntity<UserProfile>(userProfile,HttpStatus.OK);
    }



    @Transactional
    @GetMapping("/fcm/topic/orders")
    @Timed
    public ResponseEntity<String> subscribeToOrders(@RequestParam(value="token") String token){
        String result = this.frontEndService.subscribeToOrders(true,token);
        if(result == null){
            return new ResponseEntity<String>("error while registering to topics",HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<String>("registered to topics",HttpStatus.OK);
    }



    @Transactional
    @DeleteMapping("/fcm/topic/orders")
    @Timed
    public ResponseEntity<String> unSubscribeToOrders(@RequestParam(value="token") String token){
        if(token == null){
            return new ResponseEntity<String>("please send a token",HttpStatus.INTERNAL_SERVER_ERROR);

        }

        String result = this.frontEndService.subscribeToOrders(false,token);
        if(result == null){
            return new ResponseEntity<String>("error while registering to topics",HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<String>("registered to topics",HttpStatus.OK);
    }

    @GetMapping("/dashboard/report")
    @Timed
    public ResponseEntity<Page<InventoryLog>> getReport(Pageable page,@RequestParam("f") String f, @RequestParam("t") String t){
//join product and orderlist and order status
        try{
            DateTimeFormatter formatter =  DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            ZonedDateTime from =  ZonedDateTime.parse(f,formatter.withZone(ZoneId.of("Asia/Kolkata")));
            ZonedDateTime to =  ZonedDateTime.parse(t,formatter.withZone(ZoneId.of("Asia/Kolkata")));
            Page<InventoryLog> inventoryLogs = this.inventoryLogRepository.getReport(page,from,to);
            return new ResponseEntity(inventoryLogs,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            log.debug("error while parsing data");
        }
        return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @PostMapping("/dashboard/backlog")
    @Timed
    @Transactional
    public ResponseEntity createBacklog(@RequestParam(value="id") Long inventoryLogId) {
        log.info("/dashboard/backlog","request to create backlog");
        if(inventoryLogId == null) {
            log.info("input: inventorylog id is null");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        InventoryLog inventoryLog = this.inventoryLogRepository.findById(inventoryLogId).get();
        if(inventoryLogId == null) {
            log.info( "inventorylog  is null");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(!inventoryLog.isAdded()){
            log.info( "backlog can only added for inventoryLog added items");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Float existingBacklog = this.backlogRepository.getByProductId(inventoryLog.getProduct().getId());
        log.info("rtjjjjjjjjjjjjjjj" + existingBacklog);
        log.info("rtjjjjjjjjjjjjjjj" + inventoryLog.getProduct().getQuantity());

        if(inventoryLog.getProduct()==null || inventoryLog.getProduct().getQuantity()==null || (existingBacklog!=null && existingBacklog+inventoryLog.getQuantity()>inventoryLog.getProduct().getQuantity())){
            log.info( "product doesnt have quantitfy or you are trying to set more backlog than stock");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Backlog backlog = new Backlog();
        backlog.setDate(inventoryLog.getDate());
        backlog.setInventoryLog(inventoryLog);
        backlog.setQuantity(inventoryLog.getQuantity());
        backlog = this.backlogRepository.save(backlog);
        if(backlog == null) {
            log.info( "error while saving backlog");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        inventoryLog.setBacklog(backlog);
        log.info( "updating inventory log with backlog id and saving inventory log");
        inventoryLog = this.inventoryLogRepository.save(inventoryLog);
        if(inventoryLog == null) {
            log.info( "error while saving inventory log ");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info( "successfully saved backlog");
        return new ResponseEntity(backlog,HttpStatus.OK);
    }

    @GetMapping("/vendor/products")
    @Timed
    @Transactional
    public ResponseEntity getVendorProducts(@RequestParam(value="id") Long vendorId) {
        List<Product> products = productRepository.getActiveProductsByVendor(vendorId);
        return new ResponseEntity(products,HttpStatus.OK);
    }
}
