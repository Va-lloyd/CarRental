package com.valloyd.carrental.renting;

import com.valloyd.carrental.car.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentingDao {
    List<Renting> getRentings();

    Optional<Renting> getRentingById(String rentingId);

    void saveFile(List<Renting> rentings);

    void addRenting(Renting renting);

    void updateRenting(Renting renting, Renting renting1);

    void deleteRenting(UUID rentingId);

    boolean existsRentingWithId(String id);

    boolean existsRentingWithCar(Car car);
}
