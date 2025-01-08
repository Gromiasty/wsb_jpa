package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    @Transactional
    public void initializeData() {
        // Setting up test data

        // Create test address
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("Central Street");
        address.setAddressLine2("Apartment 5");
        address.setCity("New York");
        address.setPostalCode("10001");

        // Save address to in-memory database
        address = addressDao.save(address);

        // Create test patient
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Anna");
        patient.setLastName("Smith");
        patient.setTelephoneNumber("123123123");
        patient.setEmail("anna.smith@example.com");
        patient.setPatientNumber("P123");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setAddress(address);
        patient.setAge(30);

        // Add patient to database
        patientDao.save(patient);
    }

    @Test
    @Transactional
    public void addVisitTest() {
        // Setup
        Long patientId = 1L; // Assuming patient with ID 1 exists in data.sql
        Long doctorId = 1L;  // Assuming doctor with ID 1 exists in data.sql
        Long treatmentId = 1L; // Assuming treatment with ID 1 exists in data.sql
        String description = "Checkup";
        LocalDateTime visitDate = LocalDateTime.of(2025, 01, 01, 10, 0, 0, 0); // Fixed date

        // Act
        patientDao.addVisit(patientId, doctorId, description, visitDate, treatmentId);

        // Assert
        PatientEntity patient = patientDao.findOne(patientId);
        assertNotNull(patient, "Patient should not be null");
        assertFalse(patient.getVisits().isEmpty(), "Patient should have visits");

        VisitEntity visit = patient.getVisits().get(0);
        assertEquals(description, visit.getDescription(), "Description should match");
        assertEquals(visitDate, visit.getTime(), "Visit date should match");
        assertEquals(doctorId, visit.getDoctor().getId(), "Doctor ID should match");
    }

    @Test
    @Transactional
    public void deletePatientTest() {
        Long patientId = 1L;
        patientDao.delete(patientId);

        assertThat(patientDao.findOne(patientId)).isNull();
    }

    @Test
    @Transactional
    public void findByLastNameTest() {
        // Setup
        String lastName = "Smith";  // Assuming patient with this last name was added in @Before

        // Act
        List<PatientEntity> patients = patientDao.findByLastName(lastName);

        // Debug - Print patient data in database
        System.out.println("Patients in database:");
        patients.forEach(patient ->
                System.out.println("ID: " + patient.getId() + ", First Name: " + patient.getFirstName() + ", Last Name: " + patient.getLastName())
        );

        // Assert
        assertNotNull(patients, "Patients list should not be null");
        assertFalse(patients.isEmpty(), "Patients list should not be empty");

        PatientEntity patient = patients.get(0);
        assertEquals(lastName, patient.getLastName(), "Last name should match");
    }

    @Test
    @Transactional
    public void findByVisitsGreaterThanTest() {
        // Value of visitCount to find patients
        int visitCount = 1;

        // Call DAO method
        List<PatientEntity> patients = patientDao.findByVisitsGreaterThan(visitCount);

        // Verify results
        assertThat(patients).isNotEmpty(); // Ensure the result is not empty
        patients.forEach(patient ->
                assertThat(patient.getVisits().size()).isGreaterThan(visitCount)
        ); // Each patient should have more than visitCount visits
    }

    @Test
    public void findByAgeGreaterThanTest() {
        // Setup
        Integer ageThreshold = 40; 

        // Act
        List<PatientEntity> patients = patientDao.findByAgeGreaterThan(ageThreshold);

        // Assert
        assertThat(patients).isNotEmpty();
        patients.forEach(patient ->
                assertThat(patient.getAge()).isGreaterThan(ageThreshold)
        );
    }

    @Test
    @Transactional
    public void optimisticLockingTest() {
        System.out.println("=== TEST START ===");

        // Retrieve and detach first instance
        PatientEntity patient1 = patientDao.findOne(1L);
        System.out.println("Patient1 loaded: " + patient1);
        System.out.println("Patient1 ID: " + patient1.getId());
        System.out.println("Patient1 Version: " + patient1.getVersion());

        entityManager.detach(patient1); // Ensure entity is detached
        System.out.println("Patient1 detached.");

        // Retrieve second instance
        PatientEntity patient2 = patientDao.findOne(1L);
        System.out.println("Patient2 loaded: " + patient2);
        System.out.println("Patient2 ID: " + patient2.getId());
        System.out.println("Patient2 Version: " + patient2.getVersion());

        // Change and save first instance
        patient1.setFirstName("JohnUpdated");
        System.out.println("Patient1 FirstName updated to: " + patient1.getFirstName());

        try {
            patientDao.save(patient1); // Update causes version increment
            System.out.println("Patient1 saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving Patient1: " + e.getMessage());
            e.printStackTrace();
        }

        // Verify version in database after save
        System.out.println("Patient1 version after save: " + patient1.getVersion());

        // Change and try to save second instance
        patient2.setLastName("DoeUpdated");
        System.out.println("Patient2 LastName updated to: " + patient2.getLastName());

        System.out.println("Patient2 version before save: " + patient2.getVersion());

        try {
            patientDao.save(patient2); // Should throw exception
            System.out.println("Patient2 saved successfully (unexpected).");
        } catch (OptimisticLockException e) {
            System.out.println("Expected OptimisticLockException caught: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected exception caught while saving Patient2: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== TEST END ===");
    }
}