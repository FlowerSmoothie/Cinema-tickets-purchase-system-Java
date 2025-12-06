package services;

import dao.CinemaDAO;
import dao.DAO;
import entities.Cinema;

import java.util.List;

public class CinemaService extends Service
{

    public CinemaService() { dao = new CinemaDAO(); }

    @Override
    public Cinema findEntity(int id) {
        return (Cinema)dao.findById(id);
    }

    public Cinema findCinemaEntity(int id) { return CinemaDAO.findCinema(id); }

    @Override
    public List<Cinema> findAllEntities() {
        return dao.findAll();
    }

    public List<Cinema> findAllCinemaEntities() {
        return CinemaDAO.findAllCinemaEntities();
    }

    public boolean isCinemaUnique(Cinema cinema)
    {
        String name = cinema.getName();
        List<Cinema> cinemas = findAllCinemaEntities();
        for (Cinema c: cinemas)
            if(c.getName().equals(name))
                return false;
        return true;
    }

    public void deleteCinema(Cinema c) { CinemaDAO.deleteCinema(c); }

}
