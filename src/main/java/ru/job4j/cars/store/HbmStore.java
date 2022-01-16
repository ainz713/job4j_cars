package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class HbmStore implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public <T> boolean save(T model) {
        return tx(session -> {
            session.save(model);
            return true;
        });
    }

    public <T> boolean update(T model) {
        return tx(session -> {
            session.update(model);
            return true;
        });
    }

    public <T> boolean delete(T model) {
        return tx(session -> {
            session.delete(model);
            return true;
        });
    }

    @Override
    public <T> List<T> findAll(Class<T> cl) {
        return tx(
                session -> session.createQuery("from " + cl.getName(), cl)
                .list());
    }

    @Override
    public Collection<Item> findAllPost() {
        return tx(
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

    @Override
    public Collection<Model> findAllModels(Integer id) {
        return tx(
                session -> session.createQuery(
                        "select distinct st from Model st "
                                + "join fetch st.brand a "
                        + "join fetch st.bodies b "
                        + "where a.id = :sId", Model.class).setParameter("sId", id).list()
        );
    }

    @Override
    public Collection<Body> findAllBodies(Integer id) {
        return tx(
                session -> session.createQuery(
                        "select distinct st from Body st ", Body.class).list()
        );
    }


    public <T> T findById(Class<T> cl, Integer id) {
        return tx(session -> session.get(cl, id));
    }

    @Override
    public Collection<Item> findItemPerDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return this.tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "where st.created between :stDate and current_timestamp", Item.class
                ).setParameter("stDate", c.getTime()).list()
        );
    }

    @Override
    public Collection<Item> findWithPhoto() {
        return this.tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "where st.photo is not null", Item.class
                ).list()
        );
    }

    @Override
    public Collection<Item> findCar(String name) {
        return this.tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "join fetch st.brand a "
                                + "where a.name = :sId", Item.class
                ).setParameter("sId", name).list()
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return tx(session ->
                (User) session.createQuery("from User where email = :email")
                        .setParameter("email", email)
                        .uniqueResult()
        );
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}