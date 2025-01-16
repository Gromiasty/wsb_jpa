package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DoctorDaoImpl extends AbstractDao<DoctorEntity, Long> implements DoctorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> findPatientNamesByDoctorId(Long doctorId) {
        String jpql = "SELECT DISTINCT CONCAT(p.firstName, ' ', p.lastName) FROM PatientEntity p JOIN p.visits v WHERE v.doctor.id = :doctorId";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        query.setParameter("doctorId", doctorId);
        return query.getResultList();
    }
}