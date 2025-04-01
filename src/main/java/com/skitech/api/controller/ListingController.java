package com.skitech.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skitech.api.dto.ApiResponse;
import com.skitech.api.model.Listing;
import com.skitech.api.model.Location;
import com.skitech.api.model.Picture;
import com.skitech.api.repository.PictureRepository;
import com.skitech.api.service.ListingService;
import com.skitech.api.service.PictureService;
import com.skitech.api.service.S3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.skitech.api.repository.DeliveryStatus;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;
    private final S3Service s3Service;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private PictureService pictureService;
    
    public ListingController(ListingService listingService, S3Service s3Service) {
        this.listingService = listingService;
        this.s3Service = s3Service;
    }

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
    
    @PutMapping("/region/{regionId}/listing/{listingId}/status")
    public ResponseEntity<String> updateListingStatus(
    //public ResponseEntity<ApiResponse> updateListingStatus(
            @PathVariable Long regionId, 
            @PathVariable Long listingId, 
            @RequestBody Map<String, Integer> requestBody) {

        int status = requestBody.get("status");  // Extract status from JSON request body
        Listing updatedListing = listingService.updateListingStatus(regionId, listingId, status);
        //return updatedListing != null ? ResponseEntity.ok(updatedListing) : ResponseEntity.notFound().build();
        return updatedListing != null ? ResponseEntity.ok("Status updated successfully") : ResponseEntity.notFound().build();
        
        //ApiResponse response = new ApiResponse("Status updated successfully", updatedListing);
        //return ResponseEntity.ok(response);
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
    
    
    @PostMapping("/add/{regionId}")
    //public ResponseEntity<Listing> createListing(
    public ResponseEntity<Map<String, Object>> createListing(
            @PathVariable Long regionId,
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("individual") String individual,
            @RequestParam(value = "doorNumber", required = false) String doorNumber,
            @RequestParam(value = "floorNumber", required = false) String floorNumber,
            @RequestParam(value = "comment", required = false) String comment,
            //@RequestParam(value = "picture", required = false) MultipartFile picture,
            @RequestParam(value = "pictures", required = false) MultipartFile[] pictures,
            @RequestParam(value = "video", required = false) MultipartFile video,
            @RequestParam(value = "location", required = false) String locationJson
    ) throws IOException {
    	
    	 // Convert JSON location string to Location object
        ObjectMapper objectMapper = new ObjectMapper();
        Location location = null;
        if (locationJson != null && !locationJson.isEmpty()) {
            location = objectMapper.readValue(locationJson, Location.class);
        }
        
        
        // Step 1: Create Listing object
        Listing listing = new Listing();
        listing.setName(name);
        listing.setPhoneNumber(phoneNumber);
        listing.setIndividual(individual);
        listing.setDoorNumber(doorNumber);
        listing.setFloorNumber(floorNumber);
        listing.setComment(comment);
        listing.setLocation(location);
        listing.setStatus(DeliveryStatus.PENDING);
        
        // Step 2: Save listing first
        Listing savedListing = listingService.saveListing(regionId, listing);
        
        if (savedListing == null) {
            return ResponseEntity.badRequest().build();
        }
        
        System.out.println("regionId : "+regionId);
        System.out.println("savedListing : "+listing.toString());
        
        /*// Upload files to S3 (if provided)
        if (picture != null && !picture.isEmpty()) {
            listing.setPictures(s3Service.uploadFile(picture));
        }*/
        
        // Upload multiple images to S3 (if provided)
        List<String> pictureUrls = new ArrayList<>();
        
        // Step 3: Upload images and save Picture entities
        if (pictures != null && pictures.length > 0) {
            for (MultipartFile picture : pictures) {
                if (!picture.isEmpty()) {
                    String pictureUrl = s3Service.uploadFile(picture);
                    pictureUrls.add(pictureUrl);
                    //System.out.println("picture : "+picture);
                    //System.out.println("pictureUrl : "+pictureUrl);
                    Picture pic = new Picture();
                    pic.setFileName(pictureUrl);
                    pic.setListing(savedListing); // Associate with saved Listing
                    pictureRepository.save(pic);
                    //savedListing.addPicturess(pic);
                    //System.out.println("Saved Picture: " + pic.toString());              
                    
                }
            }
        }
        System.out.println("Pictures: "+String.join(",", pictureUrls));
        //listing.setPictures(String.join(",", pictureUrls)); // Store URLs as comma-separated string - add API
        
        /*// Upload video (if provided)
        if (video != null && !video.isEmpty()) {
        	System.out.println("video : "+video.toString());
        	savedListing.setVideo(s3Service.uploadFile(video));
        }*/
        
        // Step 4: Upload video (if provided) and update listing
        if (video != null && !video.isEmpty()) {
            String videoUrl = s3Service.uploadFile(video);
            savedListing.setVideo(videoUrl);
        }
        
        
        
        // Step 5: Save updated listing (with video and images associated)
        Listing finalListing = listingService.saveListing(regionId, savedListing);

        System.out.println("Final listing : " + finalListing.toString());
        //return ResponseEntity.ok(finalListing);
        
        // Step 6: Construct the Response Map
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", savedListing.getId());
        response.put("name", savedListing.getName());
        response.put("phoneNumber", savedListing.getPhoneNumber());
        response.put("individual", savedListing.getIndividual());
        response.put("doorNumber", savedListing.getDoorNumber());
        response.put("floorNumber", savedListing.getFloorNumber());
        response.put("video", savedListing.getVideo());
        response.put("comment", savedListing.getComment());
        response.put("location", savedListing.getLocation());
        response.put("status", savedListing.getStatus());
        response.put("pictures", pictureUrls);
        
        System.out.println("Response: " + response);
        
        // Step 7: Fetch all pictures associated with the listing
        List<String> storedPictures = pictureRepository.findByListing(savedListing).stream()
                .map(Picture::getFileName)
                .collect(Collectors.toList());
        
        //System.out.println("storedPictures : "+storedPictures);
        // Save Listing
        //Listing savedListing = listingService.saveListing(regionId, listing);
        //return finalListing != null ? ResponseEntity.ok(finalListing) : ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);        
    }    
    
    	/*@PutMapping(value = "/region/{regionId}/listing/edit/{listingId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    	public ResponseEntity<Map<String, Object>> updateListing(
            @PathVariable Long regionId,
            @PathVariable Long listingId,
            @RequestPart("listings") String listingJson, // JSON data
            @RequestPart(value = "pictures", required = false) MultipartFile[] pictures,
            @RequestPart(value = "video", required = false) MultipartFile video) throws IOException {*/
    	
    	@PutMapping("/region/{regionId}/listing/edit/{listingId}")
        public ResponseEntity<Map<String, Object>> updateListing(
                @PathVariable Long regionId,
                @PathVariable Long listingId,
                @RequestParam("name") String name,
                @RequestParam("phoneNumber") String phoneNumber,
                @RequestParam("individual") String individual,
                @RequestParam(value = "doorNumber", required = false) String doorNumber,
                @RequestParam(value = "floorNumber", required = false) String floorNumber,
                @RequestParam(value = "comment", required = false) String comment,
                @RequestParam(value = "pictures", required = false) MultipartFile[] pictures,
                @RequestParam(value = "video", required = false) MultipartFile video,
                @RequestParam(value = "location", required = false) String locationJson) throws IOException {

        /*// Convert JSON string to Listing object
        ObjectMapper objectMapper = new ObjectMapper();
        Listing listingDetails = objectMapper.readValue(listingJson, Listing.class);*/

        // Fetch existing listing
        Listing existingListing = listingService.getListingByIdAndRegion(regionId, listingId);
        if (existingListing == null) {
            return ResponseEntity.notFound().build();
        }        
        
        /*// Update listing details
        existingListing.setName(listingDetails.getName());
        existingListing.setPhoneNumber(listingDetails.getPhoneNumber());
        existingListing.setIndividual(listingDetails.getIndividual());
        existingListing.setDoorNumber(listingDetails.getDoorNumber());
        existingListing.setFloorNumber(listingDetails.getFloorNumber());
        existingListing.setComment(listingDetails.getComment());    
        existingListing.setStatus(listingDetails.getStatus() != null ? listingDetails.getStatus() : DeliveryStatus.PENDING);

        // Update location if provided
        if (listingDetails.getLocation() != null) {
            existingListing.setLocation(listingDetails.getLocation());
        }*/
        
     // Update listing details        
        existingListing.setName(name);
        existingListing.setPhoneNumber(phoneNumber);
        existingListing.setIndividual(individual);
        existingListing.setDoorNumber(doorNumber);
        existingListing.setFloorNumber(floorNumber);
        existingListing.setComment(comment);    
        //existingListing.setStatus(listingDetails.getStatus() != null ? listingDetails.getStatus() : DeliveryStatus.PENDING);
        
        // Convert JSON location string to Location object
        ObjectMapper objectMapper = new ObjectMapper();
        Location location = null;
        if (locationJson != null && !locationJson.isEmpty()) {
            location = objectMapper.readValue(locationJson, Location.class);
        }
        existingListing.setLocation(location);
        /*// Update location if provided
        if (location != null) {
            existingListing.setLocation(listingDetails.getLocation());
        }*/
        
        // Upload and update pictures
        List<String> pictureUrls = new ArrayList<>();
        if (pictures != null && pictures.length > 0) {
            //pictureRepository.deleteByListing(existingListing); // Remove old pictures
        	//pictureRepository.deleteByListingId(listingId); // Remove old pictures
        	
        	//pictureService.deletePicturesByListingId(listingId); // Remove old pictures
        	
        	List<Picture> existingPictures = pictureRepository.findByListingIdOrderByVersionDesc(listingId);
            
            int latestVersion = existingPictures.isEmpty() ? 1 : existingPictures.get(0).getVersion() + 1;

        	
            for (MultipartFile picture : pictures) {
                if (!picture.isEmpty()) {
                    String pictureUrl = s3Service.uploadFile(picture);
                    pictureUrls.add(pictureUrl);

                    Picture pic = new Picture();
                    pic.setFileName(pictureUrl);
                    pic.setListing(existingListing);
                    pic.setVersion(latestVersion);
                    pictureRepository.save(pic);
                }
            }
        }

        // Upload and update video
        if (video != null && !video.isEmpty()) {
            String videoUrl = s3Service.uploadFile(video);
            existingListing.setVideo(videoUrl);
        }

        // Save updated listing
        Listing updatedListing = listingService.saveListing(regionId, existingListing);

        // Prepare response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", updatedListing.getId());
        response.put("name", updatedListing.getName());
        response.put("phoneNumber", updatedListing.getPhoneNumber());
        response.put("individual", updatedListing.getIndividual());
        response.put("doorNumber", updatedListing.getDoorNumber());
        response.put("floorNumber", updatedListing.getFloorNumber());
        response.put("comment", updatedListing.getComment());
        response.put("video", updatedListing.getVideo());
        response.put("status", updatedListing.getStatus());
        response.put("location", updatedListing.getLocation());
        response.put("pictures", pictureUrls);

        return ResponseEntity.ok(response);
    }

}
