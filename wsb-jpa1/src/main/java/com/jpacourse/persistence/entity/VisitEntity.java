package com.jpacourse.persistence.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "VISIT")
@NamedQuery(
    name = "VisitEntity.findAllVisitsByPatientId",
    query = "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId"
)
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    private LocalDateTime time;

    /**
     * Bidirectional relationship with {@link DoctorEntity}, where the VisitEntity class
     * is the owner of the relationship. Each visit must be associated with one doctor.
     *
     * The {@code doctor_id} column in the table represents the foreign key pointing to the DoctorEntity.
     * The {@code nullable = false} annotation enforces the presence of a doctor for each visit.
     */
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    /**
     * Bidirectional relationship with {@link PatientEntity}, where the VisitEntity class
     * is the owner of the relationship. Each visit must be associated with one patient.
     *
     * The {@code patient_id} column in the table represents the foreign key pointing to the PatientEntity.
     * The {@code nullable = false} annotation enforces the presence of a patient for each visit.
     */
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "treatment_id", nullable = false)
    private MedicalTreatmentEntity medicalTreatment;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public MedicalTreatmentEntity getMedicalTreatment() {
        return medicalTreatment;
    }

    public void setMedicalTreatment(MedicalTreatmentEntity medicalTreatment) {
        this.medicalTreatment = medicalTreatment;
    }
}