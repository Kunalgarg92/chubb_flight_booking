package com.flightapp.Controller;

import com.flightapp.DTO.BookingResponse;
import com.flightapp.Service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/flight")
public class TicketController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/ticket/{pnr}")
    public ResponseEntity<BookingResponse> getTicketByPnr(@PathVariable("pnr") String pnr) {
        BookingResponse resp = bookingService.getBookingByPnr(pnr);
        return ResponseEntity.ok(resp);
    }
}
