package com.flightapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightInventoryRepository extends JpaRepository<FlightInventory, Integer> {
	
	List<FlightInventory> findByFromPlaceAndToPlaceAndDepartureTimeBetween(
		    String from,
		    String to,
		    LocalDateTime start,
		    LocalDateTime end
		);


}
