package com.zigzag.auction.model;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
public class User extends AbstractEntity {
    private String firstName;
    private String secondName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "owner")
    private List<Product> products;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Lot> lots;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Bid> bids;

    public User() {
    }

    public User(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    @Override
    public String toString() {
        return "User{"
                + "id='" + super.getId() + '\''
                + ", firstName='" + firstName + '\''
                + ", secondName='" + secondName + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", products=" + products + '\''
                + ", bids="
                + bids
                + '}';
    }
}
