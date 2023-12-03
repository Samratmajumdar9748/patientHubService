package com.straumann.hub.patient.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InvalidArgumentsExceptionModel {

    private String errorCode="ERR_INVALID_INPUT";
    private String errorMsg="Invalid input. Parameters:";
    private List<String> errorFields;
}
