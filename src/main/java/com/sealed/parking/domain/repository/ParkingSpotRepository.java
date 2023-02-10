package com.sealed.parking.domain.repository;

import com.sealed.parking.domain.entity.ParkingSpot;
import com.sealed.parking.domain.entity.VehicleType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    int countBySpotTypeAndVehicleIsNull(VehicleType vehicleType);

    int countBySpotTypeAndVehicleIsNotNull(VehicleType vehicleType);

    Optional<ParkingSpot> findFirstBySpotTypeAndVehicleIsNull(VehicleType vehicleType);

    Optional<ParkingSpot> findFirstByVehiclePlate(String plate);

    List<ParkingSpot> findBySpotTypeAndVehicleIsNull(VehicleType vehicleType, Pageable pageable);

    List<ParkingSpot> findByVehicleId(Long vehicle);

    List<ParkingSpot> findByVehicleIsNotNull();
}
