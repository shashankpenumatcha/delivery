package io.shashank.penumatcha.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

/**
 * A OrderItems.
 */
@Entity
@Table(name = "order_items")
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"orderItems","quantity"})
    private Product product;

    @ManyToOne()
    @JsonIgnoreProperties({"orderItems","lastUpdated","orderTrackers","orderStatus","userProfile"})
    private OrderList orderList;

    private Long quantity;

    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public OrderItems product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    public OrderItems orderList(OrderList orderList) {
        this.orderList = orderList;
        return this;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
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
        OrderItems orderItems = (OrderItems) o;
        if (orderItems.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderItems.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderItems{" +
            "id=" + getId() +
            "}";
    }
}
