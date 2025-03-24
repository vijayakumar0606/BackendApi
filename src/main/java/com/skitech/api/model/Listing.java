package com.skitech.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.skitech.api.dto.ListingDTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Individual status is required")
    private String individual;

    private String doorNumber;
    private String floorNumber;
    //private Double latitude;
    //private Double longitude;
    private String picture;
    private String video;
    private String comment;
    
    @Embedded
    private Location location;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @JsonBackReference
    //@JoinColumn(name = "region_id", nullable = false)
  
    private Region region;

    public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	// Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getIndividual() { return individual; }
    public void setIndividual(String individual) { this.individual = individual; }

    public String getDoorNumber() { return doorNumber; }
    public void setDoorNumber(String doorNumber) { this.doorNumber = doorNumber; }

    public String getFloorNumber() { return floorNumber; }
    public void setFloorNumber(String floorNumber) { this.floorNumber = floorNumber; }

    /*public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }*/

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    
    public Listing(ListingDTO listingDTO) {
        this.id = listingDTO.getId();
        this.name = listingDTO.getName();
        this.phoneNumber = listingDTO.getPhoneNumber();
        this.individual = listingDTO.getIndividual();
        this.doorNumber = listingDTO.getDoorNumber();
        this.floorNumber = listingDTO.getFloorNumber();
        this.picture = listingDTO.getPicture();
        this.video = listingDTO.getVideo();
        this.comment = listingDTO.getComment();
        
        if (listingDTO.getLocation() != null) {
            this.location = new Location(listingDTO.getLocation()); // Convert LocationDTO to Location entity
        }
    }
    
 // ✅ No-arg constructor (REQUIRED by Hibernate)
    public Listing() {
    }

}
