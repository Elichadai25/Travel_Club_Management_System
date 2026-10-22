package dao;

import java.util.List;

public interface IGenericDao<T> {
    T save(T entity);
    T update(T entity);
    boolean delete(int id);
    T findById(int id);
    List<T> findAll();
}
