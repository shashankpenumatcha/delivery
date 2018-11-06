package io.shashank.penumatcha.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer")
    private Boolean customer;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name="phone_number")
    private String phoneNumber;

    @OneToOne    @JoinColumn(unique = true)
    @JsonIgnoreProperties({"id","password","langKey","activated","resetDate"})
    private User user;

    @OneToMany(mappedBy = "userProfile",fetch= LAZY) @JsonIgnoreProperties("userProfile")
    private Set<OrderList> orderLists = new HashSet<>();
    @OneToMany(mappedBy = "userProfile",fetch= LAZY) @JsonIgnoreProperties("userProfile")
    private Set<InventoryLog> inventoryLogs = new HashSet<>();
    @OneToMany(mappedBy = "userProfile",fetch= LAZY) @JsonIgnoreProperties("userProfile")
    private Set<OrderTracker> orderTrackers = new HashSet<>();
    @OneToOne(mappedBy = "userProfile",fetch= LAZY)   @JsonIgnoreProperties("userProfile")
    private Cart cart;

    @OneToMany(mappedBy = "userProfile",fetch= LAZY) @JsonIgnoreProperties("userProfile")
    private Set<UserAddress> userAddresses = new HashSet<>();

    @OneToMany(mappedBy = "userProfile",fetch= LAZY) @JsonIgnoreProperties("userProfile")
    private Set<FcmToken> fcmTokens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCustomer() {
        return customer;
    }

    public UserProfile customer(Boolean customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public UserProfile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderList> getOrderLists() {
        return orderLists;
    }

    public UserProfile orderLists(Set<OrderList> orderLists) {
        this.orderLists = orderLists;
        return this;
    }

    public UserProfile addOrderList(OrderList orderList) {
        this.orderLists.add(orderList);
        orderList.setUserProfile(this);
        return this;
    }

    public UserProfile removeOrderList(OrderList orderList) {
        this.orderLists.remove(orderList);
        orderList.setUserProfile(null);
        return this;
    }

    public void setOrderLists(Set<OrderList> orderLists) {
        this.orderLists = orderLists;
    }

    public Set<InventoryLog> getInventoryLogs() {
        return inventoryLogs;
    }

    public UserProfile inventoryLogs(Set<InventoryLog> inventoryLogs) {
        this.inventoryLogs = inventoryLogs;
        return this;
    }

    public UserProfile addInventoryLog(InventoryLog inventoryLog) {
        this.inventoryLogs.add(inventoryLog);
        inventoryLog.setUserProfile(this);
        return this;
    }

    public UserProfile removeInventoryLog(InventoryLog inventoryLog) {
        this.inventoryLogs.remove(inventoryLog);
        inventoryLog.setUserProfile(null);
        return this;
    }

    public void setInventoryLogs(Set<InventoryLog> inventoryLogs) {
        this.inventoryLogs = inventoryLogs;
    }

    public Set<OrderTracker> getOrderTrackers() {
        return orderTrackers;
    }

    public UserProfile orderTrackers(Set<OrderTracker> orderTrackers) {
        this.orderTrackers = orderTrackers;
        return this;
    }

    public UserProfile addOrderTracker(OrderTracker orderTracker) {
        this.orderTrackers.add(orderTracker);
        orderTracker.setUserProfile(this);
        return this;
    }

    public UserProfile removeOrderTracker(OrderTracker orderTracker) {
        this.orderTrackers.remove(orderTracker);
        orderTracker.setUserProfile(null);
        return this;
    }

    public void setOrderTrackers(Set<OrderTracker> orderTrackers) {
        this.orderTrackers = orderTrackers;
    }

    public Cart getCart() {
        return cart;
    }

    public UserProfile cart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<UserAddress> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(Set<UserAddress> userAddresses) {
        this.userAddresses = userAddresses;
    }

    public Set<FcmToken> getFcmTokens() {
        return fcmTokens;
    }

    public void setFcmTokens(Set<FcmToken> fcmTokens) {
        this.fcmTokens = fcmTokens;
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
        UserProfile userProfile = (UserProfile) o;
        if (userProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", customer='" + isCustomer() + "'" +
            "}";
    }
}
