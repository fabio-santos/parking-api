package com.sealed.parking.controller;

import com.sealed.parking.domain.dto.ParkingDTO;
import com.sealed.parking.domain.service.ParkingSpotService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/state")
@AllArgsConstructor
public class ParkingStateController {

    private ParkingSpotService service;

    @GetMapping("remainingSpots")
    public ParkingDTO getRemainingSpots() {
        return service.getAvailableSpots();
    }

    @GetMapping("full")
    public boolean isParkingLotFull() {
        return service.isParkingLotFull();
    }

    @GetMapping("takenSpots")
    public ParkingDTO getTakenSpots() {
        return service.getTakenSpots();
    }
}
