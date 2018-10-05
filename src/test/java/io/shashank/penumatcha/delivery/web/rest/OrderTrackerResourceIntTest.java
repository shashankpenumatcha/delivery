package io.shashank.penumatcha.delivery.web.rest;

import io.shashank.penumatcha.delivery.DeliveryApp;

import io.shashank.penumatcha.delivery.domain.OrderTracker;
import io.shashank.penumatcha.delivery.repository.OrderTrackerRepository;
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
 * Test class for the OrderTrackerResource REST controller.
 *
 * @see OrderTrackerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeliveryApp.class)
public class OrderTrackerResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private OrderTrackerRepository orderTrackerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderTrackerMockMvc;

    private OrderTracker orderTracker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderTrackerResource orderTrackerResource = new OrderTrackerResource(orderTrackerRepository);
        this.restOrderTrackerMockMvc = MockMvcBuilders.standaloneSetup(orderTrackerResource)
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
    public static OrderTracker createEntity(EntityManager em) {
        OrderTracker orderTracker = new OrderTracker()
            .dateTime(DEFAULT_DATE_TIME);
        return orderTracker;
    }

    @Before
    public void initTest() {
        orderTracker = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderTracker() throws Exception {
        int databaseSizeBeforeCreate = orderTrackerRepository.findAll().size();

        // Create the OrderTracker
        restOrderTrackerMockMvc.perform(post("/api/order-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTracker)))
            .andExpect(status().isCreated());

        // Validate the OrderTracker in the database
        List<OrderTracker> orderTrackerList = orderTrackerRepository.findAll();
        assertThat(orderTrackerList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTracker testOrderTracker = orderTrackerList.get(orderTrackerList.size() - 1);
        assertThat(testOrderTracker.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    public void createOrderTrackerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTrackerRepository.findAll().size();

        // Create the OrderTracker with an existing ID
        orderTracker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTrackerMockMvc.perform(post("/api/order-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTracker)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTracker in the database
        List<OrderTracker> orderTrackerList = orderTrackerRepository.findAll();
        assertThat(orderTrackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderTrackers() throws Exception {
        // Initialize the database
        orderTrackerRepository.saveAndFlush(orderTracker);

        // Get all the orderTrackerList
        restOrderTrackerMockMvc.perform(get("/api/order-trackers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getOrderTracker() throws Exception {
        // Initialize the database
        orderTrackerRepository.saveAndFlush(orderTracker);

        // Get the orderTracker
        restOrderTrackerMockMvc.perform(get("/api/order-trackers/{id}", orderTracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderTracker.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingOrderTracker() throws Exception {
        // Get the orderTracker
        restOrderTrackerMockMvc.perform(get("/api/order-trackers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTracker() throws Exception {
        // Initialize the database
        orderTrackerRepository.saveAndFlush(orderTracker);

        int databaseSizeBeforeUpdate = orderTrackerRepository.findAll().size();

        // Update the orderTracker
        OrderTracker updatedOrderTracker = orderTrackerRepository.findById(orderTracker.getId()).get();
        // Disconnect from session so that the updates on updatedOrderTracker are not directly saved in db
        em.detach(updatedOrderTracker);
        updatedOrderTracker
            .dateTime(UPDATED_DATE_TIME);

        restOrderTrackerMockMvc.perform(put("/api/order-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderTracker)))
            .andExpect(status().isOk());

        // Validate the OrderTracker in the database
        List<OrderTracker> orderTrackerList = orderTrackerRepository.findAll();
        assertThat(orderTrackerList).hasSize(databaseSizeBeforeUpdate);
        OrderTracker testOrderTracker = orderTrackerList.get(orderTrackerList.size() - 1);
        assertThat(testOrderTracker.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderTracker() throws Exception {
        int databaseSizeBeforeUpdate = orderTrackerRepository.findAll().size();

        // Create the OrderTracker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTrackerMockMvc.perform(put("/api/order-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTracker)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTracker in the database
        List<OrderTracker> orderTrackerList = orderTrackerRepository.findAll();
        assertThat(orderTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderTracker() throws Exception {
        // Initialize the database
        orderTrackerRepository.saveAndFlush(orderTracker);

        int databaseSizeBeforeDelete = orderTrackerRepository.findAll().size();

        // Get the orderTracker
        restOrderTrackerMockMvc.perform(delete("/api/order-trackers/{id}", orderTracker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderTracker> orderTrackerList = orderTrackerRepository.findAll();
        assertThat(orderTrackerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTracker.class);
        OrderTracker orderTracker1 = new OrderTracker();
        orderTracker1.setId(1L);
        OrderTracker orderTracker2 = new OrderTracker();
        orderTracker2.setId(orderTracker1.getId());
        assertThat(orderTracker1).isEqualTo(orderTracker2);
        orderTracker2.setId(2L);
        assertThat(orderTracker1).isNotEqualTo(orderTracker2);
        orderTracker1.setId(null);
        assertThat(orderTracker1).isNotEqualTo(orderTracker2);
    }
}
