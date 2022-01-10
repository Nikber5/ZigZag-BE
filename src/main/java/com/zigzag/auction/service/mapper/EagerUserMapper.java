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
    private final UserMapper userMapper;

    public EagerUserMapper(ProductMapper productMapper, LotMapper lotMapper,
                           BidMapper bidMapper, UserMapper userMapper) {
        this.productMapper = productMapper;
        this.lotMapper = lotMapper;
        this.bidMapper = bidMapper;
        this.userMapper = userMapper;
    }

    @Override
    public EagerUserResponseDto mapToDto(User user) {
        EagerUserResponseDto dto = new EagerUserResponseDto();
        dto = (EagerUserResponseDto) userMapper.populateDto(dto, user);

        dto.setLots(user.getLots().stream()
                .map(lotMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setProducts(user.getProducts().stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setBids(user.getBids().stream()
                .map(bidMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setLikedLots(user.getLikedLots().stream()
                .map(lotMapper::mapToDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
