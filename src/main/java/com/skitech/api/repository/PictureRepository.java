package com.skitech.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skitech.api.dto.ListingDTO;
import com.skitech.api.model.Listing;
import com.skitech.api.model.Picture;

import jakarta.transaction.Transactional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {    
	// Retrieve all pictures for a given listing ID
    List<Picture> findByListingId(Long listingId);
    
    List<Picture> findByListing(Listing listing);
    
    @Query("SELECT DISTINCT l FROM Listing l LEFT JOIN FETCH l.pictures WHERE l.id IN :listingIds")
    List<Listing> findAllWithPictures(@Param("listingIds") List<Long> listingIds);
    
    void deleteByListing(Listing listing);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Picture p WHERE p.listing.id = :listingId")
    void deleteByListingId(@Param("listingId") Long listingId);
    
    /*@Transactional
    @Modifying
    @Query("DELETE FROM Picture p WHERE p.listing.id = :listingId")
    void deleteAllByListingId(Long listingId);*/
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Picture p WHERE p.listing.id = :listingId")
    void deleteAllByListingId(@Param("listingId") Long listingId);
    
    List<Picture> findByListingIdOrderByVersionDesc(Long listingId);
    
    @Query("SELECT p FROM Picture p WHERE p.listing.id = :listingId AND p.version = (SELECT MAX(p2.version) FROM Picture p2 WHERE p2.listing.id = :listingId)")
    List<Picture> findLatestVersionPictures(@Param("listingId") Long listingId);
}