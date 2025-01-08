package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.Collections;

public class VisitMapper {

    // Mapping VisitEntity to VisitTO
    public static VisitTO toTO(VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setVisitTime(visitEntity.getTime());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
        visitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());
        visitTO.setTreatmentDescription(
                visitEntity.getMedicalTreatment() != null ?
                        visitEntity.getMedicalTreatment().getDescription() : null
        );

        // Getting treatment description from a single MedicalTreatmentEntity object
        visitTO.setMedicalTreatments(
                visitEntity.getMedicalTreatment() != null ?
                        Collections.singletonList(visitEntity.getMedicalTreatment().getDescription()) :
                        Collections.emptyList()
        );

        return visitTO;
    }
}