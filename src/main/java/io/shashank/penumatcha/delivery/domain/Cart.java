package io.shashank.penumatcha.delivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_updated")
    private ZonedDateTime lastUpdated;

    @OneToOne    @JoinColumn(unique = true)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "cart",cascade=CascadeType.REMOVE,fetch=FetchType.LAZY)
    private Set<CartItems> cartItems = new HashSet<>();
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

    public Cart lastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Cart userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<CartItems> getCartItems() {
        return cartItems;
    }

    public Cart cartItems(Set<CartItems> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public Cart addCartItems(CartItems cartItems) {
        this.cartItems.add(cartItems);
        cartItems.setCart(this);
        return this;
    }

    public Cart removeCartItems(CartItems cartItems) {
        this.cartItems.remove(cartItems);
        cartItems.setCart(null);
        return this;
    }

    public void setCartItems(Set<CartItems> cartItems) {
        this.cartItems = cartItems;
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
        Cart cart = (Cart) o;
        if (cart.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
