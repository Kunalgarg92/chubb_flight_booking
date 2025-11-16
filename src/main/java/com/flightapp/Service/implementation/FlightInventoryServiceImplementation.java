package com.flightapp.Service.implementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flightapp.DTO.FlightInventoryRequest;
import com.flightapp.DTO.FlightSearchRequest;
import com.flightapp.DTO.FlightSearchResponse;
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
    
        
    @Override
    public List<FlightSearchResponse> searchFlights(FlightSearchRequest request) {
    	
        LocalDateTime startTime;
        LocalDateTime endTime;

        if (request.getTravelTime() == null || request.getTravelTime().isBlank()) {
            startTime = request.getTravelDate().atStartOfDay();
            endTime = request.getTravelDate().atTime(23, 59, 59);
        } else {
            LocalTime userTime = LocalTime.parse(request.getTravelTime());
            startTime = LocalDateTime.of(request.getTravelDate(), userTime.minusMinutes(30));
            endTime = LocalDateTime.of(request.getTravelDate(), userTime.plusMinutes(30));
        }
        List<FlightInventory> onwardFlights =repository.searchFlights(
                        request.getFromPlace().trim(),
                        request.getToPlace().trim(),
                        startTime,
                        endTime
                );

        List<FlightSearchResponse> responses = new ArrayList<>();

        // If no onward flights, return empty
        if (onwardFlights.isEmpty()) {
            return responses;
        }

        for (FlightInventory f : onwardFlights) {

            FlightSearchResponse res = new FlightSearchResponse();
            res.setAirlineName(f.getAirlineName());
            res.setFlightNumber(f.getFlightNumber());
            res.setDepartureTime(f.getDepartureTime());
            res.setArrivalTime(f.getArrivalTime());
            res.setOneWayPrice(f.getPrice());
            res.setRoundTripPrice(f.getPrice() * 2);
            if ("ROUND_TRIP".equalsIgnoreCase(request.getTripType())) {

                LocalDate returnDate = request.getReturnDate();
                LocalDateTime returnStart = returnDate.atStartOfDay();
                LocalDateTime returnEnd = returnDate.atTime(23, 59, 59);

                List<FlightInventory> returnFlights =
                        repository.searchFlights(
                                request.getToPlace().trim(),
                                request.getFromPlace().trim(),
                                returnStart,
                                returnEnd
                        );
                if (!returnFlights.isEmpty()) {
                    FlightInventory r = returnFlights.get(0);
                    res.setReturnFlightNumber(r.getFlightNumber());
                    res.setReturnDepartureTime(r.getDepartureTime());
                    res.setReturnArrivalTime(r.getArrivalTime());
                    res.setMessage("Round trip available.");
                } else {
                    res.setReturnFlightNumber(null);
                    res.setReturnDepartureTime(null);
                    res.setReturnArrivalTime(null);
                    res.setMessage("Only onward flight available. Return flight not found.");
                }

                responses.add(res);
                continue; 
            }
            res.setMessage("One-way trip available.");
            responses.add(res);
        }

        return responses;
    }

}
