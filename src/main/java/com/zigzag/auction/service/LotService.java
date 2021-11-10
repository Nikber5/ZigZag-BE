package com.zigzag.auction.service;

import com.zigzag.auction.model.Lot;
import java.util.List;

public interface LotService {
    List<Lot> getAll();

    Lot get(Long id);

    Lot create(Lot lot);

    Lot update(Lot lot);
}
