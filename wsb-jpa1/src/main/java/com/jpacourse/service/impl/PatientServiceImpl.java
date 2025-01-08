package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.AddressMapper;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDao patientDao;

    @Override
    public PatientTO getPatientById(Long id) {
        PatientEntity patientEntity = patientDao.findOne(id);
        if (patientEntity == null) {
            throw new RuntimeException("Patient not found");
        }

        // Mapowanie do TO i zwrócenie
        return PatientMapper.toTransferObject(patientEntity);
    }

    @Override
    public PatientTO createPatient(PatientTO patientTO) {
        PatientEntity patientEntity = PatientMapper.toEntity(patientTO);
        if (patientTO.getAddress() != null) {
            patientEntity.setAddress(AddressMapper.mapToEntity(patientTO.getAddress()));
        }
        patientEntity = patientDao.save(patientEntity);
        return PatientMapper.toTransferObject(patientEntity);
    }

    @Override
    public void deletePatient(Long id) {
        patientDao.delete(id);
    }

    @Override
    public PatientTO updatePatient(Long id, PatientTO patientTO) {
        PatientEntity patientEntity = patientDao.findOne(id);
        PatientMapper.updateEntity(patientEntity, patientTO);
        patientEntity = patientDao.save(patientEntity);
        return PatientMapper.toTransferObject(patientEntity);
    }

    @Override
    public List<PatientTO> getAllPatients() {
        return patientDao.findAll().stream()
                .map(PatientMapper::toTransferObject)
                .collect(Collectors.toList());
    }

    @Override
    public void addVisitToPatient(Long patientId, Long doctorId, String description, String visitTime) {
        PatientEntity patient = patientDao.findOne(patientId);

        LocalDateTime visitDateTime = LocalDateTime.parse(visitTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Tworzymy wizytę i przypisujemy jej odpowiednie dane
        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitDateTime);
        //visit.setDoctor(patientDao.findDoctorById(doctorId));  // Zakładając, że masz metodę findDoctorById // TODO patientDao nie moze zwracac doktora - musi byc DoctorDao
        visit.setPatient(patient);
        // Dodajemy wizytę do pacjenta
        patient.getVisits().add(visit);

        // Kaskadowe zapisanie pacjenta (update)
        patientDao.save(patient);
    }

    @Override
    public List<VisitEntity> findAllPatientVisitsById(Long patientId) {
        return patientDao.findOne(patientId).getVisits();  // Fixed: Removed unnecessary getId() call
    }

    
}
