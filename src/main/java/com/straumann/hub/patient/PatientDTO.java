package com.straumann.hub.patient;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
public class PatientDTO {

    @Email
    @NotBlank
    private String email;

 //   @Size(max = 10,message = "Mobile number size should be max 10 digits")
    private Long phoneNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp="(^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$)", message = "Please enter a valid date")
    private String dateOfBirth;

    @Valid
    private AddressDTO address;

    @NotBlank
    private String name;


    private String medicalHistory;

}
