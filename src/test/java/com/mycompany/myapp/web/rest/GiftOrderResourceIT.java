package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.GiftOrder;
import com.mycompany.myapp.repository.GiftOrderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GiftOrderResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GiftOrderResourceIT {

    private static final String DEFAULT_DESCRIPPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gift-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GiftOrderRepository giftOrderRepository;

    @Mock
    private GiftOrderRepository giftOrderRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGiftOrderMockMvc;

    private GiftOrder giftOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftOrder createEntity(EntityManager em) {
        GiftOrder giftOrder = new GiftOrder().descripption(DEFAULT_DESCRIPPTION);
        return giftOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftOrder createUpdatedEntity(EntityManager em) {
        GiftOrder giftOrder = new GiftOrder().descripption(UPDATED_DESCRIPPTION);
        return giftOrder;
    }

    @BeforeEach
    public void initTest() {
        giftOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createGiftOrder() throws Exception {
        int databaseSizeBeforeCreate = giftOrderRepository.findAll().size();
        // Create the GiftOrder
        restGiftOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftOrder)))
            .andExpect(status().isCreated());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeCreate + 1);
        GiftOrder testGiftOrder = giftOrderList.get(giftOrderList.size() - 1);
        assertThat(testGiftOrder.getDescripption()).isEqualTo(DEFAULT_DESCRIPPTION);
    }

    @Test
    @Transactional
    void createGiftOrderWithExistingId() throws Exception {
        // Create the GiftOrder with an existing ID
        giftOrder.setId(1L);

        int databaseSizeBeforeCreate = giftOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftOrder)))
            .andExpect(status().isBadRequest());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGiftOrders() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        // Get all the giftOrderList
        restGiftOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripption").value(hasItem(DEFAULT_DESCRIPPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGiftOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        when(giftOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGiftOrderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(giftOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGiftOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(giftOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGiftOrderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(giftOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getGiftOrder() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        // Get the giftOrder
        restGiftOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, giftOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(giftOrder.getId().intValue()))
            .andExpect(jsonPath("$.descripption").value(DEFAULT_DESCRIPPTION));
    }

    @Test
    @Transactional
    void getNonExistingGiftOrder() throws Exception {
        // Get the giftOrder
        restGiftOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGiftOrder() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();

        // Update the giftOrder
        GiftOrder updatedGiftOrder = giftOrderRepository.findById(giftOrder.getId()).get();
        // Disconnect from session so that the updates on updatedGiftOrder are not directly saved in db
        em.detach(updatedGiftOrder);
        updatedGiftOrder.descripption(UPDATED_DESCRIPPTION);

        restGiftOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGiftOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGiftOrder))
            )
            .andExpect(status().isOk());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
        GiftOrder testGiftOrder = giftOrderList.get(giftOrderList.size() - 1);
        assertThat(testGiftOrder.getDescripption()).isEqualTo(UPDATED_DESCRIPPTION);
    }

    @Test
    @Transactional
    void putNonExistingGiftOrder() throws Exception {
        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();
        giftOrder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, giftOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giftOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGiftOrder() throws Exception {
        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();
        giftOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giftOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGiftOrder() throws Exception {
        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();
        giftOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGiftOrderWithPatch() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();

        // Update the giftOrder using partial update
        GiftOrder partialUpdatedGiftOrder = new GiftOrder();
        partialUpdatedGiftOrder.setId(giftOrder.getId());

        restGiftOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGiftOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGiftOrder))
            )
            .andExpect(status().isOk());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
        GiftOrder testGiftOrder = giftOrderList.get(giftOrderList.size() - 1);
        assertThat(testGiftOrder.getDescripption()).isEqualTo(DEFAULT_DESCRIPPTION);
    }

    @Test
    @Transactional
    void fullUpdateGiftOrderWithPatch() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();

        // Update the giftOrder using partial update
        GiftOrder partialUpdatedGiftOrder = new GiftOrder();
        partialUpdatedGiftOrder.setId(giftOrder.getId());

        partialUpdatedGiftOrder.descripption(UPDATED_DESCRIPPTION);

        restGiftOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGiftOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGiftOrder))
            )
            .andExpect(status().isOk());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
        GiftOrder testGiftOrder = giftOrderList.get(giftOrderList.size() - 1);
        assertThat(testGiftOrder.getDescripption()).isEqualTo(UPDATED_DESCRIPPTION);
    }

    @Test
    @Transactional
    void patchNonExistingGiftOrder() throws Exception {
        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();
        giftOrder.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, giftOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giftOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGiftOrder() throws Exception {
        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();
        giftOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giftOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGiftOrder() throws Exception {
        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();
        giftOrder.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftOrderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(giftOrder))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGiftOrder() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        int databaseSizeBeforeDelete = giftOrderRepository.findAll().size();

        // Delete the giftOrder
        restGiftOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, giftOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
