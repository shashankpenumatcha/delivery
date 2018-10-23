package io.shashank.penumatcha.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrderStatus.
 */
@Entity
@Table(name = "order_status")
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "orderStatus",fetch=FetchType.LAZY)
    private Set<OrderTracker> orderTrackers = new HashSet<>();
    @OneToMany(mappedBy = "orderStatus",fetch=FetchType.LAZY)
    private Set<OrderList> orderLists = new HashSet<>();
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

    public OrderStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<OrderTracker> getOrderTrackers() {
        return orderTrackers;
    }

    public OrderStatus orderTrackers(Set<OrderTracker> orderTrackers) {
        this.orderTrackers = orderTrackers;
        return this;
    }

    public OrderStatus addOrderTracker(OrderTracker orderTracker) {
        this.orderTrackers.add(orderTracker);
        orderTracker.setOrderStatus(this);
        return this;
    }

    public OrderStatus removeOrderTracker(OrderTracker orderTracker) {
        this.orderTrackers.remove(orderTracker);
        orderTracker.setOrderStatus(null);
        return this;
    }

    public void setOrderTrackers(Set<OrderTracker> orderTrackers) {
        this.orderTrackers = orderTrackers;
    }

    public Set<OrderList> getOrderLists() {
        return orderLists;
    }

    public OrderStatus orderLists(Set<OrderList> orderLists) {
        this.orderLists = orderLists;
        return this;
    }

    public OrderStatus addOrderList(OrderList orderList) {
        this.orderLists.add(orderList);
        orderList.setOrderStatus(this);
        return this;
    }

    public OrderStatus removeOrderList(OrderList orderList) {
        this.orderLists.remove(orderList);
        orderList.setOrderStatus(null);
        return this;
    }

    public void setOrderLists(Set<OrderList> orderLists) {
        this.orderLists = orderLists;
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
        OrderStatus orderStatus = (OrderStatus) o;
        if (orderStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
