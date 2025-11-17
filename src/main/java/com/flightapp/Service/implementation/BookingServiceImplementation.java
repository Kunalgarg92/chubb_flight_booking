package com.flightapp.Service.implementation;

import com.flightapp.DTO.BookingRequest;
import com.flightapp.DTO.BookingResponse;
import com.flightapp.DTO.PassengerRequest;
import com.flightapp.model.BookingTicket;
import com.flightapp.model.FlightInventory;
import com.flightapp.model.Passenger;
import com.flightapp.model.BookingStatus;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.repository.PassengerRepository;
import com.flightapp.Service.BookingService;
import com.flightapp.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImplementation implements BookingService {

    @Autowired
    private FlightInventoryRepository flightRepo;

    @Autowired
    private BookingRepository bookingRepo;
    
    @Autowired
    private FlightInventoryRepository inventoryRepo;

    @Autowired
    private PassengerRepository passengerRepo;

    private static final Random RNG = new Random();

    @Override
    @Transactional
    public BookingResponse bookFlight(Integer flightId, BookingRequest request) {
        FlightInventory flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));
        
        int seatsRequested = request.getNumberOfSeats();
        if (seatsRequested != request.getPassengers().size()) {
            throw new IllegalArgumentException("numberOfSeats must equal number of passengers");
        }

        if (flight.getAvailableSeats() < seatsRequested) {
            throw new IllegalArgumentException("Not enough seats available");
        }
        List<Integer> seatNumbers = request.getPassengers().stream()
                .map(PassengerRequest::getSeatNumber)
                .collect(Collectors.toList());

        Set<Integer> uniqueSeats = new HashSet<>(seatNumbers);
        if (uniqueSeats.size() != seatNumbers.size()) {
            throw new IllegalArgumentException("Duplicate seat numbers in request");
        }
        for (Integer s : seatNumbers) {
            if (s < 1 || s > flight.getTotalSeats()) {
                throw new IllegalArgumentException("Seat number " + s + " is out of range for this flight");
            }
        }
        List<Passenger> alreadyBooked = passengerRepo.findBookedSeatsForFlight(flightId, seatNumbers);
        if (!alreadyBooked.isEmpty()) {
            String taken = alreadyBooked.stream()
                    .map(p -> String.valueOf(p.getSeatNumber()))
                    .collect(Collectors.joining(","));
            throw new IllegalArgumentException("Seat(s) already booked: " + taken);
        }
        
        double totalPrice = 0.0;
        
        String inventoryCat = flight.getFareCategory() == null ? "" : flight.getFareCategory().trim().toUpperCase();

        BookingTicket booking = new BookingTicket();
        booking.setPnr(generatePnr());
        booking.setEmail(request.getEmail());
        booking.setFlight(flight);
        booking.setBookingTime(Instant.now());
        booking.setNumberOfSeats(seatsRequested);
        booking.setStatus(BookingStatus.BOOKED);
        
        for (PassengerRequest pr : request.getPassengers()) {

            Passenger p = new Passenger();
            p.setName(pr.getName());
            p.setAge(pr.getAge());
            p.setGender(pr.getGender());
            p.setMeal(pr.getMeal());
            p.setSeatNumber(pr.getSeatNumber());
            p.setFareCategory(pr.getFareCategory().toUpperCase());
            String passengerCat = pr.getFareCategory() == null ? "" : pr.getFareCategory().trim().toUpperCase();
            p.setFareCategory(passengerCat);

            if (!inventoryCat.isEmpty() && passengerCat.equals(inventoryCat)) {
                p.setFareApplied(flight.getSpecialFare());
                p.setFareMessage("Special fare applied for category: " + passengerCat);
                totalPrice += flight.getSpecialFare();
            } else {
                p.setFareApplied(flight.getPrice());
                p.setFareMessage("No special fare available. Extra benefits applied.");
                totalPrice += flight.getPrice();
            }

            booking.addPassenger(p);
        }
        booking.setTotalPrice(totalPrice);
        bookingRepo.saveAndFlush(booking); 
        flight.setAvailableSeats(flight.getAvailableSeats() - seatsRequested);
        flightRepo.save(flight);

        BookingResponse resp = new BookingResponse();
        resp.setPnr(booking.getPnr());
        resp.setEmail(booking.getEmail());
        resp.setNumberOfSeats(booking.getNumberOfSeats());
        resp.setTotalPrice(booking.getTotalPrice());
        resp.setBookingTime(booking.getBookingTime());
        resp.setMessage("Booking successful");

        List<BookingResponse.PassengerInfo> plist = booking.getPassengers().stream().map(px -> {
            BookingResponse.PassengerInfo info = new BookingResponse.PassengerInfo();
            info.name = px.getName();
            info.age = px.getAge();
            info.gender = px.getGender();
            info.meal = px.getMeal();
            info.seatNumber = px.getSeatNumber();
            info.fareCategory = px.getFareCategory();
            info.fareApplied = px.getFareApplied();
            info.fareMessage = px.getFareMessage();

            return info;
        }).collect(Collectors.toList());

        resp.setPassengers(plist);
        return resp;
    }
    private String generatePnr() {
        String p;
        do {
            p = randomAlphaNumeric(6);
        } while (bookingRepo.findByPnr(p).isPresent());
        return p;
    }

    private String randomAlphaNumeric(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(RNG.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingByPnr(String pnr) {
        BookingTicket booking = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with PNR '" + pnr + "' not found"));
        BookingResponse resp = new BookingResponse();
        resp.setPnr(booking.getPnr());
        resp.setEmail(booking.getEmail());
        resp.setNumberOfSeats(booking.getNumberOfSeats());
        resp.setTotalPrice(booking.getTotalPrice());
        resp.setBookingTime(booking.getBookingTime());
        resp.setMessage("Booking retrieved");

        List<BookingResponse.PassengerInfo> plist = booking.getPassengers().stream().map(px -> {
            BookingResponse.PassengerInfo info = new BookingResponse.PassengerInfo();
            info.name = px.getName();
            info.age = px.getAge();
            info.gender = px.getGender();
            info.meal = px.getMeal();
            info.seatNumber = px.getSeatNumber();
            info.fareCategory = px.getFareCategory();
            info.fareApplied = px.getFareApplied();
            info.fareMessage = px.getFareMessage();
            return info;
        }).collect(Collectors.toList());

        resp.setPassengers(plist);
        return resp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingHistory(String email) {

        List<BookingTicket> tickets = bookingRepo.findByEmailOrderByBookingTimeDesc(email);

        List<BookingResponse> list = new ArrayList<>();

        for (BookingTicket booking : tickets) {

            BookingResponse resp = new BookingResponse();
            resp.setPnr(booking.getPnr());
            resp.setEmail(booking.getEmail());
            resp.setNumberOfSeats(booking.getNumberOfSeats());
            resp.setTotalPrice(booking.getTotalPrice());
            resp.setBookingTime(booking.getBookingTime());
            resp.setMessage("Booking history item");

            List<BookingResponse.PassengerInfo> passengers = booking.getPassengers().stream().map(px -> {
                BookingResponse.PassengerInfo info = new BookingResponse.PassengerInfo();
                info.name = px.getName();
                info.age = px.getAge();
                info.gender = px.getGender();
                info.meal = px.getMeal();
                info.seatNumber = px.getSeatNumber();
                info.fareCategory = px.getFareCategory();
                info.fareApplied = px.getFareApplied();
                info.fareMessage = px.getFareMessage();
                return info;
            }).toList();

            resp.setPassengers(passengers);

            list.add(resp);
        }

        return list;
    }
    
    @Override
    @Transactional
    public void cancelBooking(String pnr) {
        BookingTicket booking = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("PNR '" + pnr + "' not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure = booking.getFlight().getDepartureTime();

        if (departure.minusHours(24).isBefore(now)) {
            throw new IllegalArgumentException("Cancellation not allowed within 24 hours of departure");
        }
        
        FlightInventory flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumberOfSeats());
        inventoryRepo.save(flight);
        
        bookingRepo.delete(booking);
    }


}

