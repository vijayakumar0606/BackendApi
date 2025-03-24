package com.skitech.api.repository;

import com.skitech.api.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByRegionId(Long regionId);
    Optional<Listing> findByIdAndRegionId(Long listingId, Long regionId);
}
