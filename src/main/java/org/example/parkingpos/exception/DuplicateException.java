package org.example.parkingpos.exception;

import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException{
    private final String vehiclePlateNumber;

    public DuplicateException(String vehiclePlateNumber) {
        super("Vehicle with plate number " + vehiclePlateNumber + " already checked in");
        this.vehiclePlateNumber = vehiclePlateNumber;
    }
}
