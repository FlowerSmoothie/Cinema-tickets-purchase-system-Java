package services;

import dao.CinemaDAO;
import dao.HallDAO;
import entities.Cinema;
import entities.Hall;

import java.util.List;

public class HallService extends Service
{

    public HallService() { dao = new HallDAO(); }

    @Override
    public Hall findEntity(int id) {
        return (Hall)dao.findById(id);
    }

    @Override
    public List<Hall> findAllEntities() {
        return dao.findAll();
    }

    public List<Hall> findAllHallEntities() { return HallDAO.findAllHalls(); }

    public int getHallCountFromCinema(int cinemaId) { return HallDAO.getHallCountFromCinema(cinemaId); }

    public List<Hall> getHallsByIDs(List<Integer> ids) { return HallDAO.getHallsByIDs(ids);  }

    public Hall getHallByID(int id) { return HallDAO.getHallByID(id); }

    public int addEntityID(Hall hall) { return HallDAO.addEntityID(hall); }

    public List<Hall> getHallsByCinemaID(int cinemaID) { return HallDAO.getByCinemaID(cinemaID); }

    public List<Hall> getShowableHallsByCinemaID(int cinemaID) { return HallDAO.getShowableByCinemaID(cinemaID); }

    public void deleteHall(Hall h) { HallDAO.deleteHall(h); }

}
