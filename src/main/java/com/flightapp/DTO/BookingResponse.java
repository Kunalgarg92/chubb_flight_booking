package com.flightapp.DTO;
import java.time.Instant;
import java.util.List;

public class BookingResponse {
    public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Instant getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Instant bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<PassengerInfo> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerInfo> passengers) {
		this.passengers = passengers;
	}

	private String pnr;
    private String email;
    private int numberOfSeats;
    private double totalPrice;
    private Instant bookingTime;
    private String message;
    private List<PassengerInfo> passengers;

    public static class PassengerInfo {
        public String name;
        public int seatNumber;
        public String gender;
        public int age;
        public String meal;
    }
}
