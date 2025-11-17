package com.flightapp.Controller;

import com.flightapp.DTO.BookingRequest;
import com.flightapp.DTO.BookingResponse;
import com.flightapp.Service.BookingService;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/flight")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking/{flightId}")
    public ResponseEntity<BookingResponse> book(
            @PathVariable Integer flightId,
            @Valid @RequestBody BookingRequest request) {

        BookingResponse resp = bookingService.bookFlight(flightId, request);
        return ResponseEntity.ok(resp);
    }

        @GetMapping("/booking/history/{email}")
        public ResponseEntity<List<BookingResponse>> history(@PathVariable String email) {
            return ResponseEntity.ok(bookingService.getBookingHistory(email));
        }
        
        @DeleteMapping("/booking/cancel/{pnr}")
        public ResponseEntity<String> cancel(@PathVariable String pnr) {

            bookingService.cancelBooking(pnr);
            return ResponseEntity.ok("Ticket with PNR " + pnr + " has been cancelled successfully.");
        }

    }

