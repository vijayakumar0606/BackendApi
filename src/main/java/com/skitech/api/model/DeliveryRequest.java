package com.skitech.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

//import lombok.Getter;
//import lombok.Setter;

//@Getter
//@Setter
public class DeliveryRequest {
	@NotNull(message = "listingId cannot be null")
	@JsonProperty("listingId")  // Ensure JSON maps to this field
    private Long listingId;
	@JsonProperty("deliveryPassword")
    private String deliveryPassword;
	@JsonProperty("deliveryPerson")
    private String deliveryPerson;
	@JsonProperty("productName")
    private String productName;
	public Long getListingId() {
		return listingId;
	}
	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	public String getDeliveryPassword() {
		return deliveryPassword;
	}
	public void setDeliveryPassword(String deliveryPassword) {
		this.deliveryPassword = deliveryPassword;
	}
	public String getDeliveryPerson() {
		return deliveryPerson;
	}
	public void setDeliveryPerson(String deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}