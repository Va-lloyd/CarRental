package com.valloyd.carrental.car;

import java.math.BigDecimal;
import java.util.Objects;

public final class Car {
    private String registrationNumber;
    private BigDecimal dailyRentalPrice;

    public Car(String registrationNumber, BigDecimal dailyRentalPrice) {
        this.registrationNumber = registrationNumber;
        this.dailyRentalPrice = dailyRentalPrice;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public BigDecimal getDailyRentalPrice() {
        return dailyRentalPrice;
    }

    public void setDailyRentalPrice(BigDecimal dailyRentalPrice) {
        this.dailyRentalPrice = dailyRentalPrice;
    }

    @Override
    public String toString() {
        return "Car{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", dailyRentalPrice=" + dailyRentalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(registrationNumber, car.registrationNumber) && Objects.equals(dailyRentalPrice, car.dailyRentalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, dailyRentalPrice);
    }
}
