package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.OrderTracker;
import io.shashank.penumatcha.delivery.repository.OrderTrackerRepository;
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
 * REST controller for managing OrderTracker.
 */
@RestController
@RequestMapping("/api")
public class OrderTrackerResource {

    private final Logger log = LoggerFactory.getLogger(OrderTrackerResource.class);

    private static final String ENTITY_NAME = "orderTracker";

    private final OrderTrackerRepository orderTrackerRepository;

    public OrderTrackerResource(OrderTrackerRepository orderTrackerRepository) {
        this.orderTrackerRepository = orderTrackerRepository;
    }

    /**
     * POST  /order-trackers : Create a new orderTracker.
     *
     * @param orderTracker the orderTracker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderTracker, or with status 400 (Bad Request) if the orderTracker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-trackers")
    @Timed
    public ResponseEntity<OrderTracker> createOrderTracker(@RequestBody OrderTracker orderTracker) throws URISyntaxException {
        log.debug("REST request to save OrderTracker : {}", orderTracker);
        if (orderTracker.getId() != null) {
            throw new BadRequestAlertException("A new orderTracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTracker result = orderTrackerRepository.save(orderTracker);
        return ResponseEntity.created(new URI("/api/order-trackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-trackers : Updates an existing orderTracker.
     *
     * @param orderTracker the orderTracker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderTracker,
     * or with status 400 (Bad Request) if the orderTracker is not valid,
     * or with status 500 (Internal Server Error) if the orderTracker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-trackers")
    @Timed
    public ResponseEntity<OrderTracker> updateOrderTracker(@RequestBody OrderTracker orderTracker) throws URISyntaxException {
        log.debug("REST request to update OrderTracker : {}", orderTracker);
        if (orderTracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderTracker result = orderTrackerRepository.save(orderTracker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderTracker.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-trackers : get all the orderTrackers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderTrackers in body
     */
    @GetMapping("/order-trackers")
    @Timed
    public List<OrderTracker> getAllOrderTrackers() {
        log.debug("REST request to get all OrderTrackers");
        return orderTrackerRepository.findAll();
    }

    /**
     * GET  /order-trackers/:id : get the "id" orderTracker.
     *
     * @param id the id of the orderTracker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderTracker, or with status 404 (Not Found)
     */
    @GetMapping("/order-trackers/{id}")
    @Timed
    public ResponseEntity<OrderTracker> getOrderTracker(@PathVariable Long id) {
        log.debug("REST request to get OrderTracker : {}", id);
        Optional<OrderTracker> orderTracker = orderTrackerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderTracker);
    }

    /**
     * DELETE  /order-trackers/:id : delete the "id" orderTracker.
     *
     * @param id the id of the orderTracker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-trackers/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderTracker(@PathVariable Long id) {
        log.debug("REST request to delete OrderTracker : {}", id);

        orderTrackerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
