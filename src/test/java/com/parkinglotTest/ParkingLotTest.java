package com.parkinglotTest;

import com.parkinglotTest.exception.ParkingLotException;
import com.parkinglotTest.Observers.ParkingLotOwner;
import com.parkinglotTest.Observers.SecurityStaff;
import com.parkinglotTest.services.ParkingLot;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalTime;

public class ParkingLotTest {
    public LocalTime arrivalTime = null;
    ParkingLot parkingLot = new ParkingLot(3);

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() throws ParkingLotException {
        int slot = parkingLot.getSlotNumber();
        parkingLot.park(slot, "vehicle");
        boolean result = parkingLot.isVehicleParked("vehicle");
        Assert.assertTrue(result);
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() throws ParkingLotException {
        int slot = parkingLot.getSlotNumber();
        parkingLot.park(slot, "vehicle");
        boolean result2 = parkingLot.unPark(slot);
        Assert.assertTrue(result2);
    }

    @Test
    public void givenCarToUnPark_WhenNotPresent_ShouldReturnFalse() throws ParkingLotException {
        int slot = parkingLot.getSlotNumber();
        parkingLot.park(slot, "vehicle");
        boolean result2 = parkingLot.unPark(4);
        Assert.assertFalse(result2);
    }

    @Test
    public void givenCarToPark_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLot.park(1, "vehicle");
            parkingLot.park(2, "vehicle");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("Parking Lot is full", e.getLocalizedMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLot.register(securityStaff);
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            boolean result = securityStaff.isCapacityFull();
            Assert.assertTrue(result);
        }
    }

    @Test
    public void givenParkingLot_WhenHasSpace_ShouldNotRedirectSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLot.register(securityStaff);
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            parkingLot.unPark(3);
            boolean fullCapacity = securityStaff.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }
    }


    @Test
    public void givenParkingLot_WhenHaveSpace_ShouldPutCloseSign() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(fullCapacity);
        }
    }

    @Test
    public void givenParkingLot_WhenNoSpace_ShouldPutOpenSign() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.register(parkingLotOwner);
        try {
            parkingLot.park(1, "vehicle1");
            parkingLot.park(2, "vehicle2");
            parkingLot.park(3, "vehicle3");
            parkingLot.park(4, "vehicle4");
        } catch (ParkingLotException e) {
            parkingLot.unPark(3);
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }

    }

    @Test
    public void givenVehicle_ShouldParkOnEmptySlot() throws ParkingLotException {
        int result = parkingLot.getSlotNumber();
        parkingLot.park(result, "vehicle");
        boolean result3 = parkingLot.isVehicleParked("vehicle");
        Assert.assertTrue(result3);
    }

    @Test
    public void givenSlot_WhenDriverWantToUnPark_ShouldUnParkVehicle() throws ParkingLotException {
        int result = parkingLot.getSlotNumber();
        parkingLot.park(result, "vehicle1");
        int result2 = parkingLot.getVehicleValue("vehicle1");
        boolean unPark = parkingLot.unPark(result2);
        Assert.assertTrue(unPark);
    }

    @Test
    public void givenVehicle_WhenParked_shouldReturnParkedTime() throws ParkingLotException {
        int result = parkingLot.getSlotNumber();
        parkingLot.park(result, "vehicle1");
        String result2 = String.valueOf(arrivalTime = LocalTime.now());
        Assert.assertEquals(result2, parkingLot.arrival);
    }
}
