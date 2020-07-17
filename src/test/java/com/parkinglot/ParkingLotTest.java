package com.parkinglot;

import com.parkinglot.models.ParkingLotOwner;
import com.parkinglot.models.SecurityStaff;
import com.parkinglot.services.ParkingLot;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParkingLotTest {

    ParkingLot parkingLot = new ParkingLot();

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() {
        String car = "car1";
        int result = parkingLot.park(car);
        Assert.assertEquals(1,result);
    }

    @Test
    public void givenCar_WhenUnParked_ShouldReturnTrueResult() {
        String carId = "car1";
        parkingLot.park(carId);
        boolean result2 = parkingLot.unPark(carId);
        Assert.assertTrue(result2);
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldReturnTrueResult() {
        String car1 = "car1";
        String car2 = "car2";
        String car3 = "car3";
        parkingLot.park(car1, car2, car3);
        boolean result = parkingLot.isFull();
        Assert.assertTrue(result);
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurity() {
        SecurityStaff securityStaff = new SecurityStaff();
        String car1 = "car1";
        String car2 = "car2";
        String car3 = "car3";
        parkingLot.park(car1, car2, car3);
        boolean result = parkingLot.isFull();
        String result2 = securityStaff.security(result);
        Assert.assertEquals("Redirect Security", result2);
    }

    @Test
    public void givenParkingLot_WhenHaveSpace_ShouldPutOpenSign() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        SecurityStaff securityStaff = new SecurityStaff();
        String car1 = "car1";
        String car2 = "car2";
        String car3 = "car3";
        parkingLot.park(car1, car2, car3);
        List result = parkingLot.empty();
        String result2 = parkingLotOwner.getUpdate(result);
        Assert.assertEquals("parking lot is open", result2);
    }
}
