package io.shashank.penumatcha.delivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.shashank.penumatcha.delivery.domain.InventoryLog;
import io.shashank.penumatcha.delivery.repository.InventoryLogRepository;
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
 * REST controller for managing InventoryLog.
 */
@RestController
@RequestMapping("/api")
public class InventoryLogResource {

    private final Logger log = LoggerFactory.getLogger(InventoryLogResource.class);

    private static final String ENTITY_NAME = "inventoryLog";

    private final InventoryLogRepository inventoryLogRepository;

    public InventoryLogResource(InventoryLogRepository inventoryLogRepository) {
        this.inventoryLogRepository = inventoryLogRepository;
    }

    /**
     * POST  /inventory-logs : Create a new inventoryLog.
     *
     * @param inventoryLog the inventoryLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventoryLog, or with status 400 (Bad Request) if the inventoryLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventory-logs")
    @Timed
    public ResponseEntity<InventoryLog> createInventoryLog(@RequestBody InventoryLog inventoryLog) throws URISyntaxException {
        log.debug("REST request to save InventoryLog : {}", inventoryLog);
        if (inventoryLog.getId() != null) {
            throw new BadRequestAlertException("A new inventoryLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryLog result = inventoryLogRepository.save(inventoryLog);
        return ResponseEntity.created(new URI("/api/inventory-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventory-logs : Updates an existing inventoryLog.
     *
     * @param inventoryLog the inventoryLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventoryLog,
     * or with status 400 (Bad Request) if the inventoryLog is not valid,
     * or with status 500 (Internal Server Error) if the inventoryLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventory-logs")
    @Timed
    public ResponseEntity<InventoryLog> updateInventoryLog(@RequestBody InventoryLog inventoryLog) throws URISyntaxException {
        log.debug("REST request to update InventoryLog : {}", inventoryLog);
        if (inventoryLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryLog result = inventoryLogRepository.save(inventoryLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inventoryLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventory-logs : get all the inventoryLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inventoryLogs in body
     */
    @GetMapping("/inventory-logs")
    @Timed
    public List<InventoryLog> getAllInventoryLogs() {
        log.debug("REST request to get all InventoryLogs");
        return inventoryLogRepository.findAll();
    }

    /**
     * GET  /inventory-logs/:id : get the "id" inventoryLog.
     *
     * @param id the id of the inventoryLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventoryLog, or with status 404 (Not Found)
     */
    @GetMapping("/inventory-logs/{id}")
    @Timed
    public ResponseEntity<InventoryLog> getInventoryLog(@PathVariable Long id) {
        log.debug("REST request to get InventoryLog : {}", id);
        Optional<InventoryLog> inventoryLog = inventoryLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inventoryLog);
    }

    /**
     * DELETE  /inventory-logs/:id : delete the "id" inventoryLog.
     *
     * @param id the id of the inventoryLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventory-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteInventoryLog(@PathVariable Long id) {
        log.debug("REST request to delete InventoryLog : {}", id);

        inventoryLogRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
