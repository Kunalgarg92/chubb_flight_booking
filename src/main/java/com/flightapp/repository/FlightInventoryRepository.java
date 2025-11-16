package com.flightapp.repository;

import com.flightapp.model.FlightInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightInventoryRepository extends JpaRepository<FlightInventory, Integer> {

    @Query("SELECT f FROM FlightInventory f " +
           "WHERE LOWER(TRIM(f.fromPlace)) = LOWER(TRIM(:fromPlace)) " +
           "AND LOWER(TRIM(f.toPlace)) = LOWER(TRIM(:toPlace)) " +
           "AND f.departureTime BETWEEN :start AND :end")
    List<FlightInventory> searchFlights(
            @Param("fromPlace") String fromPlace,
            @Param("toPlace") String toPlace,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
