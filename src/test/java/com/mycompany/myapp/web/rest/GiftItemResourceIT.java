package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.GiftItem;
import com.mycompany.myapp.repository.GiftItemRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GiftItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GiftItemResourceIT {

    private static final String DEFAULT_GIFT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GIFT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final Integer DEFAULT_AVALIBLE_QUANTITY = 1;
    private static final Integer UPDATED_AVALIBLE_QUANTITY = 2;

    private static final String ENTITY_API_URL = "/api/gift-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GiftItemRepository giftItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGiftItemMockMvc;

    private GiftItem giftItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftItem createEntity(EntityManager em) {
        GiftItem giftItem = new GiftItem()
            .giftName(DEFAULT_GIFT_NAME)
            .descripption(DEFAULT_DESCRIPPTION)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .avalibleQuantity(DEFAULT_AVALIBLE_QUANTITY);
        return giftItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftItem createUpdatedEntity(EntityManager em) {
        GiftItem giftItem = new GiftItem()
            .giftName(UPDATED_GIFT_NAME)
            .descripption(UPDATED_DESCRIPPTION)
            .unitPrice(UPDATED_UNIT_PRICE)
            .avalibleQuantity(UPDATED_AVALIBLE_QUANTITY);
        return giftItem;
    }

    @BeforeEach
    public void initTest() {
        giftItem = createEntity(em);
    }

    @Test
    @Transactional
    void createGiftItem() throws Exception {
        int databaseSizeBeforeCreate = giftItemRepository.findAll().size();
        // Create the GiftItem
        restGiftItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftItem)))
            .andExpect(status().isCreated());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeCreate + 1);
        GiftItem testGiftItem = giftItemList.get(giftItemList.size() - 1);
        assertThat(testGiftItem.getGiftName()).isEqualTo(DEFAULT_GIFT_NAME);
        assertThat(testGiftItem.getDescripption()).isEqualTo(DEFAULT_DESCRIPPTION);
        assertThat(testGiftItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testGiftItem.getAvalibleQuantity()).isEqualTo(DEFAULT_AVALIBLE_QUANTITY);
    }

    @Test
    @Transactional
    void createGiftItemWithExistingId() throws Exception {
        // Create the GiftItem with an existing ID
        giftItem.setId(1L);

        int databaseSizeBeforeCreate = giftItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftItem)))
            .andExpect(status().isBadRequest());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGiftItems() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        // Get all the giftItemList
        restGiftItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].giftName").value(hasItem(DEFAULT_GIFT_NAME)))
            .andExpect(jsonPath("$.[*].descripption").value(hasItem(DEFAULT_DESCRIPPTION)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].avalibleQuantity").value(hasItem(DEFAULT_AVALIBLE_QUANTITY)));
    }

    @Test
    @Transactional
    void getGiftItem() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        // Get the giftItem
        restGiftItemMockMvc
            .perform(get(ENTITY_API_URL_ID, giftItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(giftItem.getId().intValue()))
            .andExpect(jsonPath("$.giftName").value(DEFAULT_GIFT_NAME))
            .andExpect(jsonPath("$.descripption").value(DEFAULT_DESCRIPPTION))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.avalibleQuantity").value(DEFAULT_AVALIBLE_QUANTITY));
    }

    @Test
    @Transactional
    void getNonExistingGiftItem() throws Exception {
        // Get the giftItem
        restGiftItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGiftItem() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();

        // Update the giftItem
        GiftItem updatedGiftItem = giftItemRepository.findById(giftItem.getId()).get();
        // Disconnect from session so that the updates on updatedGiftItem are not directly saved in db
        em.detach(updatedGiftItem);
        updatedGiftItem
            .giftName(UPDATED_GIFT_NAME)
            .descripption(UPDATED_DESCRIPPTION)
            .unitPrice(UPDATED_UNIT_PRICE)
            .avalibleQuantity(UPDATED_AVALIBLE_QUANTITY);

        restGiftItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGiftItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGiftItem))
            )
            .andExpect(status().isOk());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
        GiftItem testGiftItem = giftItemList.get(giftItemList.size() - 1);
        assertThat(testGiftItem.getGiftName()).isEqualTo(UPDATED_GIFT_NAME);
        assertThat(testGiftItem.getDescripption()).isEqualTo(UPDATED_DESCRIPPTION);
        assertThat(testGiftItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testGiftItem.getAvalibleQuantity()).isEqualTo(UPDATED_AVALIBLE_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingGiftItem() throws Exception {
        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();
        giftItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, giftItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giftItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGiftItem() throws Exception {
        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();
        giftItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giftItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGiftItem() throws Exception {
        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();
        giftItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giftItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGiftItemWithPatch() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();

        // Update the giftItem using partial update
        GiftItem partialUpdatedGiftItem = new GiftItem();
        partialUpdatedGiftItem.setId(giftItem.getId());

        partialUpdatedGiftItem.descripption(UPDATED_DESCRIPPTION);

        restGiftItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGiftItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGiftItem))
            )
            .andExpect(status().isOk());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
        GiftItem testGiftItem = giftItemList.get(giftItemList.size() - 1);
        assertThat(testGiftItem.getGiftName()).isEqualTo(DEFAULT_GIFT_NAME);
        assertThat(testGiftItem.getDescripption()).isEqualTo(UPDATED_DESCRIPPTION);
        assertThat(testGiftItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testGiftItem.getAvalibleQuantity()).isEqualTo(DEFAULT_AVALIBLE_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateGiftItemWithPatch() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();

        // Update the giftItem using partial update
        GiftItem partialUpdatedGiftItem = new GiftItem();
        partialUpdatedGiftItem.setId(giftItem.getId());

        partialUpdatedGiftItem
            .giftName(UPDATED_GIFT_NAME)
            .descripption(UPDATED_DESCRIPPTION)
            .unitPrice(UPDATED_UNIT_PRICE)
            .avalibleQuantity(UPDATED_AVALIBLE_QUANTITY);

        restGiftItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGiftItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGiftItem))
            )
            .andExpect(status().isOk());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
        GiftItem testGiftItem = giftItemList.get(giftItemList.size() - 1);
        assertThat(testGiftItem.getGiftName()).isEqualTo(UPDATED_GIFT_NAME);
        assertThat(testGiftItem.getDescripption()).isEqualTo(UPDATED_DESCRIPPTION);
        assertThat(testGiftItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testGiftItem.getAvalibleQuantity()).isEqualTo(UPDATED_AVALIBLE_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingGiftItem() throws Exception {
        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();
        giftItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, giftItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giftItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGiftItem() throws Exception {
        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();
        giftItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giftItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGiftItem() throws Exception {
        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();
        giftItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiftItemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(giftItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGiftItem() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        int databaseSizeBeforeDelete = giftItemRepository.findAll().size();

        // Delete the giftItem
        restGiftItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, giftItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
