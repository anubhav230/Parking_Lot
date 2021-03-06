package com.parkinglot.test;

import com.parkinglot.Observers.ParkingLotOwner;
import com.parkinglot.Observers.SecurityStaff;
import com.parkinglot.enums.CarCompany;
import com.parkinglot.enums.VehicleColor;
import com.parkinglot.enums.Vehicle;
import com.parkinglot.enums.DriverType;
import com.parkinglot.exception.ParkingLotException;

import com.parkinglot.models.VehicleDetails;
import com.parkinglot.services.ParkingLotSystem;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParkingLotTest {

    ParkingLotSystem parkingLots = new ParkingLotSystem(2, 2);

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() {
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            boolean result = parkingLots.isVehicleParked("vehicle1");
            Assert.assertTrue(result);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() {
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            boolean result2 = parkingLots.unPark("vehicle1");
            Assert.assertTrue(result2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenCarToUnPark_WhenNotPresent_ShouldReturnFalse() {
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            boolean result2 = parkingLots.unPark("vehicle");
            Assert.assertFalse(result2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenCarToPark_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle3"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle4"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle5"), "AAAAAA");
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is full", e.getLocalizedMessage());
        }
    }

    @Test
    public void givenParkingLotCapacity_WhenFull_ShouldInformSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLots.register(securityStaff);
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle3"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle4"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle5"), "AAAAAA");
        } catch (ParkingLotException e) {
            boolean result = securityStaff.isCapacityFull();
            Assert.assertTrue(result);
        }
    }

    @Test
    public void givenParkingLotCapacity_WhenHasSpace_ShouldReturnFalse() {
        SecurityStaff securityStaff = new SecurityStaff();
        parkingLots.register(securityStaff);
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle3"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle4"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle5"), "AAAAAA");
        } catch (ParkingLotException e) {
            parkingLots.unPark("vehicle3");
            boolean fullCapacity = securityStaff.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }
    }

    @Test
    public void givenParkingLotCapacity_WhenFull_ShouldReturnTrue() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLots.register(parkingLotOwner);
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle3"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle4"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle5"), "AAAAAA");
        } catch (ParkingLotException e) {
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(fullCapacity);
        }
    }

    @Test
    public void givenParkingLotCapacity_WhenAgainHaveSpace_ShouldReturnFalse() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLots.register(parkingLotOwner);
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle3"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle4"), "AAAAAA");
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle5"), "AAAAAA");
        } catch (ParkingLotException e) {
            parkingLots.unPark("vehicle3");
            boolean fullCapacity = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(fullCapacity);
        }
    }

    @Test
    public void givenVehicle_ShouldParkOnEmptySlot() {
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            boolean result3 = parkingLots.isVehicleParked("vehicle1");
            Assert.assertTrue(result3);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParked_shouldReturnParkedTime() {
        try {
            parkingLots.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            LocalTime time = parkingLots.getParkTime("vehicle1");
            Assert.assertEquals(LocalTime.now().withNano(0), time.withNano(0));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkedEvenly_ShouldReturnVehicleSlot() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle3"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle4"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle5"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle6"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle7"), "AAAAAA");
            parkingService.unPark("vehicle5");
            Assert.assertEquals("3,1", parkingService.vehicleLocation("vehicle3"));
            Assert.assertNull(parkingService.vehicleLocation("vehicle5"));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDriver_WhenDriverIsHandicap_ShouldReturnVehicleSlot() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle3"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle4"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.HANDICAP, "vehicle5"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.HANDICAP, "vehicle6"), "AAAAAA");
            Assert.assertEquals("1,3", parkingService.vehicleLocation("vehicle5"));
            Assert.assertEquals("2,2", parkingService.vehicleLocation("vehicle6"));
            Assert.assertEquals("3,1", parkingService.vehicleLocation("vehicle3"));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenLargeVehicleComes_ShouldParkInGivenSlot() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle1"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle2"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle3"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "vehicle4"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.LARGE,
                    DriverType.NORMAL, "vehicle5"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.LARGE,
                    DriverType.NORMAL, "vehicle6"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.LARGE,
                    DriverType.NORMAL, "vehicle7"), "AAAAAA");
            //Assert.assertEquals("2,2", parkingService.vehicleLocation("vehicle5"));
            Assert.assertEquals("3,2", parkingService.vehicleLocation("vehicle6"));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenWhiteVehicle_WhenHaveBomb_ShouldBeLocated() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, VehicleColor.WHITE, "vehicle1"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, VehicleColor.NO_COLOR, "vehicle2"), "AAAAAA");
            List WhiteVehicleDetails  = parkingService.findWhiteVehiclePosition(VehicleColor.WHITE);
            Assert.assertEquals(Collections.singletonList("L1, S1"), WhiteVehicleDetails);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenToyotaVehicle_WhenHaveBlueColor_ShouldReturnDetails() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.WHITE,
                    CarCompany.TATA, "UP 75 DE 1234"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.BLUE,
                    CarCompany.TOYOTA, "UP 75 DE 1235"), "BBBBBB");
            List carDetails = parkingService.findPositionColorAndCompanyOfCar(VehicleColor
                                        .BLUE, CarCompany.TOYOTA);
            Assert.assertEquals(Arrays.asList("L: 1, S: 2, Number: UP 75 DE 1235, Attendant Name: BBBBBB")
                    , carDetails);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenBMWCars_WhenParked_ShouldReturnNumberOfCars() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.WHITE,
                    CarCompany.MAHINDRA, "UP 75 DE 1234"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.BLUE,
                    CarCompany.BMW, "UP 75 DE 1235"), "BBBBBB");
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.BLUE,
                    CarCompany.BMW, "UP 75 DE 1236"), "BBBBBB");
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.BLUE,
                    CarCompany.TOYOTA, "UP 75 DE 1237"), "BBBBBB");
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.BLUE,
                    CarCompany.TATA, "UP 75 DE 1238"), "BBBBBB");
            int carCount = parkingService.parkedVehicleCount(CarCompany.BMW);
            Assert.assertEquals(2, carCount);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldCheckParkDuration() {
        ParkingLotSystem parkingService = new ParkingLotSystem(3, 3);
        try {
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.WHITE,
                    CarCompany.MAHINDRA, "UP 75 DE 1234"), "AAAAAA");
            List<String> carDetails = parkingService.ParkTimeDuration(30);
            Assert.assertEquals(Arrays.asList("L: 1, S: 1"), carDetails);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenHaveHandicapDriverType_ShouldReturnLocation() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2,2);
        try {
            parkingLotSystem.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.NORMAL, "UP 75 DE 1234"), "AAAAAA");
            parkingLotSystem.park(new VehicleDetails(Vehicle.SMALL,
                    DriverType.HANDICAP, "UP 75 DE 1235"), "AAAAAA");
            List<String> information = parkingLotSystem.locationAndInformationOfDriverType
                                        (DriverType.HANDICAP, 1);
            Assert.assertEquals(Arrays.asList("L: 1, S: 2, UP 75 DE 1235"), information);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParingLot_WhenHaveParkedCars_ShouldReturnAllCars() {
        ParkingLotSystem parkingService = new ParkingLotSystem(2, 2);
        try {
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.WHITE,
                    CarCompany.MAHINDRA, "UP 75 DE 1234"), "AAAAAA");
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.BLUE,
                    CarCompany.BMW, "UP 75 DE 1235"), "BBBBBB");
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.BLUE,
                    CarCompany.BMW, "UP 75 DE 1236"), "BBBBBB");
            parkingService.park(new VehicleDetails(Vehicle.SMALL, VehicleColor.BLUE,
                    CarCompany.TOYOTA, "UP 75 DE 1237"), "BBBBBB");
            int carCount = parkingService.carCount();
            Assert.assertEquals(4, carCount);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
}