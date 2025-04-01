package com.skitech.api.dto;

import com.skitech.api.model.Listing;

public class ApiResponse {
    private String message;
    private Listing listing;

    public ApiResponse(String message, Listing listing) {
        this.message = message;
        this.listing = listing;
    }

    public String getMessage() {
        return message;
    }

    public Listing getListing() {
        return listing;
    }
}

