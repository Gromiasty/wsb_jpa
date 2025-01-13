package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Autowired
    private DoctorDao doctorDao;

    @Transactional
    @Override
    public void addVisit(Long patientId, Long doctorId, String visitDescription, java.time.LocalDateTime visitDate, Long treatmentId) {
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = doctorDao.findOne(doctorId);
        MedicalTreatmentEntity medicalTreatment = entityManager.find(MedicalTreatmentEntity.class, treatmentId);
        
        if (medicalTreatment == null) {
            throw new IllegalArgumentException("Invalid treatment ID provided");
        }

        VisitEntity newVisit = new VisitEntity();
        newVisit.setPatient(patient);
        newVisit.setDoctor(doctor);
        newVisit.setDescription(visitDescription);
        newVisit.setTime(visitDate);
        newVisit.setMedicalTreatment(medicalTreatment);

        patient.getVisits().add(newVisit);
        entityManager.merge(patient);
    }

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
        return entityManager.createNamedQuery("PatientEntity.findByLastName", PatientEntity.class)
        .setParameter("lastName", lastName)
        .getResultList();
    }

    @Override
    public List<PatientEntity> findByVisitsGreaterThan(int visitCount) {
        return entityManager.createNamedQuery("PatientEntity.findByVisitsGreaterThan", PatientEntity.class)
        .setParameter("visitCount", visitCount)
        .getResultList();
    }

    @Override
    public List<PatientEntity> findByAgeGreaterThan(Integer age) {
        return entityManager.createNamedQuery("PatientEntity.findByAgeGreaterThan", PatientEntity.class)
        .setParameter("age", age)
        .getResultList();
    }
}