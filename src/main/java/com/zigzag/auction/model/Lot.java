package com.zigzag.auction.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
public class Lot extends AbstractEntity {
    @ManyToOne
    private User creator;
    @OneToOne
    private Product product;
    private LocalDateTime startDate ;
    private LocalDateTime endDate;
    private BigInteger startPrice;
    private Boolean isActive;
    @ManyToOne
    private User winner;
    //List<Bid> bids

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }
}
