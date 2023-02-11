package com.valloyd.carrental.car;

import com.valloyd.carrental.exception.DuplicateResourceException;
import com.valloyd.carrental.exception.RequestValidationException;
import com.valloyd.carrental.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CarService {
    private final CarDao carDao;

    public CarService(@Qualifier("CarFile") CarDao carDao) {
        this.carDao = carDao;
    }

    public List<Car> getCars() {
        return carDao.getCars();
    }

    public Car getCarByRegistrationNumber(String registrationNumber) {
        return carDao.getCarByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Car with registration number \"%s\" not found.".formatted(registrationNumber)));
    }

    public void addCar(CarDataRequest request) {
        if (carDao.existsCarWithRegistrationNumber(request.registrationNumber())) {
            throw new DuplicateResourceException("Registration number already taken.");
        }

        Car car = new Car(
                request.registrationNumber()
                , request.dailyRentalPrice());

        carDao.addCar(car);
        System.out.println("Car with data: " + car + " added to database.");
    }

    public void updateCar(String registrationNumber, CarDataRequest request) {
        Car car = getCarByRegistrationNumber(registrationNumber);
        boolean changes = false;

        if (request.registrationNumber() != null && !request.registrationNumber().equals(car.getRegistrationNumber())) {
            car.setRegistrationNumber(request.registrationNumber());
            changes = true;
        }

        if (request.dailyRentalPrice() != null && !request.dailyRentalPrice().equals(car.getDailyRentalPrice())) {
            car.setDailyRentalPrice(request.dailyRentalPrice());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found.");
        }
        Car car1 = getCarByRegistrationNumber(registrationNumber);
        carDao.updateCar(car, car1);
    }

    public void deleteCar(String registrationNumber) {
        if (carDao.existsCarWithRegistrationNumber(registrationNumber)) {
            System.out.println("Car with data: " + getCarByRegistrationNumber(registrationNumber).toString() + " deleted from database.");

            carDao.deleteCar(registrationNumber);
        } else {
            throw new ResourceNotFoundException(
                    "Car with registration number: %s not found".formatted(registrationNumber)
            );
        }
    }
}
