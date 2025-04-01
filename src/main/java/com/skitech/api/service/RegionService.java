package com.skitech.api.service;

import com.skitech.api.dto.RegionDTO;
import com.skitech.api.model.Listing;
import com.skitech.api.model.Picture;
import com.skitech.api.model.Region;
import com.skitech.api.repository.PictureRepository;
import com.skitech.api.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private PictureService pictureService;

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
    /*
    //public List<RegionDTO> getAllRegions() {    
    	public List<Region> getAllRegions() {
        //List<Region> regions = regionRepository.findAllWithListings();
        //return regions.stream().map(RegionDTO::new).collect(Collectors.toList());
    	/*System.out.println("Before RegionService.getAllRegions()");
        List<Region> regions = regionRepository.findAllWithListingsAndPictures();
        System.out.println("Region Result : "+regions);
        //System.out.println("Listing ID: " + regions.get(0).getListings().get(0).getPicturess()); 
        System.out.println("Listing ID: " + regions.get(2).getListings().get(12).getId() + " Pictures: " + regions.get(2).getListings().get(12).getPicturess());
        //return regions.stream().map(RegionDTO::new).collect(Collectors.toList());
        
        
        System.out.println("Fetched Regions:");
        for (Region region : regions) {
            System.out.println("Region ID: " + region.getId() + ", Name: " + region.getName());

            for (Listing listing : region.getListings()) {
                System.out.println("  Listing ID: " + listing.getId() + ", Name: " + listing.getName());

                if (listing.getPictures() != null) {
                    for (Picture picture : listing.getPicturess()) {
                        System.out.println("    Picture ID: " + picture.getId() + ", FileName: " + picture.getFileName());
                    }
                } else {
                    System.out.println("    No pictures available.");
                }
            }
        }****\
        
        
        List<Region> regions = regionRepository.findAllWithListings();

        List<Long> listingIds = regions.stream()
                .flatMap(region -> region.getListings().stream())
                .map(Listing::getId)
                .collect(Collectors.toList());

        List<Listing> listingsWithPictures = pictureRepository.findAllWithPictures(listingIds);
        
        Map<Long, Listing> listingMap = listingsWithPictures.stream()
                .collect(Collectors.toMap(Listing::getId, listing -> listing));

        for (Region region : regions) {
            for (Listing listing : region.getListings()) {
            	System.out.println("    Picture ID: " + listing.getId() + ", Name: " + listing.getName());
            	
            	System.out.println("    status: " + listing.getStatus() + ", FileName: " + listing.getPicturess());
            	
            	
            	listing.setId(listing.getId());
            	listing.setName(listing.getName());
                listing.setPictures(listingMap.get(listing.getId()).getPictures());
            }
            //region.setListings(listing);
        }
        
        return regions;
        /*return regions.stream()
                .map(region -> new RegionDTO(
                    region.getId(),
                    region.getName(),
                    region.getListings().stream()
                        .map(listing -> new ListingDTO(
                            listing.getId(),
                            listing.getName(),
                            listing.getPhoneNumber(),
                            listing.getIndividual(),
                            listing.getDoorNumber(),
                            listing.getFloorNumber(),
                            listing.getComment(),
                            listing.getVideo(),
                            listing.getStatus(),
                            listing.getLocation(),
                            listing.getPicturess() != null  // Ensure no NullPointerException
                                ? listing.getPicturess().stream()
                                    .map(picture -> "http://localhost:8080/api/images/display/" + picture.getFileName())
                                    .collect(Collectors.toList())
                                : new ArrayList<>()
                        ))
                        .collect(Collectors.toList())  // Collect Listings into a list
                ))
                .collect(Collectors.toList());  // Collect Regions into a list***\
    }*/
    
    public List<Map<String, Object>> getAllRegionsWithListings() {
        List<Region> regions = regionRepository.findAllWithListingsAndPictures();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Region region : regions) {
            Map<String, Object> regionMap = new LinkedHashMap<>();
            regionMap.put("id", region.getId());
            regionMap.put("name", region.getName());

            List<Map<String, Object>> listingsList = new ArrayList<>();

            for (Listing listing : region.getListings()) {
                Map<String, Object> listingMap = new LinkedHashMap<>();
                listingMap.put("id", listing.getId());
                listingMap.put("name", listing.getName());
                listingMap.put("phoneNumber", listing.getPhoneNumber());
                listingMap.put("individual", listing.getIndividual());
                listingMap.put("doorNumber", listing.getDoorNumber());
                listingMap.put("floorNumber", listing.getFloorNumber());
                listingMap.put("comment", listing.getComment());
                //listingMap.put("status", listing.getStatus().name()); // Convert Enum to String
                listingMap.put("status", listing.getStatus() != null ? listing.getStatus().name() : "PENDING");
                listingMap.put("video", listing.getVideo());

                // Adding Location Object
                Map<String, Object> locationMap = new LinkedHashMap<>();
                locationMap.put("latitude", listing.getLocation().getLatitude());
                locationMap.put("longitude", listing.getLocation().getLongitude());
                listingMap.put("location", locationMap);

                // Converting Pictures to an Array of URLs
                /*List<String> pictureUrls = listing.getPicturess().stream()
                        .map(Picture::getFileName) // Assuming fileName contains the URL
                        .collect(Collectors.toList());*/
                
             // Fetch Latest Version of Picture URLs
                List<String> pictureUrls = pictureService.getLatestPictureUrls(listing.getId());

                listingMap.put("pictures", pictureUrls);
                listingsList.add(listingMap);
            }
            
            regionMap.put("listings", listingsList);
            
            //System.out.println("regionMap : "+regionMap);
            response.add(regionMap);
        }

        return response;
    }
 
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }
    
    public Region getRegionWithListings1(Long id) {
        return regionRepository.findByIdWithListings1(id)
                .orElseThrow(() -> new RuntimeException("Region not found with id: " + id)); // Fixed error
    }
    
    public RegionDTO getRegionWithListings2(Long id) {
    	Region fetchedRegion = regionRepository.findByIdWithListings(id);
    	return new RegionDTO(fetchedRegion);
    }
    
}
