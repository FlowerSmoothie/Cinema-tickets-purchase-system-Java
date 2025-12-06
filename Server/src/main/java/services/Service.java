package services;

import dao.DAO;

import java.util.List;

public abstract class Service<T>
{

    protected static DAO dao;

    abstract T findEntity(int id);

    public void addEntity(T entity) { dao.add(entity); }
    public void deleteEntity(T entity) { dao.delete(entity); }

    public void updateEntity(T entity) { dao.update(entity); }

    abstract List<T> findAllEntities();

   

}
