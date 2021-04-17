package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.GiftItem;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CartRepository;
import com.mycompany.myapp.repository.GiftItemRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Cart}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private static final String ENTITY_NAME = "cart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final GiftItemRepository giftItemRepository;

    public CartResource(CartRepository cartRepository, UserRepository userRepository, GiftItemRepository giftItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.giftItemRepository = giftItemRepository;
    }

    /**
     * {@code POST  /carts} : Create a new cart.
     *
     * @param cart the cart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cart, or with status {@code 400 (Bad Request)} if the cart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carts")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cart);
        if (cart.getId() != null) {
            throw new BadRequestAlertException("A new cart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cart result = cartRepository.save(cart);
        return ResponseEntity
            .created(new URI("/api/carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carts/:id} : Updates an existing cart.
     *
     * @param id the id of the cart to save.
     * @param cart the cart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cart,
     * or with status {@code 400 (Bad Request)} if the cart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carts/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable(value = "id", required = false) final Long id, @RequestBody Cart cart)
        throws URISyntaxException {
        log.debug("REST request to update Cart : {}, {}", id, cart);
        if (cart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cart result = cartRepository.save(cart);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cart.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carts/:id} : Partial updates given fields of an existing cart, field will ignore if it is null
     *
     * @param id the id of the cart to save.
     * @param cart the cart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cart,
     * or with status {@code 400 (Bad Request)} if the cart is not valid,
     * or with status {@code 404 (Not Found)} if the cart is not found,
     * or with status {@code 500 (Internal Server Error)} if the cart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/carts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Cart> partialUpdateCart(@PathVariable(value = "id", required = false) final Long id, @RequestBody Cart cart)
        throws URISyntaxException {
        log.debug("REST request to partial update Cart partially : {}, {}", id, cart);
        if (cart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cart> result = cartRepository
            .findById(cart.getId())
            .map(
                existingCart -> {
                    if (cart.getDescripption() != null) {
                        existingCart.setDescripption(cart.getDescripption());
                    }

                    return existingCart;
                }
            )
            .map(cartRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cart.getId().toString())
        );
    }

    /**
     * {@code GET  /carts} : get all the carts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carts in body.
     */
    @GetMapping("/carts")
    public List<Cart> getAllCarts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Carts");
        return cartRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /carts/:id} : get the "id" cart.
     *
     * @param id the id of the cart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carts/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        log.debug("REST request to get Cart : {}", id);
        Optional<Cart> cart = cartRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(cart);
    }

    @GetMapping("/carts/user/{id}")
    public ResponseEntity<Cart> getCartByUser(@PathVariable Long id) {
        log.debug("REST request to get Cart by user : {}", id);

        User user = userRepository.findById(id).get();
        List<Cart> carts = cartRepository.findByUser(user);
        Cart cart = null;
        if (carts.size() < 1) {
            cart = new Cart();
            cart.setUser(user);

            cart = cartRepository.save(cart);
        } else {
            cart = carts.get(0);
        }

        //        Cart cart = cartRepository.findByUser(userRepository.findById(id).get()).get(0);
        Set<GiftItem> giftItems = cart.getGiftItems();
        cart.setGiftItems(giftItems);

        Cart finalCart = cart;
        cart.getGiftItems().forEach(giftItem -> finalCart.getGiftItems().add(giftItem));

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/carts/user/{user_id}/gift-item/{id}")
    public ResponseEntity<Cart> removeGiftItem(@PathVariable Long id, @PathVariable Long user_id) {
        log.debug("REST request to delete Gift Item from cart by Gift Item id : {}", id);
        GiftItem giftItem = giftItemRepository.findById(id).get();
        Cart cart = cartRepository.findByUser(userRepository.findById(user_id).get()).get(0);
        cart.getGiftItems().remove(giftItem);
        cart = cartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/carts/user/{user_id}/gift-item/{id}")
    public ResponseEntity<Cart> addGiftItem(@PathVariable Long id, @PathVariable Long user_id) {
        log.debug("REST request to add Gift Item for cart by Gift Item id : {}", id);
        GiftItem giftItem = giftItemRepository.findById(id).get();

        User user = userRepository.findById(user_id).get();

        List<Cart> carts = cartRepository.findByUser(user);
        Cart cart = null;
        if (carts.size() < 1) {
            cart = new Cart();
            cart.setUser(user);

            cart = cartRepository.save(cart);
        } else {
            cart = carts.get(0);
        }

        cart.getGiftItems().add(giftItem);
        cart = cartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }

    /**
     * {@code DELETE  /carts/:id} : delete the "id" cart.
     *
     * @param id the id of the cart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
