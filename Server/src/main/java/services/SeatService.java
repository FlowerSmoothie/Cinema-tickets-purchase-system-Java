package services;

import dao.SeatDAO;
import entities.Seat;
import entities.Ticket;

import java.util.List;

public class SeatService extends Service
{
    public SeatService() { dao = new SeatDAO(); }

    @Override
    public Seat findEntity(int id) {
        return (Seat)dao.findById(id);
    }

    @Override
    public List<Seat> findAllEntities() {
        return dao.findAll();
    }

    public static List<Seat> getSeatsFromHall(int id) { return SeatDAO.getSeatsFromHall(id); }

    public static void updateSeatsList(List<Seat> seats) { SeatDAO.updateSeatsList(seats); }

    public List<Seat> findSeatcByTickets(List<Ticket> tickets) { return SeatDAO.findSeatcByTickets(tickets); }

    public void addSeats(List<Seat> seats) { SeatDAO.addSeats(seats); }
}
