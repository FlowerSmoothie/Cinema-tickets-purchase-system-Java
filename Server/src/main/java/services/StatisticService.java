package services;

import dao.StatisticDAO;
import entities.Statistic;

import java.util.List;

public class StatisticService extends Service
{

    public StatisticService() { dao = new StatisticDAO(); }

    @Override
    public Statistic findEntity(int id) {
        return (Statistic)dao.findById(id);
    }

    @Override
    public List<Statistic> findAllEntities() {
        return dao.findAll();
    }

    public Statistic findForSeance(int seanceID) { return StatisticDAO.getForSeance(seanceID); }

}
