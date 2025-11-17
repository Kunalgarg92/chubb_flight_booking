package com.flightapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import com.flightapp.DTO.BookingRequest;
import com.flightapp.DTO.BookingResponse;
import com.flightapp.DTO.FlightSearchRequest;
import com.flightapp.DTO.FlightSearchResponse;
import com.flightapp.DTO.PassengerRequest;
import com.flightapp.Service.implementation.FlightInventoryServiceImplementation;
import com.flightapp.model.FlightInventory;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.testutil.TestData;

@ExtendWith(MockitoExtension.class)
class FlightInventoryServiceTest {

    @Mock
    private FlightInventoryRepository repo;

    @InjectMocks
    private FlightInventoryServiceImplementation service;

    @Test
    void testSearchOneWayFlightSuccess() {

        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromPlace("DELHI");
        req.setToPlace("MUMBAI");
        req.setTravelDate(LocalDate.of(2025, 11, 25));
        req.setTravelTime("");
        req.setTripType("ONE_WAY");

        FlightInventory f = new FlightInventory();
        f.setAirlineName("Indigo");
        f.setFlightNumber("AE101");
        f.setPrice(4500);

        when(repo.searchFlights(
                eq("DELHI"),
                eq("MUMBAI"),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(List.of(f));

        List<FlightSearchResponse> result = service.searchFlights(req);

        assertEquals(1, result.size());
        assertEquals("Indigo", result.get(0).getAirlineName());
    }
    
    @Test
    void testRoundTripWhenReturnFlightNotAvailable() {
        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromPlace("DELHI");
        req.setToPlace("MUMBAI");
        req.setTripType("ROUND_TRIP");
        req.setTravelTime("");
        req.setTravelDate(LocalDate.of(2025, 11, 25));
        req.setReturnDate(LocalDate.of(2025, 11, 28));

        FlightInventory f1 = new FlightInventory();
        f1.setAirlineName("Indigo");
        f1.setFlightNumber("AE101");
        f1.setPrice(4500);

        when(repo.searchFlights(
                eq("DELHI"),
                eq("MUMBAI"),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(List.of(f1));

        when(repo.searchFlights(
                eq("MUMBAI"),
                eq("DELHI"),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(List.of());

        List<FlightSearchResponse> result = service.searchFlights(req);

        assertEquals("Only onward flight available. Return flight not found.",
                     result.get(0).getMessage());
    }
    
    @Test
    void oneWay_noTime_searchWholeDay() {
        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromPlace("DELHI"); req.setToPlace("MUMBAI");
        req.setTravelDate(LocalDate.of(2025,11,25));
        req.setTravelTime(""); req.setTripType("ONE_WAY");

        FlightInventory f = TestData.flightBasic();
        when(repo.searchFlights(
                eq("DELHI"),
                eq("MUMBAI"),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(List.of(f));

        var got = service.searchFlights(req);
        assertEquals(1, got.size());
        assertEquals("AE101", got.get(0).getFlightNumber());
    }

    @Test
    void oneWay_withTime_searchWindow() {
        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromPlace("DELHI"); req.setToPlace("MUMBAI");
        req.setTravelDate(LocalDate.of(2025,11,25));
        req.setTravelTime("09:00"); req.setTripType("ONE_WAY");

        FlightInventory f = TestData.flightBasic();
        when(repo.searchFlights(
                eq("DELHI"),
                eq("MUMBAI"),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(List.of(f));

        var got = service.searchFlights(req);
        assertFalse(got.isEmpty());
    }

    @Test
    void roundTrip_returnNotFound_setsMessage() {
        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromPlace("DELHI"); req.setToPlace("MUMBAI");
        req.setTripType("ROUND_TRIP"); req.setTravelDate(LocalDate.of(2025,11,25));
        req.setReturnDate(LocalDate.of(2025,11,28)); req.setTravelTime("");

        FlightInventory f = TestData.flightBasic();
        when(repo.searchFlights(
            eq("DELHI"), eq("MUMBAI"), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(List.of(f));

        when(repo.searchFlights(
            eq("MUMBAI"), eq("DELHI"), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(List.of());

        var out = service.searchFlights(req);
        assertEquals("Only onward flight available. Return flight not found.", out.get(0).getMessage());
    }

    @Test
    void roundTrip_withReturn_populatesReturnDetails() {
        FlightInventory f1 = TestData.flightBasic();
        FlightInventory r1 = TestData.flightBasic();
        r1.setFromPlace("MUMBAI"); r1.setToPlace("DELHI"); r1.setFlightNumber("AE102");

        when(repo.searchFlights(
                eq("DELHI"), eq("MUMBAI"),
                any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(List.of(f1));

        when(repo.searchFlights(
                eq("MUMBAI"), eq("DELHI"),
                any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(List.of(r1));


        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromPlace("DELHI"); req.setToPlace("MUMBAI"); req.setTripType("ROUND_TRIP");
        req.setTravelDate(LocalDate.of(2025,11,25)); req.setReturnDate(LocalDate.of(2025,11,28));
        req.setTravelTime("");

        var out = service.searchFlights(req);
        assertNotNull(out.get(0).getReturnFlightNumber());
    }



}
