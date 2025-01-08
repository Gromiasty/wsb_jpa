package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.entity.*;
import com.jpacourse.persistence.enums.TreatmentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.jpacourse.persistence.enums.Specialization;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional  // Added to manage transactions during tests
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao; // Added for address management


    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void initializeData() {
        System.out.println("[INIT] Setting up test data for patients...");

        // Creating address data
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("456 Example Avenue");
        address.setAddressLine2("Suite 7C");
        address.setCity("Sample City");
        address.setPostalCode("98-765");

        address = addressDao.save(address);  // Save address

        // Creating test data for patient
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jane");
        patient.setLastName("Smith");
        patient.setTelephoneNumber("5559876543");
        patient.setEmail("jane.smith@example.com");
        patient.setPatientNumber("P456");
        patient.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patient.setIsInsured(true);
        patient.setAddress(address);
        patient.setAge(40);

        patient = patientDao.save(patient);  // Save patient in database

        // Creating visit data
        VisitEntity visit = new VisitEntity();
        visit.setDescription("Routine Checkup");
        visit.setTime(java.time.LocalDateTime.of(2025, 1, 2, 11, 0));
        visit.setPatient(patient);

        // Creating doctor (ensure doctor with ID 1 exists in the database)
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, 1L); // Doctor with ID 1
        if (doctor == null) {
            // Create doctor if not exists
            doctor = new DoctorEntity();
            doctor.setFirstName("Adam");
            doctor.setLastName("Johnson");
            doctor.setTelephoneNumber("987654321");
            doctor.setEmail("adam.johnson@example.com");
            doctor.setDoctorNumber("D002");
            doctor.setSpecialization(Specialization.DERMATOLOGIST);
            doctor.setAddress(address);
            doctor = entityManager.merge(doctor);
        }
        visit.setDoctor(doctor);

        // Creating treatment (ensure treatment with ID 1 exists)
        MedicalTreatmentEntity treatment = entityManager.find(MedicalTreatmentEntity.class, 1L); // Treatment with ID 1
        if (treatment == null) {
            // Create treatment if not exists
            treatment = new MedicalTreatmentEntity();
            treatment.setDescription("Abdominal Ultrasound");
            treatment.setType(TreatmentType.USG);
            treatment = entityManager.merge(treatment);
        }
        visit.setMedicalTreatment(treatment);

        // Adding visit to patient
        if (patient.getVisits() == null) {
            patient.setVisits(new ArrayList<>());  // Initialize visit list
        }
        patient.getVisits().add(visit);

        patientDao.save(patient);  // Save patient with visit

        // Verifying data
        PatientEntity retrievedPatient = patientDao.findOne(patient.getId());
        System.out.println("Patient visits after save: " + retrievedPatient.getVisits());

        System.out.println("[INIT] Patient saved in database with ID: " + patient.getId());
        System.out.println("[INIT] Patient address saved: " + patient.getAddress().getId());
    }

    @Test
    @Transactional
    public void removePatientTest() {
        Long patientId = 1L;
        System.out.println("[TEST] removePatientTest - Deleting patient with ID: " + patientId);

        // Calling the method to delete patient
        patientService.deletePatient(patientId);
        System.out.println("[TEST] deletePatient method called.");

        // Verifying that the patient has been deleted
        PatientEntity patient = patientDao.findOne(patientId);
        System.out.println("[TEST] Patient value after deletion: " + patient);
        assertThat(patient).describedAs("Patient should be deleted").isNull();
    }

    @Test
    public void fetchPatientByIdTest() {
        PatientEntity patient = patientDao.findByLastName("Smith").get(0);
        Long patientId = patient.getId();
        System.out.println("[TEST] fetchPatientByIdTest - Fetching patient with ID: " + patientId);

        // Fetching patient data
        PatientTO patientTO = patientService.getPatientById(patientId);
        System.out.println("[TEST] Retrieved patient data: " + patientTO);

        // Verifying the correctness of returned data
        assertThat(patientTO).isNotNull();
        System.out.println("[TEST] Patient is not null");
        assertThat(patientTO.getId()).isEqualTo(patientId);
        System.out.println("[TEST] Patient ID is correct");
        System.out.println("[TEST] Patient visits: " + patientTO.getVisits());
        assertThat(patientTO.getVisits()).isNotNull();
        assertThat(patientTO.getVisits()).isNotEmpty();
        System.out.println("[TEST] Patient visits are not empty");
        assertThat(patientTO.getIsInsured()).isNotNull();
        assertThat(patientTO.getIsInsured()).isTrue();
        System.out.println("[TEST] Patient insurance status is correct");
    }

    @Test
    public void fetchVisitsByPatientIdTest() {
        Long patientId = 1L;
        System.out.println("[TEST] fetchVisitsByPatientIdTest - Fetching visits for patient with ID: " + patientId);

        // Fetching patient data
        PatientTO patient = patientService.getPatientById(patientId);
        assertThat(patient).isNotNull().describedAs("Patient should exist in the database");

        // Fetching visit list
        List<VisitTO> visits = patient.getVisits();
        assertThat(visits).isNotNull().describedAs("Visit list should not be empty");
        assertThat(visits).isNotEmpty().describedAs("Patient should have at least one visit");

        // Verifying visit data
        visits.forEach(visit -> {
            System.out.println("[TEST] Checking visit: ID " + visit.getId() + ", Description: " + visit.getDescription());
            assertThat(visit.getVisitTime()).isNotNull().describedAs("Visit date should not be null");
        });
    }
}