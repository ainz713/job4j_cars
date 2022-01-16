package ru.job4j.cars.store;

import ru.job4j.cars.model.*;

import java.util.Collection;
import java.util.List;

public interface Store {

    <T> boolean save(T model);

    <T> boolean update(T model);

    <T> boolean delete(T model);

    <T> List<T> findAll(Class<T> cl);

    <T> T findById(Class<T> cl, Integer id);

    Collection<Model> findAllModels(Integer id);

    Collection<Item> findAllPost();

    Collection<Body> findAllBodies(Integer id);

    Collection<Item> findItemPerDay();

    Collection<Item> findWithPhoto();

    Collection<Item> findCar(String name);

    User findUserByEmail(String email);
}