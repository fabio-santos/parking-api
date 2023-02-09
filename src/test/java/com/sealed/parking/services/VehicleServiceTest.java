package com.sealed.parking.services;

import com.sealed.parking.domain.entity.Vehicle;
import com.sealed.parking.domain.entity.VehicleType;
import com.sealed.parking.domain.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class VehicleServiceTest {

    @Autowired
    private VehicleService service;

    @Test
    public void saveSameVehiclePlateTwiceShouldReturnSameRecord() {
        String plate = "DSB-1593";

        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();

        vehicle1.setVehicleType(VehicleType.CAR);
        vehicle1.setPlate(plate);

        vehicle2.setVehicleType(VehicleType.CAR);
        vehicle2.setPlate(plate);

        Vehicle savedVehicle1 = service.save(vehicle1);
        Vehicle savedVehicle2 = service.save(vehicle2);

        assertNotNull(savedVehicle1);
        assertNotNull(savedVehicle2);
        assertEquals(savedVehicle1.getId(), savedVehicle1.getId());
    }
}
