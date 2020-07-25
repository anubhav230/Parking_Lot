package com.parkinglot.test;

import com.parkinglot.Observers.ParkingLotOwner;
import com.parkinglot.Observers.SecurityStaff;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.services.ParkingLot;
import com.parkinglot.services.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class ParkingLotTest {

    ParkingLotSystem parkingLots = new ParkingLotSystem(3, 1);

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() {
        try {
            parkingLots.park("vehicle1");
            boolean result = parkingLots.isVehicleParked("vehicle1");
            Assert.assertTrue(result);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() {
        try {
            parkingLots.park("vehicle");
            boolean result2 = parkingLots.unPark("vehicle");
            Assert.assertTrue(result2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenCarToUnPark_WhenNotPresent_ShouldReturnFalse() {
        try {
            parkingLots.park("vehicle");
            boolean result2 = parkingLots.unPark("vehicle1");
            Assert.assertFalse(result2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenCarToPark_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLots.park( "vehicle");
            parkingLots.park( "vehicle");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLots.park( "vehicle1");
            parkingLots.park( "vehicle2");
            parkingLots.park( "vehicle3");
            parkingLots.park( "vehicle4");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals("Parking Lot is full", e.getLocalizedMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLots.register(securityStaff);
        try {
            parkingLots.park( "vehicle1");
            parkingLots.park( "vehicle2");
            parkingLots.park( "vehicle3");
            parkingLots.park( "vehicle4");
        } catch (ParkingLotException e) {
            boolean result = securityStaff.isCapacityFull();
            Assert.assertTrue(result);
        }
    }

    @Test
    public void givenParkingLot_WhenHasSpace_ShouldNotRedirectSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLots.register(securityStaff);
        try {
            parkingLots.park( "vehicle1");
            parkingLots.park( "vehicle2");
            parkingLots.park( "vehicle3");
            parkingLots.park( "vehicle4");
        } catch (ParkingLotException e) {
            parkingLots.unPark("vehicle4");
            boolean fullCapacity = securityStaff.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }
    }

    @Test
    public void givenParkingLot_WhenHaveSpace_ShouldPutCloseSign() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLots.register(parkingLotOwner);
        try {
            parkingLots.park( "vehicle1");
            parkingLots.park( "vehicle2");
            parkingLots.park( "vehicle3");
            parkingLots.park( "vehicle4");
        } catch (ParkingLotException e) {
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(fullCapacity);
        }
    }

    @Test
    public void givenParkingLot_WhenNoSpace_ShouldPutOpenSign() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLots.register(parkingLotOwner);
        try {
            parkingLots.park( "vehicle1");
            parkingLots.park( "vehicle2");
            parkingLots.park( "vehicle3");
            parkingLots.park( "vehicle4");
        } catch (ParkingLotException e) {
            parkingLots.unPark("vehicle4");
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }

    }

    @Test
    public void givenVehicle_ShouldParkOnEmptySlot() {
        try {
            parkingLots.park("vehicle");
            boolean result3 = parkingLots.isVehicleParked("vehicle");
            Assert.assertTrue(result3);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

//    @Test
//    public void givenVehicle_WhenParked_shouldReturnParkedTime() {
//        ParkingLot parkingLot = new ParkingLot(2);
//        try {
//            parkingLots.park("vehicle2");
//            LocalTime time = parkingLot.getParkTime(spot);
//            Assert.assertEquals(LocalTime.now().withNano(0), time.withNano(0));
//        } catch (ParkingLotException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    @Test
    public void givenVehicle_WhenParkedAndUnParkedInLot_ShouldReturnVehicleSlotInLot() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park("vehicle1");
            parkingService.park("vehicle2");
            parkingService.park("vehicle3");
            parkingService.park("vehicle4");
            System.out.println(parkingService.parkingLots.get(0).parkingLotMap);
            Assert.assertEquals(parkingService.parkingLots.get(1).parkingLotMap.get(1).vehicle, "vehicle2");
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }


}