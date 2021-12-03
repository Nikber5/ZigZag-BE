package com.zigzag.auction.service.mapper;

import com.zigzag.auction.dto.response.LotResponseDto;
import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LotMapper implements ResponseDtoMapper<LotResponseDto, Lot> {
    private final UserMapper userMapper;
    private final BidMapper bidMapper;

    public LotMapper(UserMapper userMapper, BidMapper bidMapper) {
        this.userMapper = userMapper;
        this.bidMapper = bidMapper;
    }

    @Override
    public LotResponseDto mapToDto(Lot lot) {
        LotResponseDto dto = new LotResponseDto();
        dto.setId(lot.getId());

        User creator = lot.getCreator();
        dto.setCreatorId(creator.getId());
        dto.setCreatorName(userMapper.getName(creator));

        Product product = lot.getProduct();
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setProductDescription(product.getDescription());

        dto.setStartDate(lot.getStartDate());
        dto.setEndDate(lot.getEndDate());
        dto.setStartPrice(lot.getStartPrice());
        dto.setHighestPrice(lot.getHighestPrice());
        dto.setActive(lot.getActive());

        User winner = lot.getWinner();
        if (winner != null) {
            dto.setWinnerId(winner.getId());
            dto.setWinnerName(userMapper.getName(winner));
        }

        List<Bid> bids = lot.getBids();
        if (bids != null) {
            dto.setBets(bids.stream()
                    .map(bidMapper::mapToDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
