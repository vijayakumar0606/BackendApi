package com.skitech.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.skitech.api.dto.ListingDTO;
import com.skitech.api.dto.RegionDTO;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "listings"})
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Region name is required")
    private String name;
    
    /*@OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // ✅ Prevents infinite recursion
    //private List<Listing> listings;
    private List<Listing> listings = new ArrayList<>(); */
    
    
    //@OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonManagedReference  // ✅ Prevents infinite recursion
    //private List<Listing> listings;
    
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@JsonManagedReference
    private List<Listing> listings = new ArrayList<>();
    
    // ✅ No-arg constructor (REQUIRED by Hibernate)
    public Region() {
    }

    // ✅ Parameterized constructor (useful for DTO mapping)
    public Region(String name) {
        this.name = name;
    }
    
    
	public Region(RegionDTO regionDTO) {
		this.id = regionDTO.getId();   // Ensure 'id' is handled correctly if it's being set externally
	    this.name = regionDTO.getName();
	    
	    if (regionDTO.getListings() != null) {
	        this.listings = regionDTO.getListings().stream()
	                .map(Listing::new) // Convert each ListingDTO into a Listing entity
	                .collect(Collectors.toList());
	    }
	}
	// Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Listing> getListings() { return listings; }
    //public void setListings(List<Listing> listings) { this.listings = listings; }
    
    public void setListings(List<Listing> listings) {
        this.listings.clear();
        if (listings != null) {
            this.listings.addAll(listings);
        }
    }

	public Region orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
