package com.zigzag.auction.repository;

import com.zigzag.auction.model.Lot;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LotRepository extends JpaRepository<Lot, Long> {
    @Query("SELECT l FROM Lot l LEFT JOIN FETCH l.bids WHERE l.id = :id")
    Optional<Lot> findLotByIdWithBids(Long id);
}
