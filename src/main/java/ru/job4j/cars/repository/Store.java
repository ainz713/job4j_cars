package ru.job4j.cars.repository;

import java.util.List;

public interface Store {

    <T> boolean save(T model);

    <T> boolean update(T model);

    <T> boolean delete(T model);

    <T> List<T> findAll(Class<T> cl);

    <T> T findById(Class<T> cl, Integer id);
}