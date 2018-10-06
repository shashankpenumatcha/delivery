package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.OrderList;
import io.shashank.penumatcha.delivery.repository.OrderListRepository;
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
 * REST controller for managing OrderList.
 */
@RestController
@RequestMapping("/api")
public class OrderListResource {

    private final Logger log = LoggerFactory.getLogger(OrderListResource.class);

    private static final String ENTITY_NAME = "orderList";

    private final OrderListRepository orderListRepository;

    public OrderListResource(OrderListRepository orderListRepository) {
        this.orderListRepository = orderListRepository;
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
    @PutMapping("/order-lists")
    @Timed
    public ResponseEntity<OrderList> updateOrderList(@RequestBody OrderList orderList) throws URISyntaxException {
        log.debug("REST request to update OrderList : {}", orderList);
        if (orderList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderList result = orderListRepository.save(orderList);
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
    @DeleteMapping("/order-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderList(@PathVariable Long id) {
        log.debug("REST request to delete OrderList : {}", id);

        orderListRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
