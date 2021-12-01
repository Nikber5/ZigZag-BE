package com.zigzag.auction.service;

import com.zigzag.auction.model.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LotService extends AbstractCrudService<Lot, Long> {
    Page<Lot> getAllWithPagination(Pageable pageable);
}
