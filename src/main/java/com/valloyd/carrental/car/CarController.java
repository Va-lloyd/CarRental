package com.valloyd.carrental.car;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
public final class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCars() {
        return carService.getCars();
    }

    @GetMapping("/findRegistrationNumber/{registrationNumber}")
    public Car getCarByRegistrationNumber(
            @PathVariable("registrationNumber") String registrationNumber
    ) {
        return carService.getCarByRegistrationNumber(registrationNumber);
    }

    @PostMapping("/addCar")
    public void addCar(
            @RequestBody CarDataRequest request
    ) {
        carService.addCar(request);
    }

    @PutMapping("/updateCar/{registrationNumber}")
    public void updateCar(
            @PathVariable("registrationNumber") String registrationNumber,
            @RequestBody CarDataRequest request
    ) {
        carService.updateCar(registrationNumber, request);
    }

    @DeleteMapping("/deleteCar/{registrationNumber}")
    public void deleteCar(
            @PathVariable("registrationNumber") String registrationNumber
    ) {
        carService.deleteCar(registrationNumber);
    }
}
