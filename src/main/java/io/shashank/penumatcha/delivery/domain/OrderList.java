package io.shashank.penumatcha.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrderList.
 */
@Entity
@Table(name = "order_list")
public class OrderList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_updated")
    private ZonedDateTime lastUpdated;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "total")
    private Float total;

    @OneToMany(mappedBy = "orderList",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("orderLists")
    private Set<OrderItems> orderItems = new HashSet<>();
    @OneToMany(mappedBy = "orderList", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("orderLists")
    @JsonIgnore
    private Set<OrderTracker> orderTrackers = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("orderLists")
    private OrderStatus orderStatus;

    @ManyToOne
    @JsonIgnoreProperties({"orderLists","inventoryLogs","orderTrackers","cart", "fcmTokens"})
    private UserProfile userProfile;

    @Column(name="created_date")
    private ZonedDateTime createdDate;


    @OneToMany(mappedBy = "orderList", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("orderLists")
    @JsonIgnore
    private Set<InventoryLog> inventoryLogs = new HashSet<>();


    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnoreProperties("orderLists")
    private Vendor vendor;

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
    public OrderList vendor(Vendor vendor){
        this.vendor = vendor;
        return this;
    }

    public Set<InventoryLog> getInventoryLogs() {
        return inventoryLogs;
    }

    public void setInventoryLogs(Set<InventoryLog> inventoryLogs) {
        this.inventoryLogs = inventoryLogs;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public OrderList lastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<OrderItems> getOrderItems() {
        return orderItems;
    }

    public OrderList orderItems(Set<OrderItems> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public OrderList addOrderItems(OrderItems orderItems) {
        this.orderItems.add(orderItems);
        orderItems.setOrderList(this);
        return this;
    }

    public OrderList removeOrderItems(OrderItems orderItems) {
        this.orderItems.remove(orderItems);
        orderItems.setOrderList(null);
        return this;
    }

    public void setOrderItems(Set<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public Set<OrderTracker> getOrderTrackers() {
        return orderTrackers;
    }

    public OrderList orderTrackers(Set<OrderTracker> orderTrackers) {
        this.orderTrackers = orderTrackers;
        return this;
    }

    public OrderList addOrderTracker(OrderTracker orderTracker) {
        this.orderTrackers.add(orderTracker);
        orderTracker.setOrderList(this);
        return this;
    }

    public OrderList removeOrderTracker(OrderTracker orderTracker) {
        this.orderTrackers.remove(orderTracker);
        orderTracker.setOrderList(null);
        return this;
    }

    public void setOrderTrackers(Set<OrderTracker> orderTrackers) {
        this.orderTrackers = orderTrackers;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderList orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public OrderList userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
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
        OrderList orderList = (OrderList) o;
        if (orderList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderList{" +
            "id=" + getId() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
