package com.skitech.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skitech.api.model.Delivery;
import com.skitech.api.model.DeliveryRequest;
import com.skitech.api.model.Listing;
import com.skitech.api.repository.DeliveryRepository;
import com.skitech.api.repository.DeliveryStatus;
import com.skitech.api.repository.ListingRepository;

@Service
//@RequiredArgsConstructor
public class DeliveryService {
    
    //private final DeliveryRepository deliveryRepository = null;
    //private final ListingRepository listingRepository = null;

	private final DeliveryRepository deliveryRepository;
    private final ListingRepository listingRepository;

    @Autowired // Ensure dependencies are properly injected
    public DeliveryService(DeliveryRepository deliveryRepository, ListingRepository listingRepository) {
        this.deliveryRepository = deliveryRepository;
        this.listingRepository = listingRepository;
    }
    
    public Delivery beginDelivery(Long listingId, String deliveryPersonName, String productName, String password) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        
        Delivery delivery = new Delivery();
        delivery.setListing(listing);
        delivery.setDeliveryPersonName(deliveryPersonName);
        delivery.setProductName(productName);
        delivery.setDeliveryPassword(password);
        delivery.setStatus(DeliveryStatus.IN_PROGRESS);
        return deliveryRepository.save(delivery);
    }

    public Delivery completeDelivery(Long deliveryId, String password) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        if (!delivery.getDeliveryPassword().equals(password)) {
            throw new RuntimeException("Invalid delivery password!");
        }

        delivery.setStatus(DeliveryStatus.COMPLETED);
        return deliveryRepository.save(delivery);
    }

    public Delivery getNextDelivery() {
        return deliveryRepository.findFirstByStatusOrderByIdAsc(DeliveryStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("No pending deliveries available"));
    }

    @Transactional
    public ResponseEntity<?> beginDelivery(DeliveryRequest request) {
        // Check if listing exists
        /*Listing listing = listingRepository.findById(request.getListingId())
                .orElseThrow(() -> new RuntimeException("Listing not found with ID: " + request.getListingId()));
		*/
    	 if (request.getListingId() == null) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: listingId cannot be null.");
         }

         Listing listing = listingRepository.findById(request.getListingId())
                 .orElseThrow(() -> new RuntimeException("Listing not found with ID: " + request.getListingId()));

         // Create new delivery entry
        // Create a new delivery
        Delivery delivery = new Delivery();
        delivery.setListing(listing);
        delivery.setDeliveryPassword(request.getDeliveryPassword());
        delivery.setDeliveryPersonName(request.getDeliveryPerson());
        delivery.setProductName(request.getProductName());
        delivery.setStatus(DeliveryStatus.IN_PROGRESS); // Mark delivery as started

        deliveryRepository.save(delivery);

        return ResponseEntity.ok("Delivery started successfully for Listing ID: " + request.getListingId());
    }
}
