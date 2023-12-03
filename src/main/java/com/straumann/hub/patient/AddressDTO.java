package com.straumann.hub.patient;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDTO {

    private String state;

    private String city;

    private Integer zip;

    private String street;

}
