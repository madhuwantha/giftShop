package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A GiftItem.
 */
@Entity
@Table(name = "gift_item")
public class GiftItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gift_name")
    private String giftName;

    @Column(name = "descripption")
    private String descripption;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "avalible_quantity")
    private Integer avalibleQuantity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "giftItems" }, allowSetters = true)
    private Category category;

    @ManyToMany(mappedBy = "giftItems")
    @JsonIgnoreProperties(value = { "user", "giftItems" }, allowSetters = true)
    private Set<Cart> carts = new HashSet<>();

    @ManyToMany(mappedBy = "giftItems")
    @JsonIgnoreProperties(value = { "user", "giftItems" }, allowSetters = true)
    private Set<GiftOrder> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GiftItem id(Long id) {
        this.id = id;
        return this;
    }

    public String getGiftName() {
        return this.giftName;
    }

    public GiftItem giftName(String giftName) {
        this.giftName = giftName;
        return this;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getDescripption() {
        return this.descripption;
    }

    public GiftItem descripption(String descripption) {
        this.descripption = descripption;
        return this;
    }

    public void setDescripption(String descripption) {
        this.descripption = descripption;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public GiftItem unitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getAvalibleQuantity() {
        return this.avalibleQuantity;
    }

    public GiftItem avalibleQuantity(Integer avalibleQuantity) {
        this.avalibleQuantity = avalibleQuantity;
        return this;
    }

    public void setAvalibleQuantity(Integer avalibleQuantity) {
        this.avalibleQuantity = avalibleQuantity;
    }

    public Category getCategory() {
        return this.category;
    }

    public GiftItem category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Cart> getCarts() {
        return this.carts;
    }

    public GiftItem carts(Set<Cart> carts) {
        this.setCarts(carts);
        return this;
    }

    public GiftItem addCart(Cart cart) {
        this.carts.add(cart);
        cart.getGiftItems().add(this);
        return this;
    }

    public GiftItem removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.getGiftItems().remove(this);
        return this;
    }

    public void setCarts(Set<Cart> carts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.removeGiftItems(this));
        }
        if (carts != null) {
            carts.forEach(i -> i.addGiftItems(this));
        }
        this.carts = carts;
    }

    public Set<GiftOrder> getOrders() {
        return this.orders;
    }

    public GiftItem orders(Set<GiftOrder> giftOrders) {
        this.setOrders(giftOrders);
        return this;
    }

    public GiftItem addOrder(GiftOrder giftOrder) {
        this.orders.add(giftOrder);
        giftOrder.getGiftItems().add(this);
        return this;
    }

    public GiftItem removeOrder(GiftOrder giftOrder) {
        this.orders.remove(giftOrder);
        giftOrder.getGiftItems().remove(this);
        return this;
    }

    public void setOrders(Set<GiftOrder> giftOrders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.removeGiftItems(this));
        }
        if (giftOrders != null) {
            giftOrders.forEach(i -> i.addGiftItems(this));
        }
        this.orders = giftOrders;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GiftItem)) {
            return false;
        }
        return id != null && id.equals(((GiftItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GiftItem{" +
            "id=" + getId() +
            ", giftName='" + getGiftName() + "'" +
            ", descripption='" + getDescripption() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", avalibleQuantity=" + getAvalibleQuantity() +
            "}";
    }
}
