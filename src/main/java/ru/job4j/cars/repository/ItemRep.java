package ru.job4j.cars.repository;

import ru.job4j.cars.model.Item;

import java.util.Calendar;
import java.util.Collection;

public class ItemRep {

    public Collection<Item> findAllPost() {
        return new HbmStore().tx(
                session -> session.createQuery(
                        "select st from Item st "
                                + "join fetch st.user "
                                + "join fetch st.brand "
                                + "join fetch st.model "
                                + "join fetch st.body "
                                + "join fetch st.color "
                                + "join fetch st.photo ", Item.class).list()
        );
    }

    public Collection<Item> findItemPerDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return new HbmStore().tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "where st.created between :stDate and current_timestamp", Item.class
                ).setParameter("stDate", c.getTime()).list()
        );
    }

    public Collection<Item> findWithPhoto() {
        return new HbmStore().tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "where st.photo is not null", Item.class
                ).list()
        );
    }

    public Collection<Item> findCar(String name) {
        return new HbmStore().tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "join fetch st.brand a "
                                + "where a.name = :sId", Item.class
                ).setParameter("sId", name).list()
        );
    }
}
