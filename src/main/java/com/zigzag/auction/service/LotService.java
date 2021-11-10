package com.zigzag.auction.service;

import com.zigzag.auction.model.Lot;
import java.util.List;

public interface LotService {
    List<Lot> getAll();

    Lot create(Lot lot);
}
