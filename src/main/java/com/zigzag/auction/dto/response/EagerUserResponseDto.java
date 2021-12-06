package com.zigzag.auction.dto.response;

import java.util.List;

public class EagerUserResponseDto extends UserResponseDto {
    private List<ProductResponseDto> products;
    private List<LotResponseDto> lots;
    private List<BidResponseDto> bids;

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDto> products) {
        this.products = products;
    }

    public List<LotResponseDto> getLots() {
        return lots;
    }

    public void setLots(List<LotResponseDto> lots) {
        this.lots = lots;
    }

    public List<BidResponseDto> getBids() {
        return bids;
    }

    public void setBids(List<BidResponseDto> bids) {
        this.bids = bids;
    }
}
