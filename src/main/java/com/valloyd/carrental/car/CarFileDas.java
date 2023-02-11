package com.valloyd.carrental.car;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Repository("CarFile")
public final class CarFileDas implements CarDao {
    private final File carFile = new File(
            Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("cars.csv")).getPath());

    @Override
    public List<Car> getCars() {
        try (
                Scanner scanner = new Scanner(carFile)
        ) {
            String line;
            String[] lines;
            List<Car> cars = new ArrayList<>();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lines = line.split(", ");

                cars.add(new Car(lines[0], BigDecimal.valueOf(Double.parseDouble(lines[1]))));
            }
            return cars;
        } catch (FileNotFoundException e) {
            System.out.println("🚫 Must be valid file...");
        }
        return null;
    }

    @Override
    public Optional<Car> getCarByRegistrationNumber(String registrationNumber) {
        return getCars().stream()
                .filter(car -> car.getRegistrationNumber().equals(registrationNumber))
                .findFirst();
    }

    @Override
    public void saveFile(List<Car> cars) {
        try (
                FileWriter fileWriter = new FileWriter(carFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {

            for (int i = 0; i < cars.size(); i++) {
                bufferedWriter.write(cars.get(i).getRegistrationNumber() + ", ");
                bufferedWriter.write(cars.get(i).getDailyRentalPrice().toString());
                if (i < cars.size() - 1) {
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCar(Car car) {
        if (getCars() != null) {
            List<Car> cars = getCars();
            cars.add(car);

            saveFile(cars);
        }
    }

    @Override
    public void updateCar(Car car, Car car1) {
        if (getCars() != null) {
            List<Car> cars = getCars();
            cars.remove(car1);
            cars.add(car);

            saveFile(cars);
            System.out.println("Car with data: " + car.toString() + " updated.");
        }
    }

    @Override
    public void deleteCar(String registrationNumber) {
        if (getCars() != null) {
            List<Car> cars = getCars();

            cars.stream()
                    .filter(car -> car.getRegistrationNumber().equals(registrationNumber))
                    .findFirst()
                    .ifPresent(cars::remove);

            saveFile(cars);
        }
    }

    @Override
    public boolean existsCarWithRegistrationNumber(String registrationNumber) {
        if (getCars() != null) {
            return getCars().stream()
                    .anyMatch(car -> car.getRegistrationNumber().equals(registrationNumber));
        }
        return false;
    }
}
