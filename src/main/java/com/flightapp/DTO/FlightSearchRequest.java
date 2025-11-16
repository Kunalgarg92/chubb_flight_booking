package com.flightapp.DTO;

import java.time.LocalDate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class FlightSearchRequest {
	
	@NotBlank(message = "From place is required")
    private String fromPlace;

    @NotBlank(message = "To place is required")
    private String toPlace;

    @NotNull(message = "Travel date is required")
    @Future(message = "Travel date must be a future date")
    private LocalDate travelDate;

    
    @Pattern(
        regexp = "^$|^([01]\\d|2[0-3]):([0-5]\\d)$",
        message = "Time must be in HH:mm format"
    )
    private String travelTime;
    
    @Pattern(
        regexp = "ONE_WAY|ROUND_TRIP",
        message = "Trip type must be ONE_WAY or ROUND_TRIP"
    )
    private String tripType; 
    
    @Future(message = "Return date must be a future date")
    private LocalDate returnDate; 
    
    

    public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public LocalDate getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
	}

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

    
}

