package com.parkinglotTest.exception;

public class ParkingLotException extends Exception {
    public enum ExceptionType {
       PARKING_FULL,ALREADY_PARKED
    }

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
