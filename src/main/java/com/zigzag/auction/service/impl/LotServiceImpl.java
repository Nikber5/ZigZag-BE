package com.zigzag.auction.service.impl;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.repository.LotRepository;
import com.zigzag.auction.repository.ProductRepository;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.util.DateTimeUtil;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LotServiceImpl implements LotService {
    private final LotRepository repository;
    private final ProductRepository productRepository;

    public LotServiceImpl(LotRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Lot> getAll() {
        return repository.findAll();
    }

    @Override
    public Lot get(Long id) {
        return repository.findLotByIdWithBids(id)
                .orElseThrow(() -> new DataProcessingException("Can't get lot by id: " + id));
    }

    @Override
    public Lot create(Lot lot) {
        return repository.save(lot);
    }

    @Override
    public Lot update(Lot lot) {
        return repository.save(lot);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Lot> getAllWithPagination(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public boolean isValid(Lot lot) {
        if (lot.getEndDate().isBefore(DateTimeUtil.getCurrentUtcLocalDateTime())
                && lot.getActive()) {
            closeLot(lot);
            return false;
        }
        return true;
    }

    @Override
    public void closeLot(Lot lot) {
        lot.setActive(Boolean.FALSE);
        Optional<Bid> highestBid = lot.getBids()
                .stream()
                .max(Comparator.comparing(Bid::getBidSum));
        highestBid.ifPresent(bid -> lot.setWinner(bid.getOwner()));
        if (highestBid.isPresent()) {
            highestBid.ifPresent(bid -> lot.getProduct().setOwner(bid.getOwner()));
            lot.getProduct().setOwner(highestBid.get().getOwner());
        } else {
            lot.getProduct().setOwner(lot.getCreator());
        }
        productRepository.save(lot.getProduct());
        repository.save(lot);
    }

    @Override
    public Page<Long> getAllWithBidsWithPagination(Pageable pageable, LocalDateTime now) {
        return repository.getExpiredLotsIds(pageable, now);
    }

    @Override
    public List<Lot> getLotsByIds(List<Long> ids) {
        return repository.getLotsWithBidsByIds(ids);
    }
}
