package com.valloyd.carrental.renting;

import com.valloyd.carrental.car.CarService;
import com.valloyd.carrental.exception.DuplicateResourceException;
import com.valloyd.carrental.exception.RequestValidationException;
import com.valloyd.carrental.exception.ResourceNotFoundException;
import com.valloyd.carrental.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public final class RentingService {
    private final RentingDao rentingDao;
    private final CarService carService;
    private final UserService userService;

    public RentingService(
            @Qualifier("RentingFile") RentingDao rentingDao
            , CarService carService
            , UserService userService
    ) {
        this.rentingDao = rentingDao;
        this.carService = carService;
        this.userService = userService;
    }

    public List<Renting> getRentings() {
        return rentingDao.getRentings();
    }

    public Renting getRentingById(String id) {
        return rentingDao.getRentingById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Renting with ID \"%s\" not found.".formatted(id)));
    }

    public void addRenting(RentingDataRequest request) {
        if (rentingDao.existsRentingWithCar(carService.getCarByRegistrationNumber(request.registrationNumber()))) {
            throw new DuplicateResourceException("Car already being rented.");
        }

        Renting renting = new Renting(
                UUID.randomUUID()
                , LocalDateTime.now()
                , request.daysRented()
                , carService.getCarByRegistrationNumber(request.registrationNumber())
                , userService.getUserById(request.userId()));

        rentingDao.addRenting(renting);
        System.out.println("Renting with data: " + renting + " added to database.");
    }

    public void updateRenting(String rentingId, RentingDataRequest request) {
        Renting renting = getRentingById(rentingId);
        boolean changes = false;

        if (request.daysRented() > 0 && request.daysRented() != renting.getDaysRented()) {
            renting.setDaysRented(request.daysRented());
            changes = true;
        }

        if (request.registrationNumber() != null && !request.registrationNumber().equals(renting.getCar().getRegistrationNumber())) {
            renting.setCar(carService.getCarByRegistrationNumber(request.registrationNumber()));
            changes = true;
        }

        if (request.userId() != null && !request.userId().equals(renting.getUser().getUserId().toString())) {
            renting.setUser(userService.getUserById(request.userId()));
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found.");
        }
        Renting renting1 = getRentingById(rentingId);
        rentingDao.updateRenting(renting, renting1);
    }

    public void deleteRenting(String rentingId) {
        if (rentingDao.existsRentingWithId(rentingId)) {
            System.out.println("Renting with data: " + getRentingById(rentingId).toString() + " deleted from database.");

            rentingDao.deleteRenting(UUID.fromString(rentingId));
        } else {
            throw new ResourceNotFoundException(
                    "Renting with ID: %s not found".formatted(rentingId)
            );
        }
    }
}
