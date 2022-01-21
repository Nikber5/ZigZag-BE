package com.zigzag.auction.service.impl;

import com.zigzag.auction.dto.response.LotResponseDto;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.LikeService;
import com.zigzag.auction.service.UserService;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private final UserService userService;

    public LikeServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public LotResponseDto populateLikes(LotResponseDto dto, Authentication auth) {
        if (auth == null || auth.getPrincipal() == null) {
            dto.setLiked(false);
            return dto;
        }
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.getUserWithLikedLots(details.getUsername());

        populateLike(dto, user.getLikedLots());
        return dto;
    }

    @Override
    public Page<LotResponseDto> populateLikes(Page<LotResponseDto> dtoPage, Authentication auth) {
        System.out.println("---------------- Start populating -----------------");
        if (auth == null || auth.getPrincipal() == null) {
            return dtoPage.map(dto -> {
                dto.setLiked(false);
                return dto;
            });
        }
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.getUserWithLikedLots(details.getUsername());

        for (LotResponseDto dto : dtoPage) {
            populateLike(dto, user.getLikedLots());
        }
        System.out.println("---------------- END populating -----------------");

        return dtoPage;
    }

    private void populateLike(LotResponseDto dto, Set<Lot> likedLots) {
        for (Lot likedLot : likedLots) {
            if (likedLot.getId().equals(dto.getId())) {
                dto.setLiked(true);
                return;
            }
        }

        dto.setLiked(false);
    }
}
