package com.skitech.api.model;

import com.skitech.api.dto.LocationDTO;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {
    private Double latitude;
    public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	private Double longitude;

    // Constructors
    public Location() {}

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

	public Location(LocationDTO location) {
		if (location != null) {
	        this.latitude = location.getLatitude();
	        this.longitude = location.getLongitude();
	    }
	}
}
