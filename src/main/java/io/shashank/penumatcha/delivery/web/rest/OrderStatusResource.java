package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.OrderStatus;
import io.shashank.penumatcha.delivery.repository.OrderStatusRepository;
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
 * REST controller for managing OrderStatus.
 */
@RestController
@RequestMapping("/api")
public class OrderStatusResource {

    private final Logger log = LoggerFactory.getLogger(OrderStatusResource.class);

    private static final String ENTITY_NAME = "orderStatus";

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusResource(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    /**
     * POST  /order-statuses : Create a new orderStatus.
     *
     * @param orderStatus the orderStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderStatus, or with status 400 (Bad Request) if the orderStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-statuses")
    @Timed
    public ResponseEntity<OrderStatus> createOrderStatus(@RequestBody OrderStatus orderStatus) throws URISyntaxException {
        log.debug("REST request to save OrderStatus : {}", orderStatus);
        if (orderStatus.getId() != null) {
            throw new BadRequestAlertException("A new orderStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderStatus result = orderStatusRepository.save(orderStatus);
        return ResponseEntity.created(new URI("/api/order-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-statuses : Updates an existing orderStatus.
     *
     * @param orderStatus the orderStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderStatus,
     * or with status 400 (Bad Request) if the orderStatus is not valid,
     * or with status 500 (Internal Server Error) if the orderStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-statuses")
    @Timed
    public ResponseEntity<OrderStatus> updateOrderStatus(@RequestBody OrderStatus orderStatus) throws URISyntaxException {
        log.debug("REST request to update OrderStatus : {}", orderStatus);
        if (orderStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderStatus result = orderStatusRepository.save(orderStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-statuses : get all the orderStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderStatuses in body
     */
    @GetMapping("/order-statuses")
    @Timed
    public List<OrderStatus> getAllOrderStatuses() {
        log.debug("REST request to get all OrderStatuses");
        return orderStatusRepository.findAll();
    }

    /**
     * GET  /order-statuses/:id : get the "id" orderStatus.
     *
     * @param id the id of the orderStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderStatus, or with status 404 (Not Found)
     */
    @GetMapping("/order-statuses/{id}")
    @Timed
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        log.debug("REST request to get OrderStatus : {}", id);
        Optional<OrderStatus> orderStatus = orderStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderStatus);
    }

    /**
     * DELETE  /order-statuses/:id : delete the "id" orderStatus.
     *
     * @param id the id of the orderStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Long id) {
        log.debug("REST request to delete OrderStatus : {}", id);

        orderStatusRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
