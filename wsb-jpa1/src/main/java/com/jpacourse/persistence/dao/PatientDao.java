package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PatientDao extends Dao<PatientEntity, Long> {
    void addVisit(Long patientId, Long doctorId, String visitDescription, LocalDateTime visitDate, Long treatmentId);
    List<PatientEntity> findByLastName(String lastName);
    List<PatientEntity> findByVisitsGreaterThan(long visitCount);
    List<PatientEntity> findByAgeGreaterThan(Integer age);
}
