package repositories.interfaces;

import entities.Flight;

import java.util.List;

public interface IFlightRepository {
    List<Flight> getAllFlights();
}
