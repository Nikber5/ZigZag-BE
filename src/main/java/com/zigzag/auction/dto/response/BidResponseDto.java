package com.zigzag.auction.dto.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class BidResponseDto {
    private Long id;
    private LocalDateTime betTime;
    private BigInteger bidSum;
    private Long ownerId;
    private String ownerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
