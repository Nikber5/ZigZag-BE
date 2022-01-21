package com.zigzag.auction.service;

import com.zigzag.auction.dto.response.LotResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface LikeService {
    LotResponseDto populateLikes(LotResponseDto dto, Authentication auth);

    Page<LotResponseDto> populateLikes(Page<LotResponseDto> dtoPage, Authentication auth);
}
