package org.example.parkingpos.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private final String vehiclePlateNumber;

    public NotFoundException(String vehiclePlateNumber){
        super("Vehicle with plate number " + vehiclePlateNumber + " not found");
        this.vehiclePlateNumber = vehiclePlateNumber;
    }
}
