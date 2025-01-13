package com.jpacourse.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.VisitEntity;

@Repository
public class VisitDaoImpl extends AbstractDao<VisitEntity, Long> implements VisitDao {

    @Override
    public List<VisitEntity> findAllVisitsByPatientId(Long patientId) {
        return entityManager.createNamedQuery("VisitEntity.findAllVisitsByPatientId", VisitEntity.class)
        .setParameter("patientId", patientId)
        .getResultList();
    }
    
}
