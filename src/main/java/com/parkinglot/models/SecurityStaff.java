package com.parkinglot.models;

public class SecurityStaff {
    public String security(boolean value) {
        if (value == true)
            return "Redirect Security";
        return "parking lot is not full";
    }
}
