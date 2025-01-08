package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.entity.DoctorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PatientDao extends Dao<PatientEntity, Long> {
    void addVisit(Long patientId, Long doctorId, String visitDescription, LocalDateTime visitDate, Long treatmentId);
    List<PatientEntity> findByLastName(String lastName);
    List<PatientEntity> findByVisitsGreaterThan(int visitCount);
    List<PatientEntity> findByAgeGreaterThan(Integer age);
}
