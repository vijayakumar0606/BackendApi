package com.skitech.api.dto;

import com.skitech.api.model.Picture;

public class PictureDTO {
    private Long id;
    private String fileName;

    // Constructors
    //public PictureDTO(Picture picture) {}

    public PictureDTO(Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public PictureDTO(Picture pictures) {
    	 this.id = pictures.getId();
         this.fileName = pictures.getFileName();
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

}

