package com.sealed.parking.controller;

import com.sealed.parking.domain.entity.ParkingConfiguration;
import com.sealed.parking.domain.exception.ParkingNotConfiguredException;
import com.sealed.parking.domain.service.ParkingConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
@AllArgsConstructor
public class ParkingConfigurationController {

    private ParkingConfigurationService service;

    @PutMapping
    public ParkingConfiguration save(@RequestBody ParkingConfiguration configuration) throws ParkingNotConfiguredException {
        return service.update(configuration);
    }

    @GetMapping
    public ParkingConfiguration get() throws ParkingNotConfiguredException {
        return service.getCurrentConfiguration();
    }
}
