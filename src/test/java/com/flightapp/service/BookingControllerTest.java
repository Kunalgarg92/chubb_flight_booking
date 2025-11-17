package com.flightapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.flightapp.Controller.BookingController;
import com.flightapp.DTO.BookingResponse;
import com.flightapp.Service.BookingService;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @Test
    void postBooking_validation_error() throws Exception {
        String invalidBody = """
            {
              "email":"a@b.com",
              "numberOfSeats":2,
              "passengers":[]
            }
            """;

        mockMvc.perform(post("/api/v1.0/flight/booking/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postBooking_success_returns200() throws Exception {

        BookingResponse resp = new BookingResponse();
        resp.setPnr("ABC123");

        org.mockito.Mockito.when(
                bookingService.bookFlight(eq(1), any())
        ).thenReturn(resp);

        String validBody = """
            {
              "email":"a@b.com",
              "numberOfSeats":1,
              "passengers":[
                {
                  "name":"A",
                  "gender":"MALE",
                  "age":20,
                  "meal":"VEG",
                  "seatNumber":10,
                  "fareCategory":"STUDENT"
                }
              ]
            }
            """;

        mockMvc.perform(post("/api/v1.0/flight/booking/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pnr").value("ABC123"));
    }

    @Test
    void testGetHistory() throws Exception {

        org.mockito.Mockito.when(
                bookingService.getBookingHistory("abc@gmail.com")
        ).thenReturn(List.of(new BookingResponse()));

        mockMvc.perform(get("/api/v1.0/flight/booking/history/abc@gmail.com"))
                .andExpect(status().isOk());
    }


    @Test
    void cancelBooking_success() throws Exception {
    	
    	Mockito.doNothing()
        .when(bookingService)
        .cancelBooking("PNR123");


        mockMvc.perform(delete("/api/v1.0/flight/booking/cancel/PNR123"))
                .andExpect(status().isOk());
    }
}
