package com.sealed.parking.services;

import com.sealed.parking.domain.entity.ParkingConfiguration;
import com.sealed.parking.domain.exception.ParkingNotConfiguredException;
import com.sealed.parking.domain.service.ParkingConfigurationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ParkingConfigurationServiceTest {

    @Autowired
    private ParkingConfigurationService service;

    @Test
    public void emptyConfigurationShouldReturnParkingNotConfiguredException() {
        assertThrows(ParkingNotConfiguredException.class, () -> service.getCurrentConfiguration());
    }

    @Test
    public void setConfigurationShouldReturnSameParameters() throws ParkingNotConfiguredException {
        ParkingConfiguration configuration = new ParkingConfiguration();

        int numberOfMotorcycleSpots = 2;
        int numberOfCarSpots = 3;
        int numberOfVanSpots = 4;

        configuration.setMotorcycleSpots(numberOfMotorcycleSpots);
        configuration.setVanSpots(numberOfVanSpots);
        configuration.setCarSpots(numberOfCarSpots);

        ParkingConfiguration savedConfiguration = service.update(configuration);

        assertNotNull(savedConfiguration);
        assertEquals(numberOfMotorcycleSpots, savedConfiguration.getMotorcycleSpots());
        assertEquals(numberOfCarSpots, savedConfiguration.getCarSpots());
        assertEquals(numberOfVanSpots, savedConfiguration.getVanSpots());
    }
}
