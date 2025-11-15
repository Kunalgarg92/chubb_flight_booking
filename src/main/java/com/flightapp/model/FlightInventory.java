package com.flightapp.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class FlightInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Airline name is required")
    @Size(min = 2, max = 50, message = "Airline name must be between 2 and 50 characters")
    private String airlineName;

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z0-9]{2,8}$", message = "Flight number must be uppercase alphanumeric (2-8 characters)")
    private String flightNumber;

    @NotBlank(message = "From place is required")
    @Size(min = 2, max = 50, message = "From place must be between 2 and 50 characters")
    private String fromPlace;

    @NotBlank(message = "To place is required")
    @Size(min = 2, max = 50, message = "To place must be between 2 and 50 characters")
    private String toPlace;

    @NotNull(message = "Departure time cannot be null")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time cannot be null")
    @Future(message = "Arrival time must be in the future")
    private LocalDateTime arrivalTime;

    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;

    @Min(value = 0, message = "Available seats cannot be negative")
    private int availableSeats;

    @Positive(message = "Price must be a positive value")
    private double price;

    @Positive(message = "Special fare must be a positive value")
    private double specialFare;

    @NotBlank(message = "Fare category is required")
    @Pattern(
        regexp = "STUDENT|SENIOR|REGULAR|ARMY|CORPORATE",
        message = "Fare category must be one of: STUDENT, SENIOR, REGULAR, ARMY, CORPORATE"
    )
    private String fareCategory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
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
