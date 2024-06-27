package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{patientId}")
    public ResponseEntity<?> suggestDoctor(@PathVariable Long patientId) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }

        String speciality = getSpecialityBySymptom(patient.getSymptom());
        if (speciality == null) {
            return new ResponseEntity<>("Symptom not recognized", HttpStatus.BAD_REQUEST);
        }

        List<Doctor> doctors = doctorService.getDoctorsByCityAndSpeciality(patient.getCity(), speciality);
        if (doctors.isEmpty()) {
            return new ResponseEntity<>("There isn’t any doctor present at your location for your symptom", HttpStatus.OK);
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    private String getSpecialityBySymptom(String symptom) {
        switch (symptom.toLowerCase()) {
            case "arthritis":
            case "back pain":
            case "tissue injuries":
                return "Orthopaedic";
            case "dysmenorrhea":
                return "Gynecology";
            case "skin infection":
            case "skin burn":
                return "Dermatology";
            case "ear pain":
                return "ENT";
            default:
                return null;
        }
    }
}
