package io.shashank.penumatcha.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A OrderTracker.
 */
@Entity
@Table(name = "order_tracker")
public class OrderTracker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @ManyToOne
    @JsonIgnoreProperties("orderTrackers,orderItems")
    private OrderList orderList;

    @ManyToOne
    @JsonIgnoreProperties("orderTrackers")
    private OrderStatus orderStatus;

    @ManyToOne
    @JsonIgnoreProperties("orderTrackers, cart")
    private UserProfile userProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public OrderTracker dateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    public OrderTracker orderList(OrderList orderList) {
        this.orderList = orderList;
        return this;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderTracker orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public OrderTracker userProfile(UserProfile userProfile) {
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
        OrderTracker orderTracker = (OrderTracker) o;
        if (orderTracker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderTracker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderTracker{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}
