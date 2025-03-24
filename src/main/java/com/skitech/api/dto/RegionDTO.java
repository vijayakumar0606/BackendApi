package com.skitech.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.skitech.api.model.Region;

public class RegionDTO {
    private Long id;
    private String name;
    private List<ListingDTO> listings;  // Include Listings DTO

    public RegionDTO(Region region) {
        this.id = region.getId();
        this.name = region.getName();
        this.listings = region.getListings().stream()
                .map(ListingDTO::new)
                .collect(Collectors.toList());
    }
    
 // ✅ No-arg constructor (REQUIRED for Jackson deserialization)
    public RegionDTO() {
    }

    // ✅ Constructor with parameters (optional)
    public RegionDTO(String name) {
        this.name = name;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ListingDTO> getListings() {
		return listings;
	}

	public void setListings(List<ListingDTO> listings) {
		this.listings = listings;
	}
}
