package com.skitech.api.model;

import com.skitech.api.dto.PictureDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class Picture {

    @Override
	public String toString() {
		return "Picture [id=" + id + ", fileName=" + fileName + ", listing=" + listing + "]";
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;
    
    //@Version  // Ensures Hibernate tracks version changes
    //private Integer version;
    //private Integer version = 0; // Default to 0 to prevent null issues
    
    //@Column(nullable = false)
    private Integer version = 1;  // Default version to 1

    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/*public Integer getOptimisticLockVersion() {
		return optimisticLockVersion;
	}

	public void setOptimisticLockVersion(Integer optimisticLockVersion) {
		this.optimisticLockVersion = optimisticLockVersion;
	}*/

	//@Version
    //private Integer optimisticLockVersion; 
    
    // Constructors
    public Picture() {}

    public Picture(String fileName, Listing listing) {
        this.fileName = fileName;
        this.listing = listing;
    }
    
    public Picture(PictureDTO pictures) {
    	if (pictures != null) {
	        this.id = pictures.getId();
	        this.fileName = pictures.getFileName();
	    }
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}


}

