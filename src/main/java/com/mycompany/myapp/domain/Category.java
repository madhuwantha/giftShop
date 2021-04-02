package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = { "category", "carts", "orders" }, allowSetters = true)
    private Set<GiftItem> giftItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category id(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public Category categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<GiftItem> getGiftItems() {
        return this.giftItems;
    }

    public Category giftItems(Set<GiftItem> giftItems) {
        this.setGiftItems(giftItems);
        return this;
    }

    public Category addGiftItem(GiftItem giftItem) {
        this.giftItems.add(giftItem);
        giftItem.setCategory(this);
        return this;
    }

    public Category removeGiftItem(GiftItem giftItem) {
        this.giftItems.remove(giftItem);
        giftItem.setCategory(null);
        return this;
    }

    public void setGiftItems(Set<GiftItem> giftItems) {
        if (this.giftItems != null) {
            this.giftItems.forEach(i -> i.setCategory(null));
        }
        if (giftItems != null) {
            giftItems.forEach(i -> i.setCategory(this));
        }
        this.giftItems = giftItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            "}";
    }
}
