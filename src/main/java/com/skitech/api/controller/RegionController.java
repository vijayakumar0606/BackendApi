package com.skitech.api.controller;

import com.skitech.api.dto.RegionDTO;
import com.skitech.api.model.Region;
import com.skitech.api.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;
    /*
    @PostMapping
    public Region createRegion(@RequestBody Region region) {
        return regionService.saveRegion(region);
    }*/
    
    @PostMapping
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO regionDTO) {
        RegionDTO savedRegion = regionService.createRegion(regionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegion);
    }
    
    /*
    @GetMapping
    public List<Region> getAllRegions() {
        return regionService.getAllRegions();
    }*/
    
    /*@GetMapping
    public ResponseEntity<List<Region>> getAllRegions() {
    	System.out.println("Inside RegionController.getAllRegions() ...");
        //List<RegionDTO> regions = regionService.getAllRegions();
    	List<Region> regions = regionService.getAllRegions();
        System.out.println("GetMapping : "+regions);
        return ResponseEntity.ok(regions);
    }*/
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllRegions() {
        List<Map<String, Object>> regionsResponse = regionService.getAllRegionsWithListings();
        return ResponseEntity.ok(regionsResponse);
    }
    
    /* // Fetch only region details
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
        return regionService.getRegionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionWithListings(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.getRegionWithListings1(id));
    }*/
    
    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getRegionById(@PathVariable Long id) {
    	RegionDTO savedRegion = regionService.getRegionWithListings2(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegion);
    }
    
 // Update a region
    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable Long id, @RequestBody Region updatedRegion) {
    	//public ResponseEntity<Optional<Region>> updateRegion(@PathVariable Long id, @RequestBody Region updatedRegion) {
       /* return regionService.updateRegion(id, updatedRegion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); */
    	
    	//Optional<Region> region = regionService.updateRegion(id, updatedRegion);
    	Region region = regionService.updateRegion(id, updatedRegion);
        return region != null ? ResponseEntity.ok(region) : ResponseEntity.notFound().build();
    }

    // Delete a region
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegion(@PathVariable Long id) {
        boolean deleted = regionService.deleteRegion(id);
        if (deleted) {
            //return ResponseEntity.noContent().build()
            return ResponseEntity.ok("Region deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }    
       
    }
    
}
