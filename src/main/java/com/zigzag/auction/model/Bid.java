package com.zigzag.auction.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
public class Bid extends AbstractEntity {
    private LocalDateTime betTime;
    private BigInteger bidSum;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User owner;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Lot lot;

    public Bid() {
    }

    public Bid(LocalDateTime betTime, BigInteger bidSum) {
        this.betTime = betTime;
        this.bidSum = bidSum;
    }

    public LocalDateTime getBetTime() {
        return betTime;
    }

    public void setBetTime(LocalDateTime betTime) {
        this.betTime = betTime;
    }

    public BigInteger getBidSum() {
        return bidSum;
    }

    public void setBidSum(BigInteger bidSum) {
        this.bidSum = bidSum;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id='" + super.getId() + '\'' +
                ", betTime=" + betTime +
                ", bidSum=" + bidSum +
                '}';
    }
}
