package com.flightapp.repository;
import com.flightapp.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

	@Query("""
		    SELECT p FROM Passenger p 
		    WHERE p.booking.flight.id = :flightId
		    AND p.seatNumber IN :seats
		    AND p.booking.status = com.flightapp.model.BookingStatus.BOOKED
		    """)
	
		List<Passenger> findBookedSeatsForFlight(@Param("flightId") Integer flightId,@Param("seats") List<Integer> seats);

}
