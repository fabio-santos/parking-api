package com.sealed.parking.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpot {

    public ParkingSpot(int number, VehicleType spotType) {
        this.number = number;
        this.spotType = spotType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int number;

    @Enumerated(EnumType.STRING)
    private VehicleType spotType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;
}
