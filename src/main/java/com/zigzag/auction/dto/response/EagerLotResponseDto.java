package com.zigzag.auction.dto.response;

import java.util.List;

public class EagerLotResponseDto extends LotResponseDto {
    private List<BidResponseDto> bids;

    public List<BidResponseDto> getBids() {
        return bids;
    }

    public void setBids(List<BidResponseDto> bids) {
        this.bids = bids;
    }
}
