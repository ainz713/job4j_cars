package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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

    public <T> T tx(final Function<Session, T> command) {
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

    public <T> T findById(Class<T> cl, Integer id) {
        return tx(session -> session.get(cl, id));
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}