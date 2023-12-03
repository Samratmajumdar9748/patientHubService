package com.straumann.hub.patient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalCustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidArgumentsExceptionModel> handleInvalidRequest(MethodArgumentNotValidException ex ){
        return new ResponseEntity( InvalidArgumentsExceptionModel.builder()
                .errorCode("ERR_INVALID_INPUT")
                .errorMsg("Invalid input parameter name: ")
                .errorFields( ex.getFieldErrors().stream().map(FieldError::getField ).collect(Collectors.toList()) )
                .build() , HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public final ResponseEntity<?> handleRecordNotFoundException(PatientNotFoundException e){
        List<String> errorDetails = new ArrayList<>();
        Map<Object,Object> errResponse = new HashMap<>();
        errorDetails.add(e.getLocalizedMessage());
        errResponse.put("Error","Patient Not Found");
        errResponse.put("message",errorDetails);

        return new ResponseEntity(errResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientInsertException.class)
    public final ResponseEntity<?> handlePatientInsersionException(PatientInsertException e){
        List<String> errorDetails = new ArrayList<>();
        Map<Object,Object> errResponse = new HashMap<>();
        errorDetails.add(e.getLocalizedMessage());
        errResponse.put("Error","Patient Insertion Failed");
        errResponse.put("message",errorDetails);

        return new ResponseEntity(errResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PatientIdInvalidException.class)
    public final ResponseEntity<?> handleIdInvalidException(PatientIdInvalidException e){
        List<String> errorDetails = new ArrayList<>();
        Map<Object,Object> errResponse = new HashMap<>();
        errorDetails.add(e.getLocalizedMessage());
        errResponse.put("Error","Patient Id given is invalid");
        errResponse.put("message",errorDetails);

        return new ResponseEntity(errResponse, HttpStatus.BAD_REQUEST);
    }


}
