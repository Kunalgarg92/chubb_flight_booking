package com.flightapp.Service;

import java.util.List;

import com.flightapp.DTO.BookingRequest;
import com.flightapp.DTO.BookingResponse;

public interface BookingService {
    BookingResponse bookFlight(Integer flightId, BookingRequest request);
    BookingResponse getBookingByPnr(String pnr);
    List<BookingResponse> getBookingHistory(String email);
    void cancelBooking(String pnr);
    
}
