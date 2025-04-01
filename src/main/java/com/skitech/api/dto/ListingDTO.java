package com.skitech.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.skitech.api.model.Listing;
import com.skitech.api.repository.DeliveryStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ListingDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String individual;
    private String doorNumber;
    private String floorNumber;
    //private String pictures;
    private String video;
    private String comment;
    private LocationDTO location;
    private PictureDTO pictures;
    private List<String> picturess;
    
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.PENDING;
    
    public DeliveryStatus getStatus() {
		return status;
	}

	public List<String> getPicturess() {
		return picturess;
	}

	public void setPicturess(List<String> picturess) {
		this.picturess = picturess;
	}

	public void setStatus(DeliveryStatus inProgress) {
		this.status = inProgress;
	}
	
    // Constructor
    public ListingDTO(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getName();
        this.phoneNumber = listing.getPhoneNumber();
        this.individual = listing.getIndividual();
        this.doorNumber = listing.getDoorNumber();
        this.floorNumber = listing.getFloorNumber();
        //this.pictures = listing.getPictures();
        this.video = listing.getVideo();
        this.comment = listing.getComment();
        this.location = new LocationDTO(listing.getLocation());
        this.pictures = new PictureDTO(listing.getPictures());
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

	/*public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}*/

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

	public PictureDTO getPictures() {
		return pictures;
	}

	public void setPictures(PictureDTO pictures) {
		this.pictures = pictures;
	}
	
}
