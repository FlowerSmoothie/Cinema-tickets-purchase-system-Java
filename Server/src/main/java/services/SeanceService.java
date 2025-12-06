package services;

import dao.SeanceDAO;
import entities.Seance;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;

public class SeanceService extends Service
{

    public SeanceService() { dao = new SeanceDAO(); }

    @Override
    public Seance findEntity(int id) {
        return (Seance)dao.findById(id);
    }

    @Override
    public List<Seance> findAllEntities() {
        return dao.findAll();
    }

    public List<Seance> findAllSeancesAvailable(LocalDateTime userDate)
    {
        List<Seance> seances = getAllSeances();
        List<Seance> afisha = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime seanceDt;

        for(Seance seance : seances)
        {
            seanceDt = LocalDateTime.parse((seance.getDate() + " " + seance.getTime()), formatter);
            if(userDate.compareTo(seanceDt) < 0) afisha.add(seance);
        }
        return afisha;
    }

    public List<Seance> getSeancesByIDs(List<Integer> ints) { return SeanceDAO.getSeancesByIDs(ints); }

    public List<Seance> getAllSeances() { return SeanceDAO.getAllSeances(); }

    public void addNewSeance(Seance s) { SeanceDAO.addSeance(s); }

    public Seance getSeanceByID(int id) { return SeanceDAO.getByID(id); }

}
