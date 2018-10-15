package io.shashank.penumatcha.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "minimum_quantity")
    private Float minimumQuantity;

    @Column(name = "price_per_unit")
    private Float pricePerUnit;

    @Column(name = "active")
    private Boolean active;

    @JsonIgnore
    @Column(name = "quantity")
    private Float quantity;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Unit unit;

    @OneToMany(mappedBy = "product",fetch=FetchType.LAZY)
    private Set<InventoryLog> inventoryLogs = new HashSet<>();
    @OneToMany(mappedBy = "product",fetch=FetchType.LAZY)
    private Set<CartItems> cartItems = new HashSet<>();
    @OneToMany(mappedBy = "product",fetch=FetchType.LAZY)
    private Set<OrderItems> orderItems = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMinimumQuantity() {
        return minimumQuantity;
    }

    public Product minimumQuantity(Float minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
        return this;
    }

    public void setMinimumQuantity(Float minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public Float getPricePerUnit() {
        return pricePerUnit;
    }

    public Product pricePerUnit(Float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        return this;
    }

    public void setPricePerUnit(Float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Boolean isActive() {
        return active;
    }

    public Product active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Product quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public Product unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Set<InventoryLog> getInventoryLogs() {
        return inventoryLogs;
    }

    public Product inventoryLogs(Set<InventoryLog> inventoryLogs) {
        this.inventoryLogs = inventoryLogs;
        return this;
    }

    public Product addInventoryLog(InventoryLog inventoryLog) {
        this.inventoryLogs.add(inventoryLog);
        inventoryLog.setProduct(this);
        return this;
    }

    public Product removeInventoryLog(InventoryLog inventoryLog) {
        this.inventoryLogs.remove(inventoryLog);
        inventoryLog.setProduct(null);
        return this;
    }

    public void setInventoryLogs(Set<InventoryLog> inventoryLogs) {
        this.inventoryLogs = inventoryLogs;
    }

    public Set<CartItems> getCartItems() {
        return cartItems;
    }

    public Product cartItems(Set<CartItems> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public Product addCartItems(CartItems cartItems) {
        this.cartItems.add(cartItems);
        cartItems.setProduct(this);
        return this;
    }

    public Product removeCartItems(CartItems cartItems) {
        this.cartItems.remove(cartItems);
        cartItems.setProduct(null);
        return this;
    }

    public void setCartItems(Set<CartItems> cartItems) {
        this.cartItems = cartItems;
    }

    public Set<OrderItems> getOrderItems() {
        return orderItems;
    }

    public Product orderItems(Set<OrderItems> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public Product addOrderItems(OrderItems orderItems) {
        this.orderItems.add(orderItems);
        orderItems.setProduct(this);
        return this;
    }

    public Product removeOrderItems(OrderItems orderItems) {
        this.orderItems.remove(orderItems);
        orderItems.setProduct(null);
        return this;
    }

    public void setOrderItems(Set<OrderItems> orderItems) {
        this.orderItems = orderItems;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", minimumQuantity=" + getMinimumQuantity() +
            ", pricePerUnit=" + getPricePerUnit() +
            ", active='" + isActive() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
