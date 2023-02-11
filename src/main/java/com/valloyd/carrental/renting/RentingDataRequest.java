package com.valloyd.carrental.renting;

public record RentingDataRequest(
        int daysRented,
        String registrationNumber,
        String userId
) {
}
