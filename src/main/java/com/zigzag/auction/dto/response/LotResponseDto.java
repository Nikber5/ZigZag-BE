package com.zigzag.auction.dto.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class LotResponseDto {
    private Long id;
    private Long creatorId;
    private String creatorName;
    private Long productId;
    private String productName;
    private String productDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigInteger startPrice;
    private BigInteger highestPrice;
    private Boolean isActive;
    private Long winnerId;
    private String winnerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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

    public BigInteger getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigInteger highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
