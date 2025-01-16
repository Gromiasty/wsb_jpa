package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorDao extends Dao<DoctorEntity, Long> {

    List<String> findPatientNamesByDoctorId(@Param("doctorId") Long doctorId);

}