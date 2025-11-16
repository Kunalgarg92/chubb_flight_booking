package com.flightapp.Service;

import com.flightapp.DTO.FlightInventoryRequest;
import com.flightapp.model.FlightInventory;
import java.util.List;
import com.flightapp.DTO.FlightSearchResponse;
import com.flightapp.DTO.FlightSearchRequest;

public interface FlightInventoryService {
    FlightInventory addInventory(FlightInventoryRequest request);
    List<FlightSearchResponse> searchFlights(FlightSearchRequest request);
    
}
