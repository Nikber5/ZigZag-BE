package com.zigzag.auction.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Lot extends AbstractEntity {
    @ManyToOne
    @JoinColumn(nullable = false)
    private User creator;
    @OneToOne
    private Product product;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigInteger startPrice;
    private Boolean isActive;
    @ManyToOne
    private User winner;
    @OneToMany(mappedBy = "lot")
    private List<Bid> bids;

    public Lot() {
    }

    public Lot(User owner, Product product, LocalDateTime startDate,
               LocalDateTime endDate, BigInteger startPrice, Boolean isActive) {
        this.creator = owner;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPrice = startPrice;
        this.isActive = isActive;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigInteger getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigInteger startPrice) {
        this.startPrice = startPrice;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
