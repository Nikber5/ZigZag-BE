package com.zigzag.auction.service.impl;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.repository.LotRepository;
import com.zigzag.auction.service.LotService;
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

    public LotServiceImpl(LotRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Lot> getAll() {
        return repository.findAll();
    }

    @Override
    public Lot get(Long id) {
        return repository.findById(id)
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
        if (lot.getEndDate().isBefore(LocalDateTime.now())) {
            lot.setActive(Boolean.FALSE);
            Optional<Bid> highestBid = lot.getBids()
                    .stream()
                    .max(Comparator.comparing(Bid::getBidSum));
            highestBid.ifPresent(bid -> lot.setWinner(bid.getOwner()));
            repository.save(lot);
            return false;
        }
        return true;
    }
}
