package com.flightapp.DTO;

import java.time.LocalDateTime;

public class FlightSearchResponse {
	
	private String airlineName;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double oneWayPrice;
    private double roundTripPrice;
    private String message; 
    private LocalDateTime returnDepartureTime;
    private LocalDateTime returnArrivalTime;
    private String returnFlightNumber;
    
    public String getMessage() {
		return message;
	}
    public void setMessage(String message) {
        this.message = message;
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
	public double getOneWayPrice() {
		return oneWayPrice;
	}
	public void setOneWayPrice(double oneWayPrice) {
		this.oneWayPrice = oneWayPrice;
	}
	public double getRoundTripPrice() {
		return roundTripPrice;
	}
	public void setRoundTripPrice(double roundTripPrice) {
		this.roundTripPrice = roundTripPrice;
	}
	public LocalDateTime getReturnDepartureTime() {
		return returnDepartureTime;
	}
	public void setReturnDepartureTime(LocalDateTime returnDepartureTime) {
		this.returnDepartureTime = returnDepartureTime;
	}
	public LocalDateTime getReturnArrivalTime() {
		return returnArrivalTime;
	}
	public void setReturnArrivalTime(LocalDateTime returnArrivalTime) {
		this.returnArrivalTime = returnArrivalTime;
	}
	public String getReturnFlightNumber() {
		return returnFlightNumber;
	}
	public void setReturnFlightNumber(String returnFlightNumber) {
		this.returnFlightNumber = returnFlightNumber;
	}
	

}

