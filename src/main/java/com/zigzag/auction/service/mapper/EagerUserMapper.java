package com.zigzag.auction.service.mapper;

import com.zigzag.auction.dto.response.EagerUserResponseDto;
import com.zigzag.auction.model.User;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EagerUserMapper implements ResponseDtoMapper<EagerUserResponseDto, User> {
    private final ProductMapper productMapper;
    private final LotMapper lotMapper;
    private final BidMapper bidMapper;

    public EagerUserMapper(ProductMapper productMapper, LotMapper lotMapper,
                           BidMapper bidMapper) {
        this.productMapper = productMapper;
        this.lotMapper = lotMapper;
        this.bidMapper = bidMapper;
    }

    @Override
    public EagerUserResponseDto mapToDto(User user) {
        EagerUserResponseDto dto = new EagerUserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setSecondName(user.getSecondName());

        dto.setLots(user.getLots().stream()
                .map(lotMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setProducts(user.getProducts().stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setBids(user.getBids().stream()
                .map(bidMapper::mapToDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
