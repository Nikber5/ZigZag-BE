package com.zigzag.auction.repository;

import com.zigzag.auction.model.Lot;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LotRepository extends JpaRepository<Lot, Long> {
    @Query("SELECT l FROM Lot l LEFT JOIN FETCH l.bids WHERE l.id = :id")
    Optional<Lot> findLotByIdWithBids(Long id);

    @Query(value = "SELECT l.id FROM Lot l WHERE l.endDate < :now",
            countQuery = "SELECT count(l.id) FROM Lot l WHERE l.endDate < :now")
    Page<Long> getExpiredLotsIds(Pageable pageable, LocalDateTime now);

    @Query(value = "SELECT l FROM Lot l LEFT JOIN FETCH l.bids WHERE l.id IN (:ids)")
    List<Lot> getLotsByIds(List<Long> ids);
}
