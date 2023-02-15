package repositories;

import data.interfaces.IDB;
import entities.Flight;
import repositories.interfaces.IFlightRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class FlightRepository implements IFlightRepository {
    private final IDB db;
    public FlightRepository(IDB db){
        this.db = db;
    }
    public List<Flight> getAllFlights(){
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT postcode,fromcity,tocity,departuredate,departuretime,arrivaldate,arrivaltime,price FROM flights";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);
            List<Flight> flights = new LinkedList<>();
            while (rs.next()) {
                Flight event = new Flight(rs.getString("postcode"),
                        rs.getString("fromcity"),
                        rs.getString("tocity"),
                        rs.getString("departuredate"),
                        rs.getString("departuretime"),
                        rs.getString("arrivaldate"),
                        rs.getString("arrivaltime"),
                        rs.getDouble("price")
                );

                flights.add(event);
            }

            return flights;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
