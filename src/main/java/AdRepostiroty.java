import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Item;

import java.util.Calendar;
import java.util.Collection;
import java.util.function.Function;

public class AdRepostiroty {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

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

    public Collection<Item> findWithPhoto() {
        return this.tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "where st.photo is not null", Item.class
                ).list()
        );
    }

    public Collection<Item> findCar(String name) {
        return this.tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "join fetch st.car a "
                                + "where a.name = :sId", Item.class
                ).setParameter("sId", name).list()
        );
    }

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
}
