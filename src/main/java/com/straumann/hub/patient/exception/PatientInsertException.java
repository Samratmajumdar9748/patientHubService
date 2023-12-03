package com.straumann.hub.patient.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PatientInsertException extends RuntimeException{

    private int status;
    private String messaga;

}
