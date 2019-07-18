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
@Table(name = "backlog")
public class Backlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "date")
    private ZonedDateTime date;

    @OneToOne
    @JsonIgnoreProperties({"backlog"})
    @JoinColumn(unique = true)
    private InventoryLog inventoryLog;


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

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public InventoryLog getInventoryLog() {
        return inventoryLog;
    }

    public void setInventoryLog(InventoryLog inventoryLog) {
        this.inventoryLog = inventoryLog;
    }

    public Backlog inventoryLog(InventoryLog inventoryLog){
        this.inventoryLog=inventoryLog;
        return this;
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
            ", inventroyLog='" + getInventoryLog() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
