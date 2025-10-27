package com.hotelbooking.repository;

import java.util.List;

public interface Repository<T> {
    void save(T entity);
    T findById(int id);
    List<T> findAll();
    void delete(int id);
    void saveAll();
    void loadAll();
}