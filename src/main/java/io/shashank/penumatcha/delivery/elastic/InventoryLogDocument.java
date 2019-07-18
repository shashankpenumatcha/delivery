package io.shashank.penumatcha.delivery.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.shashank.penumatcha.delivery.domain.InventoryLog;
import io.shashank.penumatcha.delivery.domain.Product;
import io.shashank.penumatcha.delivery.domain.UserProfile;
import org.joda.time.DateTime;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

//@Document(indexName = "inventory_log")
public class InventoryLogDocument implements Serializable {

    // private static final long serialVersionUID = 1L;

   // @Id
    private Long id;

//    private Long logId;

    private Date date;

    private Boolean added;

    private Boolean removed;

    private Float quantity;

    private Long product;

    private Long userProfile;

    private String productName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public InventoryLogDocument(InventoryLog inventoryLog){
        this.id=inventoryLog.getId();
      //  this.logId=inventoryLog.getId();
        this.added=inventoryLog.isAdded();
        this.removed=inventoryLog.isRemoved();
        this.quantity=inventoryLog.getQuantity();
        this.product=inventoryLog.getProduct().getId();
        this.userProfile=inventoryLog.getUserProfile().getId();
        this.date = Date.from(inventoryLog.getDate().toInstant());
        this.productName = inventoryLog.getProduct().getName();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getDate() {
        return date;
    }

    public io.shashank.penumatcha.delivery.elastic.InventoryLogDocument date(Date date) {
        this.date = date;
        return this;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean isAdded() {
        return added;
    }

    public io.shashank.penumatcha.delivery.elastic.InventoryLogDocument added(Boolean added) {
        this.added = added;
        return this;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }

    public Boolean isRemoved() {
        return removed;
    }

    public io.shashank.penumatcha.delivery.elastic.InventoryLogDocument removed(Boolean removed) {
        this.removed = removed;
        return this;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    public Float getQuantity() {
        return quantity;
    }

    public io.shashank.penumatcha.delivery.elastic.InventoryLogDocument quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Long getProduct() {
        return product;
    }

    public io.shashank.penumatcha.delivery.elastic.InventoryLogDocument product(Long product) {
        this.product = product;
        return this;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getUserProfile() {
        return userProfile;
    }

    public io.shashank.penumatcha.delivery.elastic.InventoryLogDocument userProfile(Long userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(Long userProfile) {
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
        io.shashank.penumatcha.delivery.elastic.InventoryLogDocument inventoryLog = (io.shashank.penumatcha.delivery.elastic.InventoryLogDocument) o;
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
