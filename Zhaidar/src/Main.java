import controllers.BuyTicketController;
import controllers.FlightController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.FlightRepository;
import repositories.interfaces.IFlightRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB();
        IFlightRepository flightRepository = new FlightRepository(db);
        FlightController flightController = new FlightController(flightRepository);
        BuyTicketController buyTicketController = new BuyTicketController();
        Application app = new Application(flightController, buyTicketController);
        app.start();
    }
}