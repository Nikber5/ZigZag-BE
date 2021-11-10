package com.zigzag.auction.service.impl;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.repository.LotRepository;
import com.zigzag.auction.service.LotService;
import java.util.List;
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
}
