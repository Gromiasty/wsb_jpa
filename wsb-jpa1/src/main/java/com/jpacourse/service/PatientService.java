package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.util.List;


public interface PatientService
{

    PatientTO getPatientById(Long id);


    PatientTO createPatient(PatientTO patientTO) ;

    void deletePatient(Long id);

    PatientTO updatePatient(Long id, PatientTO patientTO);

    /**
     * Pobieranie wszystkich pacjent�w
     * @return
     */
    List<PatientTO> getAllPatients();


    /**
     * Dodawanie wizyty do pacjenta (kaskadowo)
     * @param patientId
     * @param doctorId
     * @param description
     * @param visitTime
     */
    void addVisitToPatient(Long patientId, Long doctorId, String description, String visitTime);

}
