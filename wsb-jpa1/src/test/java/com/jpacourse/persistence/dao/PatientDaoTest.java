package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;

import org.apache.tomcat.jni.Address;
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

    @Autowired
    private DoctorDao doctorDao;

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
        
        // Creating new Patient
        PatientEntity testPatient = new PatientEntity();
        testPatient.setFirstName("Carl");
        testPatient.setLastName("xyz");
        testPatient.setTelephoneNumber("123456789");
        testPatient.setEmail("carl.xyz@example.com");
        testPatient.setPatientNumber("P555");
        testPatient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        testPatient.setAge(35);
        patientDao.save(testPatient);
        
        // Finding by last name
        List<PatientEntity> testPatients = patientDao.findByLastName("xyz");

        // Checking the results
        assertThat(testPatients).hasSize(1);
        assertThat(testPatients.get(0).getFirstName()).isEqualTo("Carl");
        assertThat(testPatients.get(0).getLastName()).isEqualTo("xyz");
    }

    @Test
    @Transactional
    public void findByVisitsGreaterThanTest() {

        // Creating new Patients
        PatientEntity testPatient1 = new PatientEntity();
        testPatient1.setFirstName("Catherine");
        testPatient1.setLastName("Moore");
        testPatient1.setTelephoneNumber("678678678");
        testPatient1.setEmail("catherine.moore@example.com");
        testPatient1.setPatientNumber("P001");
        testPatient1.setDateOfBirth(LocalDate.of(1985, 5, 15));
        testPatient1.setAge(38);
        patientDao.save(testPatient1);

        PatientEntity testPatient2 = new PatientEntity();
        testPatient2.setFirstName("Jane");
        testPatient2.setLastName("Adams");
        testPatient2.setTelephoneNumber("159753123");
        testPatient2.setEmail("jane.adams@example.com");
        testPatient2.setPatientNumber("PA34");
        testPatient2.setDateOfBirth(LocalDate.of(1995, 4, 12));
        testPatient2.setAge(29);
        patientDao.save(testPatient2);

        // Creating new doctor
        DoctorEntity testDoc = new DoctorEntity();
        testDoc.setFirstName("Maksymilian");
        testDoc.setLastName("Nowak");
        testDoc.setTelephoneNumber("789654857");
        testDoc.setEmail("maksymilian.nowak@example.com");
        testDoc.setDoctorNumber("DO99");
        testDoc.setSpecialization(Specialization.SURGEON);
        doctorDao.save(testDoc);

        // Adding visits
        patientDao.addVisit(testPatient1.getId(), testDoc.getId(), "Surgery", LocalDateTime.of(2024, 04, 01, 0, 0, 0, 0), 1L);
        entityManager.flush();
        patientDao.addVisit(testPatient1.getId(), testDoc.getId(), "Consultation", LocalDateTime.of(2025, 01, 01, 0, 0, 0, 0), 2L);
        entityManager.flush();
        patientDao.addVisit(testPatient1.getId(), testDoc.getId(), "Last visit", LocalDateTime.of(2025, 02, 01, 0, 0, 0, 0), 3L);
        entityManager.flush();
        patientDao.addVisit(testPatient2.getId(), testDoc.getId(), "Consultation", LocalDateTime.of(2025, 02, 01, 0, 0, 0, 0), 4L);
        entityManager.flush();
        // Finding patients with visits more than X visits
        List<PatientEntity> testPatients = patientDao.findByVisitsGreaterThan(2L);

        // Checking the results
        assertThat(testPatients).hasSize(6);
        assertThat(testPatients.get(0).getFirstName()).isEqualTo("Catherine");
        assertThat(testPatients.get(0).getLastName()).isEqualTo("Moore");
    }

    @Test
    public void findByAgeGreaterThanTest() {
        // Creating new Patients
        PatientEntity testPatient1 = new PatientEntity();
        testPatient1.setFirstName("Daniel");
        testPatient1.setLastName("Kwiat");
        testPatient1.setTelephoneNumber("102054305");
        testPatient1.setEmail("daniel.kwiat@example.com");
        testPatient1.setPatientNumber("P045");
        testPatient1.setDateOfBirth(LocalDate.of(1924, 1, 1));
        testPatient1.setAge(101);
        patientDao.save(testPatient1);

        PatientEntity testPatient2 = new PatientEntity();
        testPatient2.setFirstName("Kamila");
        testPatient2.setLastName("Pietras");
        testPatient2.setTelephoneNumber("987654321");
        testPatient2.setEmail("kamila.pietras@example.com");
        testPatient2.setPatientNumber("PA56");
        testPatient2.setDateOfBirth(LocalDate.of(1923, 1, 2));
        testPatient2.setAge(102);
        patientDao.save(testPatient2);

        PatientEntity testPatient3 = new PatientEntity();
        testPatient3.setFirstName("Kamila");
        testPatient3.setLastName("Pietras");
        testPatient3.setTelephoneNumber("159874562");
        testPatient3.setEmail("kamila.pietras@example.com");
        testPatient3.setPatientNumber("PA56");
        testPatient3.setDateOfBirth(LocalDate.of(2000, 1, 2));
        testPatient3.setAge(25);
        patientDao.save(testPatient3);

        // Finding patients with age more than X years
        List<PatientEntity> testPatients = patientDao.findByAgeGreaterThan(100);

        //Checking the results
        assertThat(testPatients).hasSize(2);
        assertThat(testPatients.get(0).getFirstName()).isEqualTo("Daniel");
        assertThat(testPatients.get(0).getLastName()).isEqualTo("Kwiat");
        assertThat(testPatients.get(1).getFirstName()).isEqualTo("Kamila");
        assertThat(testPatients.get(1).getLastName()).isEqualTo("Pietras");

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