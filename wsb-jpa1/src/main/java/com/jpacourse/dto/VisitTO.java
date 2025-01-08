package com.jpacourse.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VisitTO {

    // Unique identifier for the visit
    private Long id;
    
    // Date and time of the visit
    private LocalDateTime visitTime;
    
    // Description of the visit
    private String description;
    
    // First name of the doctor
    private String doctorFirstName;
    
    // Last name of the doctor
    private String doctorLastName;
    
    // First name of the patient
    private String patientFirstName;
    
    // Last name of the patient
    private String patientLastName;
    
    // List of treatment types involved in the visit
    private List<String> treatmentTypes;
    
    // Description of the treatment
    private String treatmentDescription;

    // Default constructor
    public VisitTO() {}

    // Parameterized constructor to initialize all fields
    public VisitTO(Long id, LocalDateTime visitTime, String doctorFirstName, String doctorLastName, String patientFirstName, String patientLastName, List<String> treatmentTypes, String treatmentDescription) {
        this.id = id;
        this.visitTime = visitTime;
        this.description = description;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.treatmentTypes = treatmentTypes;
        this.treatmentDescription = treatmentDescription;
    }

    // Getters and setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public List<String> getTreatmentTypes() {
        return treatmentTypes;
    }

    public void setTreatmentTypes(List<String> treatmentTypes) {
        this.treatmentTypes = treatmentTypes;
    }

    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }

    public List<String> getMedicalTreatments() { 
        return treatmentTypes; 
    }

    public void setMedicalTreatments(List<String> medicalTreatments) { 
        this.treatmentTypes = medicalTreatments; 
    }
}