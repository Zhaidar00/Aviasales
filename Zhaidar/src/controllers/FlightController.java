package controllers;

import entities.Flight;
import entities.User;
import repositories.interfaces.IFlightRepository;

import java.util.ArrayList;
import java.util.List;

public class FlightController {
    private final IFlightRepository flightRepository;
    public FlightController(IFlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }
    public String getAllFlights(){
        List<Flight> flights = flightRepository.getAllFlights();
        if (flights.size() == 0)
            return "There is no any events!";
        return flights.toString();
    }
    public Flight FindFlight(String flight_postcode){
        List<Flight> flights = flightRepository.getAllFlights();
        Flight buyTicket = new Flight();
        for (Flight flight : flights) {
            if (flight.getFlightPostCode().equals(flight_postcode)){
                buyTicket = flight;
                break;
            }
        }
        return buyTicket;
    }

}
