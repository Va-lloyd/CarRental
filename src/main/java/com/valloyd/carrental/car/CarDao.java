package com.valloyd.carrental.car;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    List<Car> getCars();

    Optional<Car> getCarByRegistrationNumber(String registrationNumber);

    void saveFile(List<Car> cars);

    void addCar(Car car);

    void updateCar(Car car, Car car1);

    void deleteCar(String registrationNumber);

    boolean existsCarWithRegistrationNumber(String registrationNumber);
}
