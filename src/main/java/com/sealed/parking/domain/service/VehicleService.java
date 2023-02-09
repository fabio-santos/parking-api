package com.sealed.parking.domain.service;

import com.sealed.parking.domain.entity.Vehicle;
import com.sealed.parking.domain.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleService {

    private VehicleRepository repository;

    public Vehicle save(Vehicle vehicle) {

        Optional<Vehicle> existingVehicle = repository.findByPlate(vehicle.getPlate());

        if(existingVehicle.isPresent()) {
            return existingVehicle.get();
        }

        return repository.save(vehicle);
    }
}
