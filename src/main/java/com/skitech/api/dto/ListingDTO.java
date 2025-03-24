package com.skitech.api.dto;

import com.skitech.api.model.Listing;

public class ListingDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String individual;
    private String doorNumber;
    private String floorNumber;
    private String picture;
    private String video;
    private String comment;
    private LocationDTO location;

    // Constructor
    public ListingDTO(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getName();
        this.phoneNumber = listing.getPhoneNumber();
        this.individual = listing.getIndividual();
        this.doorNumber = listing.getDoorNumber();
        this.floorNumber = listing.getFloorNumber();
        this.picture = listing.getPicture();
        this.video = listing.getVideo();
        this.comment = listing.getComment();
        this.location = new LocationDTO(listing.getLocation());
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIndividual() {
		return individual;
	}

	public void setIndividual(String individual) {
		this.individual = individual;
	}

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}
}
