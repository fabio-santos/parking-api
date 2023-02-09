package com.sealed.parking.domain.vehicle;

import com.sealed.parking.domain.entity.ParkingSpot;
import com.sealed.parking.domain.entity.Vehicle;
import com.sealed.parking.domain.entity.VehicleType;
import com.sealed.parking.domain.exception.NoSpotsAvailable;
import com.sealed.parking.domain.repository.ParkingSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MotorcycleService implements VehicleInterface {

    private ParkingSpotRepository parkingSpotRepository;

    @Override
    public ParkingSpot park(Vehicle vehicle) throws NoSpotsAvailable {
        ParkingSpot spot = this.searchForAvailableSpot();

        spot.setVehicle(vehicle);
        this.parkingSpotRepository.save(spot);

        return spot;
    }

    public ParkingSpot searchForAvailableSpot() throws NoSpotsAvailable {
        Optional<ParkingSpot> spot = this.parkingSpotRepository.findFirstBySpotTypeAndVehicleIsNull(VehicleType.MOTORCYCLE);

        if(spot.isPresent()) {
            return spot.get();
        }

        spot = this.parkingSpotRepository.findFirstBySpotTypeAndVehicleIsNull(VehicleType.CAR);

        if(spot.isPresent()) {
            return spot.get();
        }

        spot = this.parkingSpotRepository.findFirstBySpotTypeAndVehicleIsNull(VehicleType.VAN);

        if(spot.isPresent()) {
            return spot.get();
        }

        throw new NoSpotsAvailable();
    }
}
