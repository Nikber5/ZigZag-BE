package com.zigzag.auction.service.mapper;

import com.zigzag.auction.dto.response.LotResponseDto;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import org.springframework.stereotype.Component;

@Component
public class LotMapper implements ResponseDtoMapper<LotResponseDto, Lot> {
    @Override
    public LotResponseDto mapToDto(Lot lot) {
        LotResponseDto dto = new LotResponseDto();
        dto.setId(lot.getId());

        User creator = lot.getCreator();
        dto.setCreatorId(creator.getId());
        dto.setCreatorName(getName(creator));

        Product product = lot.getProduct();
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setProductDescription(product.getDescription());

        dto.setStartDate(lot.getStartDate());
        dto.setEndDate(lot.getEndDate());
        dto.setStartPrice(lot.getStartPrice());
        dto.setActive(lot.isActive());

        User winner = lot.getWinner();
        if (winner != null) {
            dto.setWinnerId(winner.getId());
            dto.setWinnerName(getName(winner));
        }

        return dto;
    }

    private String getName(User user) {
        String name = "";
        String firstName = user.getFirstName();
        if (firstName != null && !firstName.equals("")) {
            name += firstName + " ";
        }
        String secondName = user.getSecondName();
        if (secondName != null && !secondName.equals("")) {
            name += secondName;
        }
        return name.equals("") ? null : name.trim();
    }
}
