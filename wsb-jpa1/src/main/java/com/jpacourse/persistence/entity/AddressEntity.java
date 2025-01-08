package com.jpacourse.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;

    /**
     * Bidirectional relationship with {@link DoctorEntity}, where the AddressEntity class
     * is the parent (main) side. An address can be associated with many doctors.
     *
     * <p>The {@code doctors} field represents a list of doctors associated with the given address.
     * The {@code mappedBy = "address"} attribute indicates that the owner of the relationship is the DoctorEntity class.
     * Thanks to {@code CascadeType.ALL}, operations on the AddressEntity propagate to the associated doctors.
     */
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<DoctorEntity> doctors = new ArrayList<>();

    /**
     * Bidirectional relationship with {@link PatientEntity}, where the AddressEntity class
     * is the parent (main) side. An address can be associated with many patients.
     *
     * <p>The {@code patients} field represents a list of patients associated with the given address.
     * The {@code mappedBy = "address"} attribute indicates that the owner of the relationship is the PatientEntity class.
     * Thanks to {@code CascadeType.ALL}, operations on the AddressEntity propagate to the associated patients.
     */
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<PatientEntity> patients = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}