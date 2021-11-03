package com.zigzag.auction.service;

import com.zigzag.auction.model.Lot;
import com.zigzag.auction.repository.LotRepository;
import org.springframework.stereotype.Service;

@Service
public class LotServiceImpl implements LotService {
    private final LotRepository repository;

    public LotServiceImpl(LotRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lot create(Lot lot) {
        return repository.save(lot);
    }
}
