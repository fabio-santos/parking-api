package com.sealed.parking.config;

import com.sealed.parking.domain.entity.ParkingConfiguration;
import com.sealed.parking.domain.exception.ParkingNotConfiguredException;
import com.sealed.parking.domain.service.ParkingConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartupApplication {

    private ParkingConfigurationService service;

    @EventListener(ApplicationReadyEvent.class)
    public void seed() throws ParkingNotConfiguredException {
        try {
            this.service.getCurrentConfiguration();
        } catch(ParkingNotConfiguredException exception) {
            ParkingConfiguration configuration = new ParkingConfiguration();
            configuration.setMotorcycleSpots(2);
            configuration.setCarSpots(3);
            configuration.setVanSpots(1);

            this.service.update(configuration);
        }
    }

}
