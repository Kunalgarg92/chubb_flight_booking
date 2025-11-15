package com.flightapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.DTO.FlightInventoryRequest;
import com.flightapp.Service.FlightInventoryService;
import com.flightapp.model.FlightInventory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/flight/airline")
public class AdminController {

    @Autowired
    FlightInventoryService inventoryService;

    @PostMapping("/inventory/add")
    public ResponseEntity<FlightInventory> addInventory(
            @Valid @RequestBody FlightInventoryRequest request) {

        FlightInventory saved = inventoryService.addInventory(request);
        return ResponseEntity.ok(saved);
    }
}
