package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.GiftOrder;
import com.mycompany.myapp.repository.GiftOrderRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.GiftOrder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GiftOrderResource {

    private final Logger log = LoggerFactory.getLogger(GiftOrderResource.class);

    private static final String ENTITY_NAME = "giftOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiftOrderRepository giftOrderRepository;

    public GiftOrderResource(GiftOrderRepository giftOrderRepository) {
        this.giftOrderRepository = giftOrderRepository;
    }

    /**
     * {@code POST  /gift-orders} : Create a new giftOrder.
     *
     * @param giftOrder the giftOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new giftOrder, or with status {@code 400 (Bad Request)} if the giftOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gift-orders")
    public ResponseEntity<GiftOrder> createGiftOrder(@RequestBody GiftOrder giftOrder) throws URISyntaxException {
        log.debug("REST request to save GiftOrder : {}", giftOrder);
        if (giftOrder.getId() != null) {
            throw new BadRequestAlertException("A new giftOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GiftOrder result = giftOrderRepository.save(giftOrder);
        return ResponseEntity
            .created(new URI("/api/gift-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gift-orders/:id} : Updates an existing giftOrder.
     *
     * @param id the id of the giftOrder to save.
     * @param giftOrder the giftOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giftOrder,
     * or with status {@code 400 (Bad Request)} if the giftOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the giftOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gift-orders/{id}")
    public ResponseEntity<GiftOrder> updateGiftOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GiftOrder giftOrder
    ) throws URISyntaxException {
        log.debug("REST request to update GiftOrder : {}, {}", id, giftOrder);
        if (giftOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, giftOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!giftOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GiftOrder result = giftOrderRepository.save(giftOrder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, giftOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gift-orders/:id} : Partial updates given fields of an existing giftOrder, field will ignore if it is null
     *
     * @param id the id of the giftOrder to save.
     * @param giftOrder the giftOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giftOrder,
     * or with status {@code 400 (Bad Request)} if the giftOrder is not valid,
     * or with status {@code 404 (Not Found)} if the giftOrder is not found,
     * or with status {@code 500 (Internal Server Error)} if the giftOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gift-orders/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GiftOrder> partialUpdateGiftOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GiftOrder giftOrder
    ) throws URISyntaxException {
        log.debug("REST request to partial update GiftOrder partially : {}, {}", id, giftOrder);
        if (giftOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, giftOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!giftOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GiftOrder> result = giftOrderRepository
            .findById(giftOrder.getId())
            .map(
                existingGiftOrder -> {
                    if (giftOrder.getDescripption() != null) {
                        existingGiftOrder.setDescripption(giftOrder.getDescripption());
                    }

                    return existingGiftOrder;
                }
            )
            .map(giftOrderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, giftOrder.getId().toString())
        );
    }

    /**
     * {@code GET  /gift-orders} : get all the giftOrders.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of giftOrders in body.
     */
    @GetMapping("/gift-orders")
    public List<GiftOrder> getAllGiftOrders(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all GiftOrders");
        return giftOrderRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /gift-orders/:id} : get the "id" giftOrder.
     *
     * @param id the id of the giftOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the giftOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gift-orders/{id}")
    public ResponseEntity<GiftOrder> getGiftOrder(@PathVariable Long id) {
        log.debug("REST request to get GiftOrder : {}", id);
        Optional<GiftOrder> giftOrder = giftOrderRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(giftOrder);
    }

    /**
     * {@code DELETE  /gift-orders/:id} : delete the "id" giftOrder.
     *
     * @param id the id of the giftOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gift-orders/{id}")
    public ResponseEntity<Void> deleteGiftOrder(@PathVariable Long id) {
        log.debug("REST request to delete GiftOrder : {}", id);
        giftOrderRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
