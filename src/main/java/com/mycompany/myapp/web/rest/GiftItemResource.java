package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.GiftItem;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.repository.GiftItemRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.GiftItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GiftItemResource {

    private final Logger log = LoggerFactory.getLogger(GiftItemResource.class);

    private static final String ENTITY_NAME = "giftItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiftItemRepository giftItemRepository;
    private final CategoryRepository categoryRepository;

    public GiftItemResource(GiftItemRepository giftItemRepository, CategoryRepository categoryRepository) {
        this.giftItemRepository = giftItemRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@code POST  /gift-items} : Create a new giftItem.
     *
     * @param giftItem the giftItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new giftItem, or with status {@code 400 (Bad Request)} if the giftItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gift-items")
    public ResponseEntity<GiftItem> createGiftItem(@RequestBody GiftItem giftItem) throws URISyntaxException {
        log.debug("REST request to save GiftItem : {}", giftItem);
        if (giftItem.getId() != null) {
            throw new BadRequestAlertException("A new giftItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GiftItem result = giftItemRepository.save(giftItem);
        return ResponseEntity
            .created(new URI("/api/gift-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gift-items/:id} : Updates an existing giftItem.
     *
     * @param id the id of the giftItem to save.
     * @param giftItem the giftItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giftItem,
     * or with status {@code 400 (Bad Request)} if the giftItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the giftItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gift-items/{id}")
    public ResponseEntity<GiftItem> updateGiftItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GiftItem giftItem
    ) throws URISyntaxException {
        log.debug("REST request to update GiftItem : {}, {}", id, giftItem);
        if (giftItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, giftItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!giftItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GiftItem result = giftItemRepository.save(giftItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, giftItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gift-items/:id} : Partial updates given fields of an existing giftItem, field will ignore if it is null
     *
     * @param id the id of the giftItem to save.
     * @param giftItem the giftItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giftItem,
     * or with status {@code 400 (Bad Request)} if the giftItem is not valid,
     * or with status {@code 404 (Not Found)} if the giftItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the giftItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gift-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GiftItem> partialUpdateGiftItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GiftItem giftItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update GiftItem partially : {}, {}", id, giftItem);
        if (giftItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, giftItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!giftItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GiftItem> result = giftItemRepository
            .findById(giftItem.getId())
            .map(
                existingGiftItem -> {
                    if (giftItem.getGiftName() != null) {
                        existingGiftItem.setGiftName(giftItem.getGiftName());
                    }
                    if (giftItem.getDescripption() != null) {
                        existingGiftItem.setDescripption(giftItem.getDescripption());
                    }
                    if (giftItem.getUnitPrice() != null) {
                        existingGiftItem.setUnitPrice(giftItem.getUnitPrice());
                    }
                    if (giftItem.getAvalibleQuantity() != null) {
                        existingGiftItem.setAvalibleQuantity(giftItem.getAvalibleQuantity());
                    }

                    return existingGiftItem;
                }
            )
            .map(giftItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, giftItem.getId().toString())
        );
    }

    /**
     * {@code GET  /gift-items} : get all the giftItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of giftItems in body.
     */
    @GetMapping("/gift-items")
    public List<GiftItem> getAllGiftItems() {
        log.debug("REST request to get all GiftItems");
        return giftItemRepository.findAll();
    }

    @GetMapping("/gift-items/category/{id}")
    public List<GiftItem> getAllGiftItemsByCategory(@PathVariable Long id) {
        log.debug("REST request to get all GiftItems by category with id {}", id);
        Category category = categoryRepository.findById(id).get();
        return giftItemRepository.findAllByCategory(category);
    }

    /**
     * {@code GET  /gift-items/:id} : get the "id" giftItem.
     *
     * @param id the id of the giftItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the giftItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gift-items/{id}")
    public ResponseEntity<GiftItem> getGiftItem(@PathVariable Long id) {
        log.debug("REST request to get GiftItem : {}", id);
        Optional<GiftItem> giftItem = giftItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(giftItem);
    }

    /**
     * {@code DELETE  /gift-items/:id} : delete the "id" giftItem.
     *
     * @param id the id of the giftItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gift-items/{id}")
    public ResponseEntity<Void> deleteGiftItem(@PathVariable Long id) {
        log.debug("REST request to delete GiftItem : {}", id);
        giftItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
