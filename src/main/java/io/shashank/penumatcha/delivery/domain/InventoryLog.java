package io.shashank.penumatcha.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A InventoryLog.
 */
@Entity
@Table(name = "inventory_log")
public class InventoryLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "added")
    private Boolean added;

    @Column(name = "removed")
    private Boolean removed;

    @Column(name = "quantity")
    private Float quantity;

    @ManyToOne
    @JsonIgnoreProperties("inventoryLogs")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("inventoryLogs")
    private UserProfile userProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public InventoryLog date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Boolean isAdded() {
        return added;
    }

    public InventoryLog added(Boolean added) {
        this.added = added;
        return this;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }

    public Boolean isRemoved() {
        return removed;
    }

    public InventoryLog removed(Boolean removed) {
        this.removed = removed;
        return this;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    public Float getQuantity() {
        return quantity;
    }

    public InventoryLog quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public InventoryLog product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public InventoryLog userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InventoryLog inventoryLog = (InventoryLog) o;
        if (inventoryLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventoryLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventoryLog{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", added='" + isAdded() + "'" +
            ", removed='" + isRemoved() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
