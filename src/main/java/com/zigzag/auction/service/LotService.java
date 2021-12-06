package com.zigzag.auction.service;

import com.zigzag.auction.model.Lot;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LotService extends AbstractCrudService<Lot, Long> {
    Page<Lot> getAllWithPagination(Pageable pageable);

    boolean isValid(Lot lot);

    void closeLot(Lot lot);

    Page<Long> getAllWithBidsWithPagination(Pageable pageable, LocalDateTime now);

    List<Lot> getLotsByIds(List<Long> ids);
}
