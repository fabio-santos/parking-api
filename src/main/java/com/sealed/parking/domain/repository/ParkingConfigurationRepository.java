package com.sealed.parking.domain.repository;

import com.sealed.parking.domain.entity.ParkingConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingConfigurationRepository extends JpaRepository<ParkingConfiguration, Long> {

}
