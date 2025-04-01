package com.skitech.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.skitech.api.dto.ListingDTO;
import com.skitech.api.dto.PictureDTO;
import com.skitech.api.repository.DeliveryStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Listing {

 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
	public String toString() {
		return "Listing [id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", individual=" + individual
				+ ", doorNumber=" + doorNumber + ", floorNumber=" + floorNumber + ", pictures=" + pictures + ", video="
				+ video + ", comment=" + comment + ", location=" + location + ", region=" + region + ", status="
				+ status + "]";
	}

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
    //private String picture;
    
    //@Lob
    //private String pictures; // Store multiple image URLs as a comma-separated string
    
    //@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Picture> pictures = new ArrayList<>();
    //private Picture pictures;
    //@OneToMany(mappedBy = "listing", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonManagedReference  // ✅ Prevents infinite recursion
    //private List<Picture> pictures;
    
    @ManyToOne
    @JoinColumn(name="picture_id")
    @JsonBackReference
    //@Embedded
    private Picture pictures;    

    public Picture getPictures() {
		return pictures;
	}
	public void setPictures(Picture pictures) {
		this.pictures = pictures;
	}
	
	//@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Picture> picturess = new ArrayList<>(); // 🔹 Initialize the list
	
	/*@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Picture> pictures_name=new ArrayList<>(); // Properly mapped One-to-Many

	public List<Picture> getPictures_name() {
		return pictures_name;
	}
	public void setPictures_name(List<Picture> pictures_name) {
		this.pictures_name = pictures_name;
	}*/

	public List<Picture> getPicturess() {
		if (picturess == null) {
            picturess = new ArrayList<>(); // 🔹 Ensure it's initialized
        }
		return picturess;
	}
	public void setPicturess(List<Picture> picturess) {
		this.picturess = picturess;
	}
	
	public void addPicturess(Picture picture) {
        if (this.picturess == null) {
            this.picturess = new ArrayList<>(); // 🔹 Ensure it's initialized
        }
        this.picturess.add(picture);
        picture.setListing(this); // Ensure bidirectional mapping
    }

	private String video;
    private String comment;
    
    @Embedded
    private Location location;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @JsonBackReference
    //@JoinColumn(name = "region_id", nullable = false)
  
    private Region region;
    
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.PENDING;
    
    public DeliveryStatus getStatus() {
		//return status;
    	return status != null ? status : DeliveryStatus.PENDING;
	}

	public void setStatus(DeliveryStatus inProgress) {
		this.status = inProgress;
	}

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

    /*public String getPictures() { return pictures; }
    public void setPictures(String pictures) { this.pictures = pictures; }*/

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
        //this.pictures = listingDTO.getPictures();
        this.video = listingDTO.getVideo();
        this.comment = listingDTO.getComment();
        
        this.status=listingDTO.getStatus();
        
        if (listingDTO.getLocation() != null) {
            this.location = new Location(listingDTO.getLocation()); // Convert LocationDTO to Location entity
        }
        
        if (listingDTO.getPictures() != null) {
            this.pictures = new Picture(listingDTO.getPictures()); // Convert LocationDTO to Location entity
        }
        //this.picturess = new ArrayList<>();
    }
    
 // ✅ No-arg constructor (REQUIRED by Hibernate)
    public Listing() {
    	this.picturess = new ArrayList<>(); // 🔹 Ensure it's initialized
    }
    /*
    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", individual='" + individual + '\'' +
                ", doorNumber='" + doorNumber + '\'' +
                ", floorNumber='" + floorNumber + '\'' +
                ", video='" + video + '\'' +
                ", comment='" + comment + '\'' +
                ", location=" + (location != null ? location.toString() : "null") +
                ", region=" + (region != null ? region.getId() : "null") +
                ", pictures=" + pictures +
                '}';
    }*/

}
