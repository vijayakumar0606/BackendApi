package com.skitech.api.service;

import com.skitech.api.model.Listing;
import com.skitech.api.model.Location;
import com.skitech.api.model.Region;
import com.skitech.api.repository.ListingRepository;
import com.skitech.api.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private RegionRepository regionRepository;

    // Create a new Listing in a specific Region
    public Listing saveListing(Long regionId, Listing listing) {
        return regionRepository.findById(regionId).map(region -> {
            listing.setRegion(region);
            return listingRepository.save(listing);
        }).orElse(null);
    }

    // Get all Listings in a specific Region
    public List<Listing> getListingsByRegion(Long regionId) {
        return listingRepository.findByRegionId(regionId);
    }

    // Get a Listing by ID in a specific Region
    public Optional<Listing> getListingById(Long regionId, Long listingId) {
        return listingRepository.findByIdAndRegionId(listingId, regionId);
    }

    // Update a Listing by ID in a specific Region
    /*public Optional<Listing> updateListing(Long regionId, Long listingId, Listing listingDetails) {
        return listingRepository.findByIdAndRegionId(listingId, regionId).map(existingListing -> {
            existingListing.setName(listingDetails.getName());
            existingListing.setPhoneNumber(listingDetails.getPhoneNumber());
            existingListing.setIndividual(listingDetails.getIndividual());
            existingListing.setDoorNumber(listingDetails.getDoorNumber());
            existingListing.setFloorNumber(listingDetails.getFloorNumber());
            existingListing.setPicture(listingDetails.getPicture());
            existingListing.setVideo(listingDetails.getVideo());
            existingListing.setComment(listingDetails.getComment());
            
            // Update location
            if (existingListing.getLocation() != null) {
                existingListing.setLocation(existingListing.getLocation());
            }
            
            return listingRepository.save(existingListing);
        });
    }*/
    
    @Transactional
    public Listing updateListing(Long regionId, Long listingId, Listing updatedListing) {
        Listing existingListing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        existingListing.setName(updatedListing.getName());
        existingListing.setPhoneNumber(updatedListing.getPhoneNumber());
        existingListing.setIndividual(updatedListing.getIndividual());
        existingListing.setDoorNumber(updatedListing.getDoorNumber());
        existingListing.setFloorNumber(updatedListing.getFloorNumber());
        existingListing.setPicture(updatedListing.getPicture());
        existingListing.setVideo(updatedListing.getVideo());
        existingListing.setComment(updatedListing.getComment());

        // Ensure location updates
        if (updatedListing.getLocation() != null) {
            if (existingListing.getLocation() == null) {
                existingListing.setLocation(new Location());
            }
            existingListing.getLocation().setLatitude(updatedListing.getLocation().getLatitude());
            existingListing.getLocation().setLongitude(updatedListing.getLocation().getLongitude());
        }

        return listingRepository.save(existingListing);
    }


    // Delete a Listing by ID
    public boolean deleteListing(Long listingId) {
        return listingRepository.findById(listingId).map(listing -> {
            listingRepository.delete(listing);
            return true;
        }).orElse(false);
    }
}
