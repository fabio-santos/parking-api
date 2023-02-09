package com.sealed.parking.domain.vehicle;

import com.sealed.parking.domain.entity.VehicleType;
import com.sealed.parking.domain.exception.VehicleNotMapped;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VehicleFactory {

    private MotorcycleService motorcycleService;
    private CarService carService;
    private VanService vanService;

    public VehicleInterface getVehicleService(VehicleType vehicleType) throws VehicleNotMapped {
        if(VehicleType.MOTORCYCLE.equals(vehicleType)) {
            return motorcycleService;
        }
        if(VehicleType.CAR.equals(vehicleType)) {
            return carService;
        }
        if(VehicleType.VAN.equals(vehicleType)) {
            return vanService;
        }

        throw new VehicleNotMapped();
    }

}
