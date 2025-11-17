package com.flightapp.testutil;

import com.flightapp.DTO.*;
import com.flightapp.model.FlightInventory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestData {
    public static FlightInventory flightBasic() {
        FlightInventory f = new FlightInventory();
        f.setId(1);
        f.setAirlineName("Indigo");
        f.setFlightNumber("AE101");
        f.setFromPlace("DELHI"); f.setToPlace("MUMBAI");
        f.setDepartureTime(LocalDateTime.of(2025,11,25,9,0));
        f.setArrivalTime(LocalDateTime.of(2025,11,25,11,0));
        f.setTotalSeats(180); f.setAvailableSeats(10);
        f.setPrice(4500); f.setSpecialFare(3500);
        f.setFareCategory("STUDENT");
        return f;
    }

    public static PassengerRequest passenger(String category, String name, int age, int seat) {
        PassengerRequest p = new PassengerRequest();
        p.setFareCategory(category);
        p.setName(name);
        p.setAge(age);
        p.setGender("MALE");
        p.setMeal("VEG");
        p.setSeatNumber(seat);
        return p;
    }

    public static BookingRequest bookingReq(String email, int seats, List<PassengerRequest> passengers) {
        BookingRequest r = new BookingRequest();
        r.setEmail(email);
        r.setNumberOfSeats(seats);
        r.setPassengers(passengers);
        return r;
    }
}
