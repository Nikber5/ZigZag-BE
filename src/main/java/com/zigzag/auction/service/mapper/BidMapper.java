package com.zigzag.auction.service.mapper;

import com.zigzag.auction.dto.response.BidResponseDto;
import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.User;
import org.springframework.stereotype.Component;

@Component
public class BidMapper implements ResponseDtoMapper<BidResponseDto, Bid> {
    private final UserMapper userMapper;

    public BidMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public BidResponseDto mapToDto(Bid bid) {
        BidResponseDto dto = new BidResponseDto();
        dto.setId(bid.getId());
        dto.setBidSum(bid.getBidSum());
        dto.setBetTime(bid.getBetTime());

        User owner = bid.getOwner();
        dto.setOwnerId(owner.getId());
        dto.setOwnerName(userMapper.getName(owner));
        return dto;
    }
}
