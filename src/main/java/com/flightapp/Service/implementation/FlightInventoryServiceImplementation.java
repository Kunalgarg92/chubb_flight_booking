package com.flightapp.Service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.DTO.FlightInventoryRequest;
import com.flightapp.Service.FlightInventoryService;
import com.flightapp.model.FlightInventory;
import com.flightapp.repository.FlightInventoryRepository;

@Service
public class FlightInventoryServiceImplementation implements FlightInventoryService {

    @Autowired
    private FlightInventoryRepository repository;

    @Override
    public FlightInventory addInventory(FlightInventoryRequest request) {
        FlightInventory f1 = new FlightInventory();
        
        f1.setAirlineName(request.getAirlineName());
        f1.setFlightNumber(request.getFlightNumber());
        f1.setFromPlace(request.getFromPlace());
        f1.setToPlace(request.getToPlace());
        f1.setDepartureTime(request.getDepartureTime());
        f1.setArrivalTime(request.getArrivalTime());
        f1.setTotalSeats(request.getTotalSeats());
        f1.setAvailableSeats(request.getTotalSeats());
        f1.setPrice(request.getPrice());
        f1.setSpecialFare(request.getSpecialFare());
        f1.setFareCategory(request.getFareCategory());

        return repository.save(f1);
    }
}
