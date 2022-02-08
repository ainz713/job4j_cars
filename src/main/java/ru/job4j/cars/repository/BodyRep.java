package ru.job4j.cars.repository;

import ru.job4j.cars.model.Body;
import java.util.Collection;

public class BodyRep {
    public Collection<Body> findAllBodies(Integer id) {
        return new HbmStore().tx(
                session -> session.createQuery(
                        "select distinct st from Body st ", Body.class).list()
        );
    }
}
