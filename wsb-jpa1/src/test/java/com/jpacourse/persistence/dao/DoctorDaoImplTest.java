package com.jpacourse.persistence.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DoctorDaoImplTest {

    @Autowired
    private DoctorDao doctorDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    @Transactional
    public void initializeData() {
        // Custom setup if needed                               
    }

    @Test 
    @Transactional
    public void testFindPatientNamesByDoctorId() {
        Long doctorId = 1L;
        List<String> patientNames = doctorDao.findPatientNamesByDoctorId(doctorId);
        patientNames.forEach(System.out::println);
        assertThat(patientNames).containsExactlyInAnyOrder("Catherine Moore", "Thomas Simmons", "Eve Baker");
    }
}