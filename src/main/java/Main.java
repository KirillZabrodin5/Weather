import entity.Location;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Location.class);
        configuration.configure();

        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Location location = Location.builder()
                    .name("Moscow")
                    .userId(1)
                    .latitude(new BigDecimal("10.50"))
                    .longitude(new BigDecimal("11.7"))
                    .build();
            session.persist(location);
            session.getTransaction().commit();
        }
    }
}
