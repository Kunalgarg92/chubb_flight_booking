package com.flightapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String gender;

    private int age;

    private String meal; 

    private int seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingTicket booking;
    
    private String fareCategory;

    public String getFareCategory() {
		return fareCategory;
	}

	public void setFareCategory(String fareCategory) {
		this.fareCategory = fareCategory;
	}

	public double getFareApplied() {
		return fareApplied;
	}

	public void setFareApplied(double fareApplied) {
		this.fareApplied = fareApplied;
	}

	public String getFareMessage() {
		return fareMessage;
	}

	public void setFareMessage(String fareMessage) {
		this.fareMessage = fareMessage;
	}

	private double fareApplied;

    private String fareMessage;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public BookingTicket getBooking() {
		return booking;
	}

	public void setBooking(BookingTicket booking) {
		this.booking = booking;
	}
    
}
