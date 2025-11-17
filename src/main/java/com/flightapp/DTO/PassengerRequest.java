package com.flightapp.DTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRequest {

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}
	
	  @NotBlank(message = "Passenger category is required")
	    private String fareCategory;

//	public PassengerRequest(@NotBlank(message = "Passenger category is required") String fareCategory,
//			@NotBlank(message = "Passenger name is required") String name,
//			@NotBlank(message = "Gender is required") @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE or OTHER") String gender,
//			@Min(value = 0, message = "Age must be >= 0") int age,
//			@NotBlank(message = "Meal choice required") @Pattern(regexp = "VEG|NON_VEG", message = "Meal must be VEG or NON_VEG") String meal,
//			@NotNull(message = "Seat number is required") @Min(value = 1, message = "Seat number must be >= 1") Integer seatNumber) {
//		super();
//		this.fareCategory = fareCategory;
//		this.name = name;
//		this.gender = gender;
//		this.age = age;
//		this.meal = meal;
//		this.seatNumber = seatNumber;
//	}

	public String getFareCategory() {
		return fareCategory;
	}

	  public void setFareCategory(String fareCategory) {
		  this.fareCategory = fareCategory;
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

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	@NotBlank(message = "Passenger name is required")
    private String name;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE or OTHER")
    private String gender;

    @Min(value = 0, message = "Age must be >= 0")
    private int age;

    @NotBlank(message = "Meal choice required")
    @Pattern(regexp = "VEG|NON_VEG", message = "Meal must be VEG or NON_VEG")
    private String meal;

    @NotNull(message = "Seat number is required")
    @Min(value = 1, message = "Seat number must be >= 1")
    private Integer seatNumber;
}
