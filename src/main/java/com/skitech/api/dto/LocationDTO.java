package com.skitech.api.dto;

import com.skitech.api.model.Location;

public class LocationDTO {
    private double latitude;
    private double longitude;

    public LocationDTO(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
