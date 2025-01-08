package com.jpacourse.persistence.entity;

import java.time.LocalDate;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PATIENT")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version; // Version field for optimistic locking

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String telephoneNumber;

    private String email;

    @Column(nullable = false)
    private String patientNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    // New field for insurance
    private Boolean isInsured;

    @Column(nullable = false)
    private Integer age; // New column - patient's age

    /**
     * Unidirectional relationship with {@link AddressEntity}, where the PatientEntity class
     * is the dependent side (child). Each patient has exactly one assigned address.
     *
     * The {@code address_id} column in the table represents the foreign key pointing to the AddressEntity.
     * The address is required, so the {@code nullable} attribute is set to {@code false}.
     */
    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    /**
     * List of visits associated with the given patient.
     *
     * Bidirectional relationship with {@link VisitEntity}, where the PatientEntity class
     * is the parent side, and the VisitEntity class is the owner of the relationship.
     *
     * The {@code mappedBy = "patient"} attribute indicates that the owner of the relationship
     * is the {@code patient} field in the VisitEntity class. Thanks to {@code CascadeType.ALL},
     * all operations on the patient are cascaded to the associated visits.
     * The {@code orphanRemoval = true} option ensures that visits removed from the list are also deleted.
     */
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitEntity> visits;

    // Getters and Setters
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

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
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

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<VisitEntity> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitEntity> visits) {
        this.visits = visits;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}