package io.shashank.penumatcha.delivery.web.rest;

import io.shashank.penumatcha.delivery.DeliveryApp;

import io.shashank.penumatcha.delivery.domain.OrderList;
import io.shashank.penumatcha.delivery.repository.OrderListRepository;
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
 * Test class for the OrderListResource REST controller.
 *
 * @see OrderListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeliveryApp.class)
public class OrderListResourceIntTest {

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderListMockMvc;

    private OrderList orderList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderListResource orderListResource = new OrderListResource(orderListRepository);
        this.restOrderListMockMvc = MockMvcBuilders.standaloneSetup(orderListResource)
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
    public static OrderList createEntity(EntityManager em) {
        OrderList orderList = new OrderList()
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return orderList;
    }

    @Before
    public void initTest() {
        orderList = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderList() throws Exception {
        int databaseSizeBeforeCreate = orderListRepository.findAll().size();

        // Create the OrderList
        restOrderListMockMvc.perform(post("/api/order-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderList)))
            .andExpect(status().isCreated());

        // Validate the OrderList in the database
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeCreate + 1);
        OrderList testOrderList = orderListList.get(orderListList.size() - 1);
        assertThat(testOrderList.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void createOrderListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderListRepository.findAll().size();

        // Create the OrderList with an existing ID
        orderList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderListMockMvc.perform(post("/api/order-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderList)))
            .andExpect(status().isBadRequest());

        // Validate the OrderList in the database
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderLists() throws Exception {
        // Initialize the database
        orderListRepository.saveAndFlush(orderList);

        // Get all the orderListList
        restOrderListMockMvc.perform(get("/api/order-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderList.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))));
    }
    
    @Test
    @Transactional
    public void getOrderList() throws Exception {
        // Initialize the database
        orderListRepository.saveAndFlush(orderList);

        // Get the orderList
        restOrderListMockMvc.perform(get("/api/order-lists/{id}", orderList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderList.getId().intValue()))
            .andExpect(jsonPath("$.lastUpdated").value(sameInstant(DEFAULT_LAST_UPDATED)));
    }

    @Test
    @Transactional
    public void getNonExistingOrderList() throws Exception {
        // Get the orderList
        restOrderListMockMvc.perform(get("/api/order-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderList() throws Exception {
        // Initialize the database
        orderListRepository.saveAndFlush(orderList);

        int databaseSizeBeforeUpdate = orderListRepository.findAll().size();

        // Update the orderList
        OrderList updatedOrderList = orderListRepository.findById(orderList.getId()).get();
        // Disconnect from session so that the updates on updatedOrderList are not directly saved in db
        em.detach(updatedOrderList);
        updatedOrderList
            .lastUpdated(UPDATED_LAST_UPDATED);

        restOrderListMockMvc.perform(put("/api/order-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderList)))
            .andExpect(status().isOk());

        // Validate the OrderList in the database
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeUpdate);
        OrderList testOrderList = orderListList.get(orderListList.size() - 1);
        assertThat(testOrderList.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderList() throws Exception {
        int databaseSizeBeforeUpdate = orderListRepository.findAll().size();

        // Create the OrderList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderListMockMvc.perform(put("/api/order-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderList)))
            .andExpect(status().isBadRequest());

        // Validate the OrderList in the database
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderList() throws Exception {
        // Initialize the database
        orderListRepository.saveAndFlush(orderList);

        int databaseSizeBeforeDelete = orderListRepository.findAll().size();

        // Get the orderList
        restOrderListMockMvc.perform(delete("/api/order-lists/{id}", orderList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderList> orderListList = orderListRepository.findAll();
        assertThat(orderListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderList.class);
        OrderList orderList1 = new OrderList();
        orderList1.setId(1L);
        OrderList orderList2 = new OrderList();
        orderList2.setId(orderList1.getId());
        assertThat(orderList1).isEqualTo(orderList2);
        orderList2.setId(2L);
        assertThat(orderList1).isNotEqualTo(orderList2);
        orderList1.setId(null);
        assertThat(orderList1).isNotEqualTo(orderList2);
    }
}
