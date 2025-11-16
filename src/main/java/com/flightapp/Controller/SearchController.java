package com.flightapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flightapp.DTO.FlightSearchRequest;
import com.flightapp.DTO.FlightSearchResponse;
import com.flightapp.Service.FlightInventoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/flight")
public class SearchController {
	
    @Autowired
    FlightInventoryService service;

    @PostMapping("/search")
    public ResponseEntity<List<FlightSearchResponse>> search(
            @Valid @RequestBody FlightSearchRequest request) {
    	
        return ResponseEntity.ok(service.searchFlights(request));
    }
}

