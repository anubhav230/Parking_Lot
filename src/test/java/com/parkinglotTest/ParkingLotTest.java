package com.parkinglotTest;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    @Test
    public void givenCarAndUser_WhenPark_ShouldPark() {
        ParkingLot parkingLot = new ParkingLot();
        String car = "car1";
        int result = parkingLot.park(car);
        Assert.assertEquals(1,result);
    }
}
