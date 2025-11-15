package com.flightapp.DTO;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class FlightInventoryRequest {

    @NotBlank(message = "Airline name is required")
    @Size(min = 2, max = 50, message = "Airline name must be between 2 and 50 characters")
    private String airlineName;

    @NotBlank(message = "Flight number is required")
    @Pattern(
        regexp = "^[A-Z]{1,2}[0-9]{1,4}$",
        message = "Flight number must follow pattern (e.g., 6E101, AI202)"
    )
    private String flightNumber;

    @NotBlank(message = "Origin airport / city is required")
    @Size(min = 2, max = 50, message = "From place must be between 2 and 50 characters")
    private String fromPlace;

    @NotBlank(message = "Destination airport / city is required")
    @Size(min = 2, max = 50, message = "To place must be between 2 and 50 characters")
    private String toPlace;

    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be a future date/time")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Future(message = "Arrival time must be a future date/time")
    private LocalDateTime arrivalTime;

    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;

    @Positive(message = "Base price must be greater than 0")
    private double price;

    @Positive(message = "Special fare must be greater than 0")
    private double specialFare;

    @NotBlank(message = "Fare category is required")
    @Pattern(
        regexp = "STUDENT|SENIOR|REGULAR|ARMY|CORPORATE",
        message = "Fare category must be one of: STUDENT, SENIOR, REGULAR, ARMY, CORPORATE"
    )
    private String fareCategory;

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

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

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSpecialFare() {
		return specialFare;
	}

	public void setSpecialFare(double specialFare) {
		this.specialFare = specialFare;
	}

	public String getFareCategory() {
		return fareCategory;
	}

	public void setFareCategory(String fareCategory) {
		this.fareCategory = fareCategory;
	}
    
    
}
