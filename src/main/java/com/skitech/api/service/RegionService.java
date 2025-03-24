package com.skitech.api.service;

import com.skitech.api.dto.RegionDTO;
import com.skitech.api.model.Listing;
import com.skitech.api.model.Region;
import com.skitech.api.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }
    
    /*@Transactional
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }*/

    public Optional<Region> getRegionByName(String name) {
        return regionRepository.findByName(name);
    }

    public Optional<Region> getRegionById(Long id) {
        return regionRepository.findById(id);
    }

 // Update a region
    @Transactional
    public Region updateRegion(Long id, Region updatedRegion) {
    	//public Optional<Region> updateRegion(Long id, Region updatedRegion) {	
        /*return regionRepository.findById(id).map(existingRegion -> {
            existingRegion.setName(updatedRegion.getName());
            existingRegion.setListings(updatedRegion.getListings()); // Assuming region has listings
            return regionRepository.save(existingRegion);
        });*/
    	
    	/*
    	return regionRepository.findById(id).map(existingRegion -> {
            existingRegion.setName(updatedRegion.getName());

            // Clear existing listings and add new ones without losing reference
            //existingRegion.getListings().clear();
            
            // ✅ Ensure listings is not null before modifying
            if (existingRegion.getListings() == null) {
            	existingRegion.setListings(new ArrayList<>());
            } else {
            	existingRegion.getListings().clear(); // Clear old listings
            }

            // ✅ Add new listings
            for (Listing listing : updatedRegion.getListings()) {
                listing.setRegion(existingRegion);
                existingRegion.getListings().add(listing);
            }

            return regionRepository.save(existingRegion);
        });*/
    	
    	Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region not found"));

        region.setName(updatedRegion.getName());
        
        // Ensure listings are loaded properly before returning
        region.getListings().size(); // This forces Hibernate to load listings
        
        return regionRepository.save(region);
    	
    }

    // Delete a region
    public boolean deleteRegion(Long id) {
        return regionRepository.findById(id).map(region -> {
            regionRepository.delete(region);
            return true;
        }).orElse(false);
    }
    
    public RegionDTO createRegion(RegionDTO regionDTO) {
        //Region region = new Region(regionDTO);  // Convert DTO to Entity
        Region region = new Region(regionDTO); 
        region = regionRepository.save(region);
        
        // Fetch the region along with listings
        Region fetchedRegion = regionRepository.findByIdWithListings(region.getId());
        
        return new RegionDTO(fetchedRegion);
    }
    
    public List<RegionDTO> getAllRegions() {
        List<Region> regions = regionRepository.findAllWithListings();
        return regions.stream().map(RegionDTO::new).collect(Collectors.toList());
    }
    
    
 
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }
    
    public Region getRegionWithListings1(Long id) {
        return regionRepository.findByIdWithListings1(id)
                .orElseThrow(() -> new RuntimeException("Region not found with id: " + id)); // Fixed error
    }
    
}
