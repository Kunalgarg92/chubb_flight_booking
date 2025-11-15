package com.flightapp.Service;

import com.flightapp.DTO.FlightInventoryRequest;
import com.flightapp.model.FlightInventory;

public interface FlightInventoryService {
    FlightInventory addInventory(FlightInventoryRequest request);
}
