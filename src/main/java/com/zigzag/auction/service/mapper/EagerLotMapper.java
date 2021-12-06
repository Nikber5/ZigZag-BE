package com.zigzag.auction.service.mapper;

import com.zigzag.auction.dto.response.EagerLotResponseDto;
import com.zigzag.auction.model.Lot;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EagerLotMapper implements ResponseDtoMapper<EagerLotResponseDto, Lot> {
    private final LotMapper lotMapper;
    private final BidMapper bidMapper;

    public EagerLotMapper(LotMapper lotMapper, BidMapper bidMapper) {
        this.lotMapper = lotMapper;
        this.bidMapper = bidMapper;
    }

    @Override
    public EagerLotResponseDto mapToDto(Lot lot) {
        EagerLotResponseDto dto = new EagerLotResponseDto();
        dto = (EagerLotResponseDto) lotMapper.populateDto(dto, lot);
        dto.setBids(lot.getBids()
                .stream()
                .map(bidMapper::mapToDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
