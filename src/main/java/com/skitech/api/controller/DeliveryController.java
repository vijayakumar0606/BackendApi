package com.skitech.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skitech.api.model.Delivery;
import com.skitech.api.model.DeliveryRequest;
import com.skitech.api.repository.DeliveryStatus;
import com.skitech.api.service.DeliveryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/deliveries")
//@RequiredArgsConstructor
public class DeliveryController {
    
    //private final DeliveryService deliveryService = null;
	
	private final DeliveryService deliveryService;

    @Autowired // Ensure Spring injects the service
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    
    @PostMapping("/debug")
    public ResponseEntity<?> debugRequest(@RequestBody Map<String, Object> requestBody) {
        System.out.println("Received JSON: " + requestBody);
        return ResponseEntity.ok(requestBody);
    }
	
    /*@PostMapping("/begin")
    public ResponseEntity<?> beginDelivery(@Valid @RequestBody DeliveryRequest request) {
    	
    	if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Request body is missing.");
        }
        
    	
    	System.out.println("ID: "+request.getListingId());
    	System.out.println("PWD: "+request.getDeliveryPassword());
    	System.out.println("PERSON: "+request.getDeliveryPerson());
    	System.out.println("PRODUCT: "+request.getProductName());
    	
        return deliveryService.beginDelivery(request);
    }*/
    
    @PostMapping("/begin")
    public ResponseEntity<Delivery> beginDelivery(@RequestParam Long listingId, 
                                                  @RequestParam String deliveryPersonName, 
                                                  @RequestParam String productName, 
                                                  @RequestParam String password) {
    	
    	System.out.println("ID : "+listingId);
    	System.out.println("PWD : "+deliveryPersonName);
    	System.out.println("PERSON : "+productName);
    	System.out.println("PRODUCT : "+password);	
        return ResponseEntity.ok(deliveryService.beginDelivery(listingId, deliveryPersonName, productName, password));
    }
    @PostMapping("/complete/{id}")
    public ResponseEntity<Delivery> completeDelivery(@PathVariable Long id, @RequestParam String password) {
        return ResponseEntity.ok(deliveryService.completeDelivery(id, password));
    }

    @GetMapping("/next")
    public ResponseEntity<Delivery> getNextDelivery() {
        return ResponseEntity.ok(deliveryService.getNextDelivery());
    }
}
