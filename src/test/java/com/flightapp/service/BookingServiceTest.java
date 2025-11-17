package com.flightapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flightapp.DTO.BookingRequest;
import com.flightapp.DTO.BookingResponse;
import com.flightapp.DTO.FlightSearchRequest;
import com.flightapp.DTO.FlightSearchResponse;
import com.flightapp.DTO.PassengerRequest;
import com.flightapp.Service.implementation.BookingServiceImplementation;
import com.flightapp.Service.implementation.FlightInventoryServiceImplementation;
import com.flightapp.model.FlightInventory;
import com.flightapp.model.Passenger;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.repository.PassengerRepository;
import com.flightapp.testutil.TestData;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private FlightInventoryRepository flightRepo;

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private PassengerRepository passengerRepo;

    @InjectMocks
    private BookingServiceImplementation service;

    @Test
    void testBookingFailsWhenSeatNotAvailable() {

        BookingRequest request = new BookingRequest();
        request.setEmail("test@gmail.com");
        request.setNumberOfSeats(2);

        request.setPassengers(List.of(
            new PassengerRequest("STUDENT" , "Aman", "MALE",25,  "VEG", 10 ),
            new PassengerRequest("STUDENT" , "Amen", "MALE",26,  "VEG", 11)
        ));

        FlightInventory flight = new FlightInventory();
        flight.setAvailableSeats(1); 

        when(flightRepo.findById(1)).thenReturn(Optional.of(flight));

        assertThrows(IllegalArgumentException.class,
                () -> service.bookFlight(1, request),
                "Not enough seats available");
    }
    
    @Test
    void testSpecialFareApplied() {

        FlightInventory flight = new FlightInventory();
        flight.setFareCategory("STUDENT");
        flight.setSpecialFare(3500);
        flight.setPrice(4500);
        flight.setAvailableSeats(10);
        flight.setTotalSeats(180);

        when(flightRepo.findById(1)).thenReturn(Optional.of(flight));

        BookingRequest req = new BookingRequest();
        req.setEmail("test@gmail.com");
        req.setNumberOfSeats(1);

        PassengerRequest p = new PassengerRequest();
        p.setFareCategory("STUDENT");
        p.setName("Kunal");
        p.setGender("MALE");
        p.setAge(21);
        p.setMeal("VEG");
        p.setSeatNumber(10);

        req.setPassengers(List.of(p));

        BookingResponse resp = service.bookFlight(1, req);

        assertEquals(3500, resp.getPassengers().get(0).fareApplied);
    }
    
    @Test
    void fail_when_passenger_list_null_or_mismatch() {
        BookingRequest req = new BookingRequest();
        req.setEmail("a@b.com"); req.setNumberOfSeats(2);
        req.setPassengers(List.of()); 
        FlightInventory f = TestData.flightBasic(); f.setAvailableSeats(10);
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        assertThrows(IllegalArgumentException.class, () -> service.bookFlight(1, req));
    }

    @Test
    void fail_when_not_enough_seats() {
        BookingRequest req = TestData.bookingReq("a@b.com", 2, List.of(
            TestData.passenger("STUDENT","A",20,10), TestData.passenger("STUDENT","B",21,11)
        ));
        FlightInventory f = TestData.flightBasic(); f.setAvailableSeats(1);
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        assertThrows(IllegalArgumentException.class, () -> service.bookFlight(1, req));
    }

    @Test
    void fail_when_duplicate_seats_in_request() {
        BookingRequest req = TestData.bookingReq("a@b.com", 2, List.of(
            TestData.passenger("STUDENT","A",20,10), TestData.passenger("STUDENT","B",21,10)
        ));
        FlightInventory f = TestData.flightBasic();
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        assertThrows(IllegalArgumentException.class, () -> service.bookFlight(1, req));
    }

    @Test
    void fail_when_seat_out_of_range() {
        BookingRequest req = TestData.bookingReq("a@b.com", 1, List.of(
            TestData.passenger("STUDENT","A",20,999)
        ));
        FlightInventory f = TestData.flightBasic();
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        assertThrows(IllegalArgumentException.class, () -> service.bookFlight(1, req));
    }

    @Test
    void seat_already_booked_detected() {
        BookingRequest req = TestData.bookingReq("a@b.com", 1, List.of(TestData.passenger("STUDENT","A",20,10)));
        FlightInventory f = TestData.flightBasic();
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        when(passengerRepo.findBookedSeatsForFlight(eq(1), anyList())).thenReturn(List.of(new Passenger()));
        assertThrows(IllegalArgumentException.class, () -> service.bookFlight(1, req));
    }

    @Test
    void success_booking_with_mixed_fares_applied() {
        BookingRequest req = TestData.bookingReq("a@b.com", 2, List.of(
            TestData.passenger("STUDENT","A",20,10),
            TestData.passenger("ARMY","B",25,11)
        ));
        FlightInventory f = TestData.flightBasic(); f.setAvailableSeats(10);
        when(flightRepo.findById(1)).thenReturn(Optional.of(f));
        lenient().when(bookingRepo.findByPnr(anyString()))
        .thenReturn(Optional.empty());

        when(passengerRepo.findBookedSeatsForFlight(eq(1), anyList())).thenReturn(List.of());
        when(bookingRepo.saveAndFlush(any())).thenAnswer(i -> i.getArgument(0));
        BookingResponse resp = service.bookFlight(1, req);
        assertEquals(2, resp.getNumberOfSeats());
        assertEquals(2, resp.getPassengers().size());
        double sum = resp.getPassengers().stream().mapToDouble(p -> p.fareApplied).sum();
        assertEquals(sum, resp.getTotalPrice());
    }

}

