package com.sealed.parking.controller;

import com.sealed.parking.domain.entity.ParkingSpot;
import com.sealed.parking.domain.entity.Vehicle;
import com.sealed.parking.domain.exception.NoSpotsAvailable;
import com.sealed.parking.domain.exception.VehicleAlreadyParkedException;
import com.sealed.parking.domain.exception.VehicleNotMapped;
import com.sealed.parking.domain.service.ParkingSpotService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/park")
@AllArgsConstructor
public class ParkingController {

    private ParkingSpotService service;

    @PostMapping
    public ParkingSpot park(@RequestBody Vehicle vehicle) throws VehicleNotMapped, NoSpotsAvailable, VehicleAlreadyParkedException {
        return service.park(vehicle);
    }

    @DeleteMapping("/{id}")
    public void deleteBySpotId(@PathVariable Long spotId) {
        service.deleteById(spotId);
    }

    @DeleteMapping("/vehicle/{vehicleId}")
    public void deleteByVehicleId(@PathVariable Long vehicleId) {
        service.deleteByVehicle(vehicleId);
    }

}
