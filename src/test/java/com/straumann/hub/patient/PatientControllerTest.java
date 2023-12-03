package com.straumann.hub.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.straumann.hub.patient.controller.PatientController;
import com.straumann.hub.patient.service.PatientService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    public void testInsertPatient() throws Exception {
        // Given
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("patient@example.com");
        patientDTO.setPhoneNumber(1234567890L);
        patientDTO.setName("Patient test");
        patientDTO.setDateOfBirth("1999-12-31");
        patientDTO.setMedicalHistory("Meds history----- some long data-------");
        AddressDTO addressDTO= new AddressDTO();
        addressDTO.setCity("Hawai");
        addressDTO.setStreet("Some street");
        addressDTO.setState("USA");
        addressDTO.setZip(123455);
        patientDTO.setAddress( addressDTO );
        when(patientService.addPatient(any(PatientDTO.class))).thenReturn(1L);

        // When
        MvcResult result = mockMvc.perform(post("/insertPatient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientDTO)))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String content = result.getResponse().getContentAsString();
        // Perform assertions on the content using jsonPath
        assertThat(content, Matchers.containsString("\"Status\":\"Success\""));
        assertThat(content, Matchers.containsString("\"Patient Id\":\"1\""));

    }


    @Test
    public void testInsertPatient_Failure() throws Exception {
        // Given
        // Set patientDTO attributes for a failure scenario
        PatientDTO patientDTO = new PatientDTO();
        //invalid email
        patientDTO.setEmail("patientexample.com");
        patientDTO.setPhoneNumber(1234567890L);
        patientDTO.setName("Patient test");
        patientDTO.setDateOfBirth("1999-12-31");
        patientDTO.setMedicalHistory("Meds history----- some long data-------");
        AddressDTO addressDTO= new AddressDTO();
        addressDTO.setCity("Hawai");
        addressDTO.setStreet("Some street");
        addressDTO.setState("USA");
        addressDTO.setZip(123455);
        patientDTO.setAddress( addressDTO );
        when(patientService.addPatient(any(PatientDTO.class))).thenReturn(0L);

        // When
        MvcResult result = mockMvc.perform(post("/insertPatient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        // Then, assert that the response contains the expected error details
        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody, Matchers.containsString("\"errorCode\":\"ERR_INVALID_INPUT\""));
        assertThat(responseBody,Matchers.containsString("\"errorMsg\":\"Invalid input parameter name: \""));
        assertThat(responseBody,Matchers.containsString("\"errorFields\":[\"email\"]"));
    }

    @Test
    public void testGetPatient() throws Exception {
        // Given
        Long patientId = 1L;
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("patient@example.com");
        patientDTO.setPhoneNumber(1234567890L);
        patientDTO.setName("Patient test");
        patientDTO.setDateOfBirth("1999-12-31");
        patientDTO.setMedicalHistory("Meds history----- some long data-------");
        AddressDTO addressDTO= new AddressDTO();
        addressDTO.setCity("Hawai");
        addressDTO.setStreet("Some street");
        addressDTO.setState("USA");
        addressDTO.setZip(123455);
        patientDTO.setAddress( addressDTO );
        // Set patientDTO attributes based on your requirements

        when(patientService.getPatientDetails(patientId)).thenReturn(patientDTO);

        // When
        mockMvc.perform(get("/getPatient")
                        .param("patientId", patientId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Add additional assertions based on the expected response content
                .andExpect(jsonPath("$.name").value("Patient test"))
                .andExpect(jsonPath("$.email").value("patient@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value(1234567890L));
        // Verify other attributes
    }

    @Test
    public void testGetPatient_InvalidId() throws Exception {
        // Given an invalid patientId (non-numeric)
        String invalidPatientId = "invalid";

        // When
        mockMvc.perform(get("/getPatient")
                        .param("patientId", invalidPatientId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllPatients() throws Exception {
        // Given
        List<PatientDTO> patientDTOList = Arrays.asList(new PatientDTO(), new PatientDTO());
        when(patientService.getAllPatients()).thenReturn(patientDTOList);

        // When
        mockMvc.perform(get("/allPatients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    private static String asJsonString(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }

}
