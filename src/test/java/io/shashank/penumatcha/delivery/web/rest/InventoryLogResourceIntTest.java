package io.shashank.penumatcha.delivery.web.rest;

import io.shashank.penumatcha.delivery.DeliveryApp;

import io.shashank.penumatcha.delivery.domain.InventoryLog;
import io.shashank.penumatcha.delivery.repository.InventoryLogRepository;
import io.shashank.penumatcha.delivery.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static io.shashank.penumatcha.delivery.web.rest.TestUtil.sameInstant;
import static io.shashank.penumatcha.delivery.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InventoryLogResource REST controller.
 *
 * @see InventoryLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeliveryApp.class)
public class InventoryLogResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ADDED = false;
    private static final Boolean UPDATED_ADDED = true;

    private static final Boolean DEFAULT_REMOVED = false;
    private static final Boolean UPDATED_REMOVED = true;

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;

    @Autowired
    private InventoryLogRepository inventoryLogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInventoryLogMockMvc;

    private InventoryLog inventoryLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryLogResource inventoryLogResource = new InventoryLogResource(inventoryLogRepository);
        this.restInventoryLogMockMvc = MockMvcBuilders.standaloneSetup(inventoryLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryLog createEntity(EntityManager em) {
        InventoryLog inventoryLog = new InventoryLog()
            .date(DEFAULT_DATE)
            .added(DEFAULT_ADDED)
            .removed(DEFAULT_REMOVED)
            .quantity(DEFAULT_QUANTITY);
        return inventoryLog;
    }

    @Before
    public void initTest() {
        inventoryLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryLog() throws Exception {
        int databaseSizeBeforeCreate = inventoryLogRepository.findAll().size();

        // Create the InventoryLog
        restInventoryLogMockMvc.perform(post("/api/inventory-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryLog)))
            .andExpect(status().isCreated());

        // Validate the InventoryLog in the database
        List<InventoryLog> inventoryLogList = inventoryLogRepository.findAll();
        assertThat(inventoryLogList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryLog testInventoryLog = inventoryLogList.get(inventoryLogList.size() - 1);
        assertThat(testInventoryLog.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testInventoryLog.isAdded()).isEqualTo(DEFAULT_ADDED);
        assertThat(testInventoryLog.isRemoved()).isEqualTo(DEFAULT_REMOVED);
        assertThat(testInventoryLog.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createInventoryLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryLogRepository.findAll().size();

        // Create the InventoryLog with an existing ID
        inventoryLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryLogMockMvc.perform(post("/api/inventory-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryLog)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryLog in the database
        List<InventoryLog> inventoryLogList = inventoryLogRepository.findAll();
        assertThat(inventoryLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInventoryLogs() throws Exception {
        // Initialize the database
        inventoryLogRepository.saveAndFlush(inventoryLog);

        // Get all the inventoryLogList
        restInventoryLogMockMvc.perform(get("/api/inventory-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].added").value(hasItem(DEFAULT_ADDED.booleanValue())))
            .andExpect(jsonPath("$.[*].removed").value(hasItem(DEFAULT_REMOVED.booleanValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getInventoryLog() throws Exception {
        // Initialize the database
        inventoryLogRepository.saveAndFlush(inventoryLog);

        // Get the inventoryLog
        restInventoryLogMockMvc.perform(get("/api/inventory-logs/{id}", inventoryLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryLog.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.added").value(DEFAULT_ADDED.booleanValue()))
            .andExpect(jsonPath("$.removed").value(DEFAULT_REMOVED.booleanValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryLog() throws Exception {
        // Get the inventoryLog
        restInventoryLogMockMvc.perform(get("/api/inventory-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryLog() throws Exception {
        // Initialize the database
        inventoryLogRepository.saveAndFlush(inventoryLog);

        int databaseSizeBeforeUpdate = inventoryLogRepository.findAll().size();

        // Update the inventoryLog
        InventoryLog updatedInventoryLog = inventoryLogRepository.findById(inventoryLog.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryLog are not directly saved in db
        em.detach(updatedInventoryLog);
        updatedInventoryLog
            .date(UPDATED_DATE)
            .added(UPDATED_ADDED)
            .removed(UPDATED_REMOVED)
            .quantity(UPDATED_QUANTITY);

        restInventoryLogMockMvc.perform(put("/api/inventory-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryLog)))
            .andExpect(status().isOk());

        // Validate the InventoryLog in the database
        List<InventoryLog> inventoryLogList = inventoryLogRepository.findAll();
        assertThat(inventoryLogList).hasSize(databaseSizeBeforeUpdate);
        InventoryLog testInventoryLog = inventoryLogList.get(inventoryLogList.size() - 1);
        assertThat(testInventoryLog.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testInventoryLog.isAdded()).isEqualTo(UPDATED_ADDED);
        assertThat(testInventoryLog.isRemoved()).isEqualTo(UPDATED_REMOVED);
        assertThat(testInventoryLog.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryLog() throws Exception {
        int databaseSizeBeforeUpdate = inventoryLogRepository.findAll().size();

        // Create the InventoryLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryLogMockMvc.perform(put("/api/inventory-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryLog)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryLog in the database
        List<InventoryLog> inventoryLogList = inventoryLogRepository.findAll();
        assertThat(inventoryLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryLog() throws Exception {
        // Initialize the database
        inventoryLogRepository.saveAndFlush(inventoryLog);

        int databaseSizeBeforeDelete = inventoryLogRepository.findAll().size();

        // Get the inventoryLog
        restInventoryLogMockMvc.perform(delete("/api/inventory-logs/{id}", inventoryLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryLog> inventoryLogList = inventoryLogRepository.findAll();
        assertThat(inventoryLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryLog.class);
        InventoryLog inventoryLog1 = new InventoryLog();
        inventoryLog1.setId(1L);
        InventoryLog inventoryLog2 = new InventoryLog();
        inventoryLog2.setId(inventoryLog1.getId());
        assertThat(inventoryLog1).isEqualTo(inventoryLog2);
        inventoryLog2.setId(2L);
        assertThat(inventoryLog1).isNotEqualTo(inventoryLog2);
        inventoryLog1.setId(null);
        assertThat(inventoryLog1).isNotEqualTo(inventoryLog2);
    }
}
