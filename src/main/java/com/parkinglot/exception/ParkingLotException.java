package com.parkinglot.exception;

public class ParkingLotException extends Exception {
    public enum ExceptionType {
       PARKING_FULL,ALREADY_PARKED,NO_PLACE_FOR_LARGE_VEHICLE
    }

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
