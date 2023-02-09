package com.sealed.parking.domain.vehicle;

import com.sealed.parking.domain.entity.ParkingSpot;
import com.sealed.parking.domain.entity.Vehicle;
import com.sealed.parking.domain.entity.VehicleType;
import com.sealed.parking.domain.exception.NoSpotsAvailable;
import com.sealed.parking.domain.repository.ParkingSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VanService implements VehicleInterface {

    private ParkingSpotRepository parkingSpotRepository;
    private static final int numberOfCarSpotsToParkAVan = 3;

    @Override
    public ParkingSpot park(Vehicle vehicle) throws NoSpotsAvailable {
        List<ParkingSpot> parkingSpots = this.searchForAvailableSpot();

        parkingSpots.forEach(spot -> spot.setVehicle(vehicle));

        return this.parkingSpotRepository.saveAll(parkingSpots).get(0);
    }

    public List<ParkingSpot> searchForAvailableSpot() throws NoSpotsAvailable {
        List<ParkingSpot> parkingSpots = new ArrayList<>();

        Optional<ParkingSpot> spot = this.parkingSpotRepository.findFirstBySpotTypeAndVehicleIsNull(VehicleType.VAN);

        if(spot.isPresent()) {
            parkingSpots.add(spot.get());
            return parkingSpots;
        }

        parkingSpots = this.parkingSpotRepository.findBySpotTypeAndVehicleIsNull(VehicleType.CAR, PageRequest.of(0, numberOfCarSpotsToParkAVan));

        if(!parkingSpots.isEmpty() && parkingSpots.size() == numberOfCarSpotsToParkAVan) {
            return parkingSpots;
        }

        throw new NoSpotsAvailable();
    }
}
