package com.valloyd.carrental.renting;

import com.valloyd.carrental.car.Car;
import com.valloyd.carrental.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Renting {
    private UUID rentingId;
    private LocalDateTime rentingDate;
    private int daysRented;
    private Car car;
    private User user;

    public Renting(int daysRented, Car car, User user) {
        this.daysRented = daysRented;
        this.car = car;
        this.user = user;
    }

    public Renting(UUID rentingId, LocalDateTime rentingDate, int daysRented, Car car, User user) {
        this.rentingId = rentingId;
        this.rentingDate = rentingDate;
        this.daysRented = daysRented;
        this.car = car;
        this.user = user;
    }

    public UUID getRentingId() {
        return rentingId;
    }

    public LocalDateTime getRentingDate() {
        return rentingDate;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Renting{" +
                "rentingId=" + rentingId +
                ", rentingDate=" + rentingDate +
                ", daysRented=" + daysRented +
                ", car=" + car +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Renting renting = (Renting) o;
        return daysRented == renting.daysRented && Objects.equals(rentingId, renting.rentingId) && Objects.equals(rentingDate, renting.rentingDate) && Objects.equals(car, renting.car) && Objects.equals(user, renting.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentingId, rentingDate, daysRented, car, user);
    }
}
