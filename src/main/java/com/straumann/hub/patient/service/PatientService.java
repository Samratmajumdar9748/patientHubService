package com.straumann.hub.patient.service;

import com.straumann.hub.patient.AddressDTO;
import com.straumann.hub.patient.PatientDTO;
import com.straumann.hub.patient.entity.Address;
import com.straumann.hub.patient.entity.Patient;
import com.straumann.hub.patient.exception.PatientInsertException;
import com.straumann.hub.patient.exception.PatientNotFoundException;
import com.straumann.hub.patient.repo.AddressRepository;
import com.straumann.hub.patient.repo.PatientRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "patient")
public class PatientService {

    private PatientRepository patientRepository;
    private AddressRepository addressRepository;
    private ModelMapper modelMapper;

    @Transactional
    public long addPatient(PatientDTO patientDTO){

        //first insert address
        Long addrId=insertAddress(patientDTO.getAddress());

        Patient patient= new Patient();
        patient.setAddress(addrId==-1?null:addrId);
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setMedicalHistory(patientDTO.getMedicalHistory());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());

        try {
            Date date =  new SimpleDateFormat("yyyy-MM-dd").parse(patientDTO.getDateOfBirth());
            patient.setDateOfBirth(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            Patient savedPatient = patientRepository.save(patient);
            return savedPatient.getPatientId();
        }catch (Exception exception){
         throw new PatientInsertException(400,"Patient Insertion failed! Try again!");
        }

    }

    @Transactional
    public long insertAddress(AddressDTO addressDTO){

        Address address= new Address();
        address.setStreet(addressDTO.getStreet().isBlank()?null: addressDTO.getStreet());
        address.setZip(Objects.isNull( addressDTO.getZip())?null:addressDTO.getZip() );
        address.setCity(addressDTO.getCity().isBlank()?null:addressDTO.getCity());
        address.setState(addressDTO.getState().isBlank()?null: addressDTO.getState());
        try{
            Address savedAddress= addressRepository.save(address);
            return savedAddress.getAddrId();
        }catch (Exception ex){
            System.out.println(ex.fillInStackTrace());
        }
        return -1;
    }

    @Cacheable
    public PatientDTO getPatientDetails(Long patientId){
        if (patientRepository.existsById(patientId)){
            PatientDTO patientDTO=new PatientDTO();
            Patient patient = patientRepository.findById(patientId).get();
            Address address;
            if (addressRepository.existsById(patient.getAddress())){
                address=addressRepository.findById(patient.getAddress()).get();
                patientDTO.setAddress(modelMapper.map(address, AddressDTO.class));
            }

            patientDTO.setName(patient.getName());
            patientDTO.setEmail(patient.getEmail());
            patientDTO.setPhoneNumber(patient.getPhoneNumber());
            patientDTO.setMedicalHistory(patient.getMedicalHistory());
            patientDTO.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateOfBirth()));
            return patientDTO;
        }
        else {
            throw new PatientNotFoundException();
        }

    }

    @Cacheable
    public List<PatientDTO> getAllPatients(){
        return patientRepository.findAll().stream().map(p-> modelMapper.map(p,PatientDTO.class)).collect(Collectors.toList());
    }

}
