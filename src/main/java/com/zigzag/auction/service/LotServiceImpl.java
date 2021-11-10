package com.zigzag.auction.service;

import com.zigzag.auction.model.Lot;
import com.zigzag.auction.repository.LotRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public Lot create(Lot lot) {
        return repository.save(lot);
    }
}
