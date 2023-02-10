package com.sealed.parking.domain.service;

import com.sealed.parking.domain.dto.ParkingDTO;
import com.sealed.parking.domain.entity.ParkingSpot;
import com.sealed.parking.domain.entity.Vehicle;
import com.sealed.parking.domain.entity.VehicleType;
import com.sealed.parking.domain.exception.NoSpotsAvailable;
import com.sealed.parking.domain.exception.VehicleAlreadyParkedException;
import com.sealed.parking.domain.exception.VehicleNotMapped;
import com.sealed.parking.domain.repository.ParkingSpotRepository;
import com.sealed.parking.domain.vehicle.VehicleFactory;
import com.sealed.parking.domain.vehicle.VehicleInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingSpotService {

    private ParkingSpotRepository repository;
    private VehicleFactory vehicleFactory;
    private VehicleService vehicleService;

    public @ResponseBody
    List<ParkingSpot> getParkedVehicles() {
        return this.repository.findByVehicleIsNotNull();
    }

    public void reset() {
        repository.deleteAll();
    }

    public void saveAll(List<ParkingSpot> parkingSpotList) {
        repository.saveAll(parkingSpotList);
    }

    public ParkingDTO getAvailableSpots() {
        ParkingDTO availableSpots = new ParkingDTO();
        availableSpots.setMotorcycleSpots(repository.countBySpotTypeAndVehicleIsNull(VehicleType.MOTORCYCLE));
        availableSpots.setCarSpots(repository.countBySpotTypeAndVehicleIsNull(VehicleType.CAR));
        availableSpots.setVanSpots(repository.countBySpotTypeAndVehicleIsNull(VehicleType.VAN));

        return availableSpots;
    }

    public boolean isParkingLotFull() {
        ParkingDTO availableSpots = this.getAvailableSpots();

        return availableSpots.getCarSpots() <= 0 &&
                availableSpots.getMotorcycleSpots() <= 0 &&
                availableSpots.getVanSpots() <= 0;
    }

    public ParkingDTO getTakenSpots() {
        ParkingDTO availableSpots = new ParkingDTO();
        availableSpots.setMotorcycleSpots(repository.countBySpotTypeAndVehicleIsNotNull(VehicleType.MOTORCYCLE));
        availableSpots.setCarSpots(repository.countBySpotTypeAndVehicleIsNotNull(VehicleType.CAR));
        availableSpots.setVanSpots(repository.countBySpotTypeAndVehicleIsNotNull(VehicleType.VAN));

        return availableSpots;
    }

    public ParkingSpot park(Vehicle vehicle) throws VehicleNotMapped, NoSpotsAvailable, VehicleAlreadyParkedException {
        Optional<ParkingSpot> alreadyParked = this.repository.findFirstByVehiclePlate(vehicle.getPlate());
        if(alreadyParked.isPresent()) {
            throw new VehicleAlreadyParkedException();
        }

        VehicleInterface vehicleInterface = vehicleFactory.getVehicleService(vehicle.getVehicleType());
        return vehicleInterface.park(this.vehicleService.save(vehicle));
    }

    public void deleteById(Long id) {
        Optional<ParkingSpot> spot = this.repository.findById(id);

        if(spot.isPresent()) {
            ParkingSpot parkingSpot = spot.get();
            parkingSpot.setVehicle(null);
            repository.save(parkingSpot);
        }
    }

    public void deleteByVehicle(Long vehicle) {
        List<ParkingSpot> spots = this.repository.findByVehicleId(vehicle);

        if(!spots.isEmpty()) {
            spots.forEach(spot -> {
                spot.setVehicle(null);
                repository.save(spot);
            });
        }
    }
}
