import controllers.FlightController;
import entities.Flight;
import entities.User;
import controllers.BuyTicketController;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {
    private final FlightController flightController;
    private final BuyTicketController buyTicketController;
    private final Scanner scanner;
    public Application(FlightController flightController, BuyTicketController buyTicketController){
        this.flightController = flightController;
        this.buyTicketController = buyTicketController;
        scanner = new Scanner(System.in);
    }

    public void start(){
        System.out.println();
        System.out.println("You need register first");
        System.out.println("Please enter your name");
        String name = scanner.next();
        System.out.println("Please enter your surname");
        String surname = scanner.next();
        System.out.println("Please enter your balance");
        Double balance = scanner.nextDouble();

        User user = new User(name, surname, balance);
        System.out.println("User was created!");
        while (true){
            System.out.println();
            System.out.println("Welcome to our Aviasales!");
            System.out.println("Select option:");
            System.out.println("1. Show my balance");
            System.out.println("2. List of all flights");
            System.out.println("3. Buy a ticket");
            System.out.println("4. Show my tickets");
            System.out.println("5. Refund the ticket");
            System.out.println("0. Exit");
            try {
                System.out.print("Enter option (1-5): ");
                int option = scanner.nextInt();
                if (option == 1) {
                    showMyBalance(user);
                } else if(option == 2){
                    getAllFlightsMenu();
                } else if(option == 3){
                    buyFlightTicket(user);
                } else if(option == 4){
                    getBoughtTickets();
                } else if(option == 5){
                    refundEvent(user);
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be integer");
                scanner.nextLine(); // to ignore incorrect input
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("___________________________________________");
        }
    }
    public void showMyBalance(User user){
        System.out.println("Your balance is: ");
        System.out.println(user.getBalance());
    }
    public void getAllFlightsMenu(){
        String response = flightController.getAllFlights();
        System.out.println(response);
    }
    public void buyFlightTicket(User user){
        getAllFlightsMenu();
        System.out.println("Choose flight postcode:");
        String flight_postcode = scanner.next();
        Flight ticketToBuy = flightController.FindFlight(flight_postcode);
        if (buyTicketController.buyTicket(ticketToBuy, user))
            System.out.println("Successfully bought flight: " + ticketToBuy.getFromCity() + "-" + ticketToBuy.getToCity());
        else System.out.println("You do not have enough money to buy a ticket " + ticketToBuy.getToCity() + "-" + ticketToBuy.getFromCity() +"!");
    }
    public void getBoughtTickets(){
        List<Flight> boughtFlights = buyTicketController.getBoughtTickets();
        if(boughtFlights.isEmpty()){
            System.out.println("Nothing to show, buy a ticket first.");
        }
        else {
            for (Flight flight : boughtFlights) {
                System.out.println(flight.toString());
            }
        }
    }
    public void refundEvent(User user){
        if(buyTicketController.getBoughtTickets().isEmpty()){
            System.out.println("No registered flight to refund.");
        }
        else {
            getBoughtTickets();
            System.out.println("Choose flight postcode:");
            String flight_postcode = scanner.next();
            Flight flightToRefund = flightController.FindFlight(flight_postcode);
            System.out.println(buyTicketController.refundFlight(flightToRefund, user)?
                    "Successfully refunded flight: " +flightToRefund.getFromCity() + "-" + flightToRefund.getToCity():"Couldn't refund the flight:"+flightToRefund.getFromCity()+"-"+flightToRefund.getToCity());
        }
    }
}
