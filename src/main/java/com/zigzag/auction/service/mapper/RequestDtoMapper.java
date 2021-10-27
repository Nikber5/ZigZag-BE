package com.zigzag.auction.service.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
