package com.sealed.parking.domain.service;

import com.sealed.parking.domain.entity.ParkingConfiguration;
import com.sealed.parking.domain.entity.ParkingSpot;
import com.sealed.parking.domain.entity.VehicleType;
import com.sealed.parking.domain.exception.ParkingNotConfiguredException;
import com.sealed.parking.domain.repository.ParkingConfigurationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ParkingConfigurationService {

    private ParkingConfigurationRepository repository;
    private ParkingSpotService parkingSpotService;

    public ParkingConfiguration getCurrentConfiguration() throws ParkingNotConfiguredException {
        List<ParkingConfiguration> configurationList = repository.findAll();

        if(configurationList.isEmpty()) {
            throw new ParkingNotConfiguredException();
        }

        return configurationList.get(0);
    }

    public ParkingConfiguration update(ParkingConfiguration configuration) throws ParkingNotConfiguredException {
        this.reset();

        repository.save(configuration);

        int counter = 1;
        List<ParkingSpot> parkingSpotList = new ArrayList<>();

        for(int i=0; i<configuration.getMotorcycleSpots(); i++) {
            parkingSpotList.add(new ParkingSpot(counter++, VehicleType.MOTORCYCLE));
        }

        for(int i=0; i<configuration.getCarSpots(); i++) {
            parkingSpotList.add(new ParkingSpot(counter++, VehicleType.CAR));
        }

        for(int i=0; i<configuration.getVanSpots(); i++) {
            parkingSpotList.add(new ParkingSpot(counter++, VehicleType.VAN));
        }

        parkingSpotService.saveAll(parkingSpotList);

        return this.getCurrentConfiguration();
    }

    public void reset() {
        List<ParkingConfiguration> configurationList = repository.findAll();

        if(!configurationList.isEmpty()) {
            repository.deleteAll();
            parkingSpotService.reset();
        }
    }
}
