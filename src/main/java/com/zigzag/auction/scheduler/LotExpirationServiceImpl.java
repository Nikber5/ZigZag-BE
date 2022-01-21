package com.zigzag.auction.scheduler;

import com.zigzag.auction.model.Lot;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.util.DateTimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LotExpirationServiceImpl {
    private final LotService lotService;

    public LotExpirationServiceImpl(LotService lotService) {
        this.lotService = lotService;
    }

    @Scheduled(fixedDelay = 60000)
    public void checkLots() {
        //System.out.println("Scheduled job ran at " + LocalDateTime.now());
        Pageable pageRequest = PageRequest.of(0, 20);
        //System.out.println("Perform query");
        Page<Long> lotsPage = lotService
                .getExpiredWithBidsWithPagination(pageRequest, DateTimeUtil.getCurrentUtcLocalDateTime());

        while (!lotsPage.isEmpty()) {
            pageRequest = pageRequest.next();
            lotService.getLotsByIds(lotsPage.getContent()).forEach(this::disableLotIfNeeded);
            lotsPage = lotService
                    .getExpiredWithBidsWithPagination(pageRequest, DateTimeUtil.getCurrentUtcLocalDateTime());
        }
        //System.out.println("Empty page ");
    }

    private void disableLotIfNeeded(Lot lot) {
        //System.out.println("Checking lot with id: " + lot.getId() + " " + lot);
        if (!lotService.isValid(lot)) {
            lotService.closeLot(lot);
        }
    }
}
