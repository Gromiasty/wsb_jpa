package com.jpacourse.dto;

import java.util.List;
import java.time.LocalDate;

public class PatientTO {
    // Unique identifier for the patient
    private Long id;
    
    // Patient's first name
    private String firstName;
    
    // Patient's last name
    private String lastName;
    
    // Patient's contact number
    private String telephoneNumber;
    
    // Patient's email address
    private String email;

    // Patient's date of birth
    private LocalDate dateOfBirth;
    
    // Indicates if the patient is insured
    private Boolean isInsured;
        
    // List of visits associated with the patient
    private List<VisitTO> visits;
        
    // Address of the patient
    private AddressTO address;
    
    // Unique patient number for identification
    private String patientNumber;
    


    // Default constructor
    public PatientTO() {}

    // Parameterized constructor to initialize all fields
    public PatientTO(Long id, String firstName, String lastName, String telephoneNumber, String email,
                     String patientNumber, LocalDate dateOfBirth, Boolean isInsured, List<VisitTO> visits, AddressTO address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.patientNumber = patientNumber;
        this.dateOfBirth = dateOfBirth;
        this.isInsured = isInsured;
        this.visits = visits;
        this.address = address;
    }

    // Getters and setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(Boolean isInsured) {
        this.isInsured = isInsured;
    }

    public List<VisitTO> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitTO> visits) {
        this.visits = visits;
    }

    public AddressTO getAddress() {
        return address;
    }

    public void setAddress(AddressTO address) {
        this.address = address;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }


}