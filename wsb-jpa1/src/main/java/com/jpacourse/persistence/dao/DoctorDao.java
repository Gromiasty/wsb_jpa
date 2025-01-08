package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface DoctorDao extends Dao<DoctorEntity, Long> {


}
