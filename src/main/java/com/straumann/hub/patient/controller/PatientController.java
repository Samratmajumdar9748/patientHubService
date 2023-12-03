package com.straumann.hub.patient.controller;

import com.straumann.hub.patient.PatientDTO;
import com.straumann.hub.patient.exception.PatientIdInvalidException;
import com.straumann.hub.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class PatientController {

    PatientService patientService;
    @PostMapping(path="/insertPatient",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertpatient(@RequestBody @Valid PatientDTO patientDTO){
        Long response= patientService.addPatient(patientDTO);

        Map responseMap= new HashMap<>();
        responseMap.put("Status","Success");
        responseMap.put("Patient Id",response.toString());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping(path = "/getPatient")
    public ResponseEntity<?> getPatient(@RequestParam("patientId") String patientId){
        try {
            Long id= Long.parseLong(patientId);
            PatientDTO patientDTO= patientService.getPatientDetails(id);
            return new ResponseEntity<>(patientDTO,HttpStatus.OK);
        }catch (NumberFormatException exception){
            throw new PatientIdInvalidException();
        }catch (Exception ex){
            throw ex;
        }

    }

    @GetMapping(path="/allPatients")
    public ResponseEntity<?> getAllPatients(){
        return new ResponseEntity(patientService.getAllPatients(),HttpStatus.OK);
    }

}
