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
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name="phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "vendor",fetch= LAZY) @JsonIgnoreProperties("vendor")
    private Set<UserProfile> userProfiles = new HashSet<>();

    @OneToMany(mappedBy = "vendor",fetch= LAZY) @JsonIgnoreProperties("vendor")
    private Set<InventoryLog> inventoryLogs = new HashSet<>();

    @OneToMany(mappedBy = "vendor",fetch= LAZY) @JsonIgnoreProperties("vendor")
    private Set<OrderList> orderLists  = new HashSet<>();

    @OneToMany(mappedBy = "vendor",fetch= LAZY) @JsonIgnoreProperties("vendor")
    private Set<Cart> carts = new HashSet<>();

    @OneToMany(mappedBy = "vendor",fetch= LAZY) @JsonIgnoreProperties("vendor")
    private Set<Product> products = new HashSet<>();

    @OneToOne(mappedBy = "vendor",fetch= LAZY) @JsonIgnoreProperties("vendor")
    private UserAddress userAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Vendor userAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
        return this;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
    public Vendor addUserProfiles(UserProfile userProfile) {
        this.userProfiles.add(userProfile);
        userProfile.setVendor(this);
        return this;
    }

    public Vendor removeUserProfiles(UserProfile userProfile) {
        this.userProfiles.remove(userProfile);
        userProfile.setVendor(null);
        return this;
    }

    public Set<InventoryLog> getInventoryLogs() {
        return inventoryLogs;
    }

    public void setInventoryLogs(Set<InventoryLog> inventoryLogs) {
        this.inventoryLogs = inventoryLogs;
    }

    public Vendor addInventoryLogs(InventoryLog inventoryLog) {
        this.inventoryLogs.add(inventoryLog);
        inventoryLog.setVendor(this);
        return this;
    }

    public Vendor removeInventoryLogs(InventoryLog inventoryLog) {
        this.inventoryLogs.remove(inventoryLog);
        inventoryLog.setVendor(null);
        return this;
    }

    public Set<OrderList> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(Set<OrderList> orderLists) {
        this.orderLists = orderLists;
    }
    public Vendor addOrderLists(OrderList orderList) {
        this.orderLists.add(orderList);
        orderList.setVendor(this);
        return this;
    }

    public Vendor removeOrderLists(OrderList orderList) {
        this.orderLists.remove(orderList);
        orderList.setVendor(null);
        return this;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public Vendor addCarts(Cart cart) {
        this.carts.add(cart);
        cart.setVendor(this);
        return this;
    }

    public Vendor removeCarts(Cart cart) {
        this.carts.remove(cart);
        cart.setVendor(null);
        return this;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    public Vendor addProducts(Product product) {
        this.products.add(product);
        product.setVendor(this);
        return this;
    }

    public Vendor removeProducts(Product product) {
        this.products.remove(product);
        product.setVendor(null);
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
            "}";
    }
}
