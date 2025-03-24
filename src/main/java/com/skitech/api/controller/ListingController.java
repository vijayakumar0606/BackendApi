package com.skitech.api.controller;

import com.skitech.api.model.Listing;
import com.skitech.api.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    // Create Listing in a Specific Region
    @PostMapping("/{regionId}")
    public ResponseEntity<Listing> createListing(@PathVariable Long regionId, @RequestBody Listing listing) {
        Listing savedListing = listingService.saveListing(regionId, listing);
        return savedListing != null ? ResponseEntity.ok(savedListing) : ResponseEntity.badRequest().build();
    }

    // Get All Listings in a Specific Region
    @GetMapping("/region/{regionId}")
    public List<Listing> getListingsByRegion(@PathVariable Long regionId) {
        return listingService.getListingsByRegion(regionId);
    }

    // Get a Specific Listing by ID within a Region
    @GetMapping("/region/{regionId}/listing/{listingId}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long regionId, @PathVariable Long listingId) {
        Optional<Listing> listing = listingService.getListingById(regionId, listingId);
        return listing.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update a Listing by ID within a Region
    @PutMapping("/region/{regionId}/listing/{listingId}")
    public ResponseEntity<Listing> updateListing(
            @PathVariable Long regionId, 
            @PathVariable Long listingId, 
            @RequestBody Listing listingDetails) {
        
        //Optional<Listing> updatedListing = listingService.updateListing(regionId, listingId, listingDetails);     
        //return updatedListing.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    	Listing updatedListing = listingService.updateListing(regionId, listingId, listingDetails);
        return updatedListing != null ? ResponseEntity.ok(updatedListing) : ResponseEntity.notFound().build();
    }

    // Delete a Listing by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteListing(@PathVariable Long id) {
        return listingService.deleteListing(id)
                ? ResponseEntity.ok("Listing deleted successfully")
                : ResponseEntity.notFound().build();
    }
    
    /*@PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id, @RequestBody Listing listing) {
        Listing updatedListing = listingService.updateListing(id, listing);
        return updatedListing != null ? ResponseEntity.ok(updatedListing) : ResponseEntity.notFound().build();
    }*/
}
