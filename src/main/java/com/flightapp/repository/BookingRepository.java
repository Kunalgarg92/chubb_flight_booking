package com.flightapp.repository;

import com.flightapp.model.BookingTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingTicket, Long> {

    Optional<BookingTicket> findByPnr(String pnr);

    List<BookingTicket> findByEmailOrderByBookingTimeDesc(String email);
}
