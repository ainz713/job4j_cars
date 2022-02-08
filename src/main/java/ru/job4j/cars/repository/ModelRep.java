package ru.job4j.cars.repository;

import ru.job4j.cars.model.Model;
import java.util.Collection;

public class ModelRep {

    public Collection<Model> findAllModels(Integer id) {
        return new HbmStore().tx(
                session -> session.createQuery(
                        "select distinct st from Model st "
                                + "join fetch st.brand a "
                                + "join fetch st.bodies b "
                                + "where a.id = :sId", Model.class).setParameter("sId", id).list()
        );
    }
}
