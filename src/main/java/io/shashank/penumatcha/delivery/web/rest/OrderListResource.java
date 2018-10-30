package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.*;
import io.shashank.penumatcha.delivery.repository.InventoryLogRepository;
import io.shashank.penumatcha.delivery.repository.OrderListRepository;
import io.shashank.penumatcha.delivery.repository.OrderTrackerRepository;
import io.shashank.penumatcha.delivery.repository.ProductRepository;
import io.shashank.penumatcha.delivery.service.FrontEndService;
import io.shashank.penumatcha.delivery.web.rest.errors.BadRequestAlertException;
import io.shashank.penumatcha.delivery.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderList.
 */
@RestController
@RequestMapping("/api")
public class OrderListResource {

    private final Logger log = LoggerFactory.getLogger(OrderListResource.class);

    private static final String ENTITY_NAME = "orderList";

    private final OrderListRepository orderListRepository;

    private final FrontEndService frontEndService;

    private final OrderTrackerRepository orderTrackerRepository;

    private final ProductRepository productRepository;

    private  final InventoryLogRepository inventoryLogRepository;

    public OrderListResource(OrderListRepository orderListRepository,FrontEndService frontEndService,
                             OrderTrackerRepository orderTrackerRepository, ProductRepository productRepository,
                             InventoryLogRepository inventoryLogRepository) {
        this.orderListRepository = orderListRepository;
        this.frontEndService = frontEndService;
        this.orderTrackerRepository = orderTrackerRepository;
        this.productRepository = productRepository;
        this.inventoryLogRepository = inventoryLogRepository;
    }

    /**
     * POST  /order-lists : Create a new orderList.
     *
     * @param orderList the orderList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderList, or with status 400 (Bad Request) if the orderList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-lists")
    @Timed
    public ResponseEntity<OrderList> createOrderList(@RequestBody OrderList orderList) throws URISyntaxException {
        log.debug("REST request to save OrderList : {}", orderList);
        if (orderList.getId() != null) {
            throw new BadRequestAlertException("A new orderList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderList result = orderListRepository.save(orderList);
        return ResponseEntity.created(new URI("/api/order-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-lists : Updates an existing orderList.
     *
     * @param orderList the orderList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderList,
     * or with status 400 (Bad Request) if the orderList is not valid,
     * or with status 500 (Internal Server Error) if the orderList couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Transactional
    @PutMapping("/order-lists")
    @Timed
    public ResponseEntity<OrderList> updateOrderList(@RequestBody OrderList orderList) throws URISyntaxException {
        log.debug("REST request to update OrderList : {}", orderList);
        if (orderList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserProfile userProfile = frontEndService.getCurrentUserProfile();
        if(userProfile == null){
            throw new BadRequestAlertException("Invalid userprofile", ENTITY_NAME, "user proflile doesnt exist");

        }
        OrderList oldOrder = orderListRepository.findById(orderList.getId()).get();
        OrderList result = orderListRepository.save(orderList);
        if(oldOrder!=null && oldOrder.getOrderStatus()!=null && oldOrder.getOrderStatus().getId() != orderList.getOrderStatus().getId()){
            OrderTracker orderTracker = new OrderTracker();
            orderTracker.setUserProfile(userProfile);
            orderTracker.setOrderStatus(orderList.getOrderStatus());
            orderTracker.setOrderList(orderList);
            orderTracker.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            orderTracker =  orderTrackerRepository.save(orderTracker);
            log.debug("rtjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
        }
        for (OrderItems orderItem : orderList.getOrderItems()){
            log.debug("rtjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj2"+orderItem.getProduct().toString());

            Product oldProduct = productRepository.getOne(orderItem.getProduct().getId());
           if(oldProduct!=null) {
               log.debug("rtjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj3"+oldProduct.toString());
               oldProduct.setQuantity(oldProduct.getQuantity() + orderItem.getQuantity());
               log.debug("rtjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj3"+oldProduct.toString());

               oldProduct = productRepository.save(oldProduct);


               log.debug("rtjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj4");

               if (oldProduct != null) {
                   log.debug("rtjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj5");

                   InventoryLog inventoryLog = new InventoryLog();
                   inventoryLog.setAdded(true);
                   inventoryLog.setDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
                   inventoryLog.setProduct(oldProduct);
                   inventoryLog.setQuantity(orderItem.getQuantity().floatValue());
                   inventoryLog.setUserProfile(userProfile);
                   inventoryLog = inventoryLogRepository.save(inventoryLog);
                   log.debug("rtjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj6");


               }
           }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-lists : get all the orderLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderLists in body
     */
    @GetMapping("/order-lists")
    @Timed
    public List<OrderList> getAllOrderLists() {
        log.debug("REST request to get all OrderLists");
        return orderListRepository.findAll();
    }

    /**
     * GET  /order-lists/:id : get the "id" orderList.
     *
     * @param id the id of the orderList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderList, or with status 404 (Not Found)
     */
    @GetMapping("/order-lists/{id}")
    @Timed
    public ResponseEntity<OrderList> getOrderList(@PathVariable Long id) {
        log.debug("REST request to get OrderList : {}", id);
        Optional<OrderList> orderList = orderListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderList);
    }

    /**
     * DELETE  /order-lists/:id : delete the "id" orderList.
     *
     * @param id the id of the orderList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    /*@DeleteMapping("/order-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderList(@PathVariable Long id) {
        log.debug("REST request to delete OrderList : {}", id);

        orderListRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }*/


}
