package com.sealed.parking.domain.vehicle;

import com.sealed.parking.domain.entity.ParkingSpot;
import com.sealed.parking.domain.entity.Vehicle;
import com.sealed.parking.domain.exception.NoSpotsAvailable;

public interface VehicleInterface {
    ParkingSpot park(Vehicle vehicle) throws NoSpotsAvailable;
}
