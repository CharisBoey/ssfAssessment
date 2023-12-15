package vttp.ssf.assessment.eventmanagement.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Register {

    @NotEmpty(message="Must fill in Full Name")
    @Size(min=5, max=25, message="Name must be between 5 and 25 characters")
    private String fullName;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past(message="Birth date cannot be greater than or equals to present date")
    private LocalDate birthDate;

    @NotEmpty(message="Must fill in email")
    @Email(message="Email must comply to email format")
    @Size(max=50, message="Email cannot be longer than 50 characters")
    private String email;

    @Pattern(regexp="([89])[0-9]{7}", message = "Phone Number must start with 8 or 9 followed by 7")
    private String mobileNumber;

    private String gender;

    @Min(value=1, message="Must choose a minimum of 1 ticket")
    @Max(value=3, message="Must choose a maximum of 3 ticket")
    @NotNull(message="Must choose a minimum of 1 ticket")
    private Integer noOfTicketsRequested;


    public Register(){

    }

    public Register(String fullName, LocalDate birthDate, String email, String mobileNumber, String gender, Integer noOfTicketsRequested) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.noOfTicketsRequested = noOfTicketsRequested;
    }


    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Integer getNoOfTicketsRequested() {
        return noOfTicketsRequested;
    }
    public void setNoOfTicketsRequested(Integer noOfTicketsRequested) {
        this.noOfTicketsRequested = noOfTicketsRequested;
    }

}
