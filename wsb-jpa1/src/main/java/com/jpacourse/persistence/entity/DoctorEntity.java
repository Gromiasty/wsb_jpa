package com.jpacourse.persistence.entity;

import com.jpacourse.persistence.enums.Specialization;

import javax.persistence.*;

@Entity
@Table(name = "DOCTOR")
@NamedQuery(
    name = "DoctorEntity.findPatientNamesByDoctorId",
    query = "SELECT DISTINCT CONCAT(p.firstName, ' ', p.lastName) FROM PatientEntity p JOIN p.visits v WHERE v.doctor.id = :doctorId"
)
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String telephoneNumber;

    private String email;

    @Column(nullable = false)
    private String doctorNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    /**
     * Unidirectional relationship with {@link AddressEntity}, where the DoctorEntity class
     * is the dependent side (child). Each doctor has exactly one assigned address.
     *
     * <p>The {@code address_id} column in the table represents the foreign key pointing to the AddressEntity.
     * The address is required, so the {@code nullable} attribute is set to {@code false}.
     */
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = true)
    private AddressEntity address;

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

    public String getDoctorNumber() {
        return doctorNumber;
    }

    public void setDoctorNumber(String doctorNumber) {
        this.doctorNumber = doctorNumber;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}