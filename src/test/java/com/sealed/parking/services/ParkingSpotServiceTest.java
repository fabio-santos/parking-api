package com.sealed.parking.services;

import com.sealed.parking.domain.dto.ParkingDTO;
import com.sealed.parking.domain.entity.ParkingConfiguration;
import com.sealed.parking.domain.entity.ParkingSpot;
import com.sealed.parking.domain.entity.Vehicle;
import com.sealed.parking.domain.entity.VehicleType;
import com.sealed.parking.domain.exception.NoSpotsAvailable;
import com.sealed.parking.domain.exception.ParkingNotConfiguredException;
import com.sealed.parking.domain.exception.VehicleAlreadyParkedException;
import com.sealed.parking.domain.exception.VehicleNotMapped;
import com.sealed.parking.domain.service.ParkingConfigurationService;
import com.sealed.parking.domain.service.ParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParkingSpotServiceTest {

    @Autowired
    private ParkingSpotService parkingService;

    @Autowired
    private ParkingConfigurationService configurationService;

    private final int numberOfMotorcycleSpots = 2;
    private final int numberOfCarSpots = 3;
    private final int numberOfVanSpots = 1;

    @BeforeEach
    public void setParkingLotConfiguration() throws ParkingNotConfiguredException {
        ParkingConfiguration configuration = new ParkingConfiguration();

        configuration.setMotorcycleSpots(numberOfMotorcycleSpots);
        configuration.setVanSpots(numberOfVanSpots);
        configuration.setCarSpots(numberOfCarSpots);

        configurationService.update(configuration);
    }

    @Test
    public void allSpotsShouldBeAvailable() {
        ParkingDTO availableSpots = this.parkingService.getAvailableSpots();

        assertNotNull(availableSpots);
        assertEquals(numberOfMotorcycleSpots, availableSpots.getMotorcycleSpots());
        assertEquals(numberOfCarSpots, availableSpots.getCarSpots());
        assertEquals(numberOfVanSpots, availableSpots.getVanSpots());
    }

    @Test
    public void eachVehicleShouldParkInTheirSameType() throws NoSpotsAvailable, VehicleNotMapped, VehicleAlreadyParkedException {
        Vehicle motorcycle = new Vehicle();
        motorcycle.setVehicleType(VehicleType.MOTORCYCLE);
        motorcycle.setPlate("plate1");

        Vehicle car = new Vehicle();
        car.setVehicleType(VehicleType.CAR);
        car.setPlate("plate2");

        Vehicle van = new Vehicle();
        van.setVehicleType(VehicleType.VAN);
        van.setPlate("plate3");

        ParkingSpot motorcycleSpot = this.parkingService.park(motorcycle);
        ParkingSpot carSpot = this.parkingService.park(car);
        ParkingSpot vanSpot = this.parkingService.park(van);

        assertNotNull(motorcycleSpot);
        assertNotNull(carSpot);
        assertNotNull(vanSpot);

        assertEquals(VehicleType.MOTORCYCLE, motorcycleSpot.getSpotType());
        assertEquals(VehicleType.CAR, carSpot.getSpotType());
        assertEquals(VehicleType.VAN, vanSpot.getSpotType());
    }

    @Test
    public void thirdMotorcycleShouldParkInCarSpot() throws NoSpotsAvailable, VehicleNotMapped, VehicleAlreadyParkedException {
        Vehicle motorcycle1 = new Vehicle();
        motorcycle1.setVehicleType(VehicleType.MOTORCYCLE);
        motorcycle1.setPlate("plate1");

        Vehicle motorcycle2 = new Vehicle();
        motorcycle2.setVehicleType(VehicleType.MOTORCYCLE);
        motorcycle2.setPlate("plate2");

        Vehicle motorcycle3 = new Vehicle();
        motorcycle3.setVehicleType(VehicleType.MOTORCYCLE);
        motorcycle3.setPlate("plate3");

        ParkingSpot motorcycleSpot1 = this.parkingService.park(motorcycle1);
        ParkingSpot motorcycleSpot2 = this.parkingService.park(motorcycle2);
        ParkingSpot motorcycleSpot3 = this.parkingService.park(motorcycle3);

        assertNotNull(motorcycleSpot1);
        assertNotNull(motorcycleSpot2);
        assertNotNull(motorcycleSpot3);

        assertEquals(VehicleType.MOTORCYCLE, motorcycleSpot1.getSpotType());
        assertEquals(VehicleType.MOTORCYCLE, motorcycleSpot2.getSpotType());
        assertEquals(VehicleType.CAR, motorcycleSpot3.getSpotType());
    }

    @Test
    public void secondVanShouldParkInCarSpot() throws NoSpotsAvailable, VehicleNotMapped, VehicleAlreadyParkedException {
        Vehicle van1 = new Vehicle();
        van1.setVehicleType(VehicleType.VAN);
        van1.setPlate("plate1");

        Vehicle van2 = new Vehicle();
        van2.setVehicleType(VehicleType.VAN);
        van2.setPlate("plate2");

        ParkingSpot vanSpot1 = this.parkingService.park(van1);
        ParkingSpot vanSpot2 = this.parkingService.park(van2);

        assertNotNull(vanSpot1);
        assertNotNull(vanSpot2);

        assertEquals(VehicleType.VAN, vanSpot1.getSpotType());
        assertEquals(VehicleType.CAR, vanSpot2.getSpotType());
    }

    @Test
    public void vehicleAlreadyParkedException() {
        assertThrows(VehicleAlreadyParkedException.class, () -> {
            Vehicle motorcycle = new Vehicle();
            motorcycle.setVehicleType(VehicleType.MOTORCYCLE);
            motorcycle.setPlate("plate1");

            this.parkingService.park(motorcycle);
            this.parkingService.park(motorcycle);
        });
    }
}
