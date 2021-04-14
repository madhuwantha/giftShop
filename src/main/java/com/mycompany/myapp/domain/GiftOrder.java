package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A GiftOrder.
 */
@Entity
@Table(name = "gift_order")
public class GiftOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripption")
    private String descripption;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "rel_gift_order__gift_items",
        joinColumns = @JoinColumn(name = "gift_order_id"),
        inverseJoinColumns = @JoinColumn(name = "gift_items_id")
    )
    @JsonIgnoreProperties(value = { "user", "category", "carts", "orders" }, allowSetters = true)
    private Set<GiftItem> giftItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GiftOrder id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescripption() {
        return this.descripption;
    }

    public GiftOrder descripption(String descripption) {
        this.descripption = descripption;
        return this;
    }

    public void setDescripption(String descripption) {
        this.descripption = descripption;
    }

    public User getUser() {
        return this.user;
    }

    public GiftOrder user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<GiftItem> getGiftItems() {
        return this.giftItems;
    }

    public GiftOrder giftItems(Set<GiftItem> giftItems) {
        this.setGiftItems(giftItems);
        return this;
    }

    public GiftOrder addGiftItems(GiftItem giftItem) {
        this.giftItems.add(giftItem);
        giftItem.getOrders().add(this);
        return this;
    }

    public GiftOrder removeGiftItems(GiftItem giftItem) {
        this.giftItems.remove(giftItem);
        giftItem.getOrders().remove(this);
        return this;
    }

    public void setGiftItems(Set<GiftItem> giftItems) {
        this.giftItems = giftItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GiftOrder)) {
            return false;
        }
        return id != null && id.equals(((GiftOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GiftOrder{" +
            "id=" + getId() +
            ", descripption='" + getDescripption() + "'" +
            "}";
    }
}
