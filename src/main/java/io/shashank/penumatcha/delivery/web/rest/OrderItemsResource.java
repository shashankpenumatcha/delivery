package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.OrderItems;
import io.shashank.penumatcha.delivery.repository.OrderItemsRepository;
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
 * REST controller for managing OrderItems.
 */
@RestController
@RequestMapping("/api")
public class OrderItemsResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemsResource.class);

    private static final String ENTITY_NAME = "orderItems";

    private final OrderItemsRepository orderItemsRepository;

    public OrderItemsResource(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    /**
     * POST  /order-items : Create a new orderItems.
     *
     * @param orderItems the orderItems to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderItems, or with status 400 (Bad Request) if the orderItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-items")
    @Timed
    public ResponseEntity<OrderItems> createOrderItems(@RequestBody OrderItems orderItems) throws URISyntaxException {
        log.debug("REST request to save OrderItems : {}", orderItems);
        if (orderItems.getId() != null) {
            throw new BadRequestAlertException("A new orderItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderItems result = orderItemsRepository.save(orderItems);

        //remove quantity


        return ResponseEntity.created(new URI("/api/order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-items : Updates an existing orderItems.
     *
     * @param orderItems the orderItems to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderItems,
     * or with status 400 (Bad Request) if the orderItems is not valid,
     * or with status 500 (Internal Server Error) if the orderItems couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-items")
    @Timed
    public ResponseEntity<OrderItems> updateOrderItems(@RequestBody OrderItems orderItems) throws URISyntaxException {
        log.debug("REST request to update OrderItems : {}", orderItems);
        if (orderItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderItems result = orderItemsRepository.save(orderItems);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderItems.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-items : get all the orderItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderItems in body
     */
    @GetMapping("/order-items")
    @Timed
    public List<OrderItems> getAllOrderItems() {
        log.debug("REST request to get all OrderItems");
        return orderItemsRepository.findAll();
    }

    /**
     * GET  /order-items/:id : get the "id" orderItems.
     *
     * @param id the id of the orderItems to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderItems, or with status 404 (Not Found)
     */
    @GetMapping("/order-items/{id}")
    @Timed
    public ResponseEntity<OrderItems> getOrderItems(@PathVariable Long id) {
        log.debug("REST request to get OrderItems : {}", id);
        Optional<OrderItems> orderItems = orderItemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderItems);
    }

    /**
     * DELETE  /order-items/:id : delete the "id" orderItems.
     *
     * @param id the id of the orderItems to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderItems(@PathVariable Long id) {
        log.debug("REST request to delete OrderItems : {}", id);

        orderItemsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
