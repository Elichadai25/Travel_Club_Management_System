package dao;

import model.Trip;
import java.util.List;

public interface ITripDao extends IGenericDao<Trip> {
    List<Trip> findByDestination(String destination);
    List<Trip> findActiveTrips();
}
