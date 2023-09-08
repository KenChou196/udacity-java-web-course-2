package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient maps;
    private final PriceClient price;
    public CarService(CarRepository repository, MapsClient maps, PriceClient price) {
        this.repository = repository;
        this.maps = maps;
        this.price = price;
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

        Optional<Car> car = Optional.ofNullable(repository.findById(id)
                .orElseThrow(CarNotFoundException::new));

        String priceFromApi = price.getPrice(id);
        car.get().setPrice(priceFromApi);
        Location locationFromApi = maps.getAddress(car.get().getLocation());
        if (locationFromApi != null) {
            car.get().setLocation(locationFromApi);
        }
        return car.get();
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        Long carId = car.getId();
        System.out.println(carId);
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setCondition(car.getCondition());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Car> carSearch = Optional.ofNullable(repository.findById(id).orElseThrow(CarNotFoundException::new));
        repository.delete(carSearch.get());

    }
}
