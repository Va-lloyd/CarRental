package com.valloyd.carrental.renting;

import com.valloyd.carrental.car.Car;
import com.valloyd.carrental.car.CarService;
import com.valloyd.carrental.user.UserService;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository("RentingFile")
public final class RentingFileDas implements RentingDao {
    private final CarService carService;
    private final UserService userService;
    private final File rentingFile = new File(
            Objects.requireNonNull(getClass().getClassLoader().getResource("rentings.csv")).getPath());

    public RentingFileDas(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @Override
    public List<Renting> getRentings() {
        try (
                Scanner scanner = new Scanner(rentingFile)
        ) {
            String line;
            String[] lines;
            List<Renting> rentings = new ArrayList<>();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lines = line.split(", ");

                rentings.add(new Renting(UUID.fromString(lines[0])
                        , LocalDateTime.parse(lines[1])
                        , Integer.parseInt(lines[2])
                        , carService.getCarByRegistrationNumber(lines[3])
                        , userService.getUserById(lines[5])));
            }
            return rentings;
        } catch (FileNotFoundException e) {
            System.out.println("🚫 Must be valid file...");
        }
        return null;
    }

    @Override
    public Optional<Renting> getRentingById(String rentingId) {
        return getRentings().stream()
                .filter(renting -> renting.getRentingId().equals(UUID.fromString(rentingId)))
                .findFirst();
    }

    @Override
    public void saveFile(List<Renting> rentings) {
        try (
                FileWriter fileWriter = new FileWriter(rentingFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {

            for (int i = 0; i < rentings.size(); i++) {
                bufferedWriter.write(rentings.get(i).getRentingId() + ", ");
                bufferedWriter.write(rentings.get(i).getRentingDate().toString() + ", ");
                bufferedWriter.write(rentings.get(i).getDaysRented() + ", ");
                bufferedWriter.write(rentings.get(i).getCar().getRegistrationNumber() + ", ");
                bufferedWriter.write(rentings.get(i).getCar().getDailyRentalPrice().toString() + ", ");
                bufferedWriter.write(rentings.get(i).getUser().getUserId().toString() + ", ");
                bufferedWriter.write(rentings.get(i).getUser().getName() + ", ");
                bufferedWriter.write(rentings.get(i).getUser().getEmail() + ", ");
                bufferedWriter.write(rentings.get(i).getUser().getPhoneNumber());
                if (i < rentings.size() - 1) {
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRenting(Renting renting) {
        if (getRentings() != null) {
            List<Renting> rentings = getRentings();
            rentings.add(renting);

            saveFile(rentings);
        }
    }

    @Override
    public void updateRenting(Renting renting, Renting renting1) {
        if (getRentings() != null) {
            List<Renting> rentings = getRentings();
            rentings.remove(renting1);
            rentings.add(renting);

            saveFile(rentings);
            System.out.println("Renting with data: " + renting.toString() + " updated.");
        }
    }

    @Override
    public void deleteRenting(UUID rentingId) {
        if (getRentings() != null) {
            List<Renting> rentings = getRentings();

            rentings.stream()
                    .filter(renting -> renting.getRentingId().equals(rentingId))
                    .findFirst()
                    .ifPresent(rentings::remove);

            saveFile(rentings);
        }
    }

    @Override
    public boolean existsRentingWithId(String id) {
        if (getRentings() != null) {
            return getRentings().stream()
                    .anyMatch(renting -> renting.getRentingId().toString().equals(id));
        }
        return false;
    }

    @Override
    public boolean existsRentingWithCar(Car car) {
        if (getRentings() != null) {
            return getRentings().stream()
                    .anyMatch(renting -> renting.getCar().equals(car));
        }
        return false;
    }
}
