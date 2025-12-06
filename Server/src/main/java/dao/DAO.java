package dao;

import java.util.List;

public interface DAO<T>
{

    void add(T obj);
    void update(T obj);
    void delete(T obj);
    T findById(int id);
    List<T> findAll();
}
