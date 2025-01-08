package com.jpacourse.persistence.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jpacourse.persistence.entity.VisitEntity;

@Repository
public interface VisitDao extends Dao<VisitEntity, Long> {
    public List<VisitEntity> findAllVisitsByPatientId(Long patientId);
}