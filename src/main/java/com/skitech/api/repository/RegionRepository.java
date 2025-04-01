package com.skitech.api.repository;

import com.skitech.api.dto.RegionDTO;
import com.skitech.api.model.Listing;
import com.skitech.api.model.Region;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByName(String name);
    
    //@Query("SELECT r FROM Region r LEFT JOIN FETCH r.listings")
    //List<Region> findAllWithListings();

	//Region findByIdWithListings(Long id);
    
    @Query("SELECT r FROM Region r LEFT JOIN FETCH r.listings WHERE r.id = :id")
    Region findByIdWithListings(@Param("id") Long id);
    
    @Query("SELECT r FROM Region r LEFT JOIN FETCH r.listings")
    List<Region> findAllWithListings();
    
    @Query("SELECT r FROM Region r LEFT JOIN FETCH r.listings WHERE r.id = :id")
    Optional<Region> findByIdWithListings1(Long id); // ✅ This should return Optional<Region>
    
    /*@Query("SELECT DISTINCT r FROM Region r " +
            "LEFT JOIN FETCH r.listings l " +
            "LEFT JOIN FETCH l.pictures p")
     List<Region> findAllWithListingsAndPictures();*/
    
    @Query("SELECT DISTINCT r FROM Region r " +
    	       "LEFT JOIN FETCH r.listings l " +
    	       "LEFT JOIN FETCH l.pictures")
    	List<Region> findAllWithListingsAndPictures();
    
    /*@EntityGraph(attributePaths = {"listings", "listings.picturess"})
    @Query("SELECT r FROM Region r")
    List<Region> findAllWithListingsAndPictures();*/
    
    
    
}

