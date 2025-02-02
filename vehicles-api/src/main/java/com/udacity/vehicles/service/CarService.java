package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {
    private final CarRepository repository;
    private final MapsClient mapsClient;
    private final PriceClient priceClient;

    public CarService(CarRepository repository, MapsClient mapsClient, PriceClient priceClient) {
        this.repository = repository;
        this.mapsClient = mapsClient;
        this.priceClient = priceClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        /**
         *   Finding the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         */
        Car car = repository.findById(id).orElseThrow(CarNotFoundException::new);

        // getting the price based on the id input
        String price = priceClient.getPrice(id);
        car.setPrice(price);

        // getting the location info for the vehicle
        Location location = mapsClient.getAddress(car.getLocation());
        car.setLocation(location);

        return car;
    }

    /**
     * Creates a vehicle
     * @param car A car object
     * @return the new car is stored in the repository
     */
    public Car save(Car car) {
        return repository.save(car);
    }

    /**
     * Updates a vehicle
     * @param car A car object
     * @return the updated car is stored in the repository
     */
    public Car update(Car car) {
        Car storedCar = repository.findById(car.getId()).orElseThrow(CarNotFoundException::new);

        storedCar.setDetails(car.getDetails());
        storedCar.setCondition(car.getCondition());
        storedCar.setLocation(car.getLocation());

        return repository.save(storedCar);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Car car = repository.findById(id).orElseThrow(CarNotFoundException::new);
        repository.delete(car);
    }
}
