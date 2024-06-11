import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("MARZ")
                    .firstname("Maria")
                    .lastname("Zabrodina")
                    .birthDate(LocalDate.of(2009, 10, 16))
                    .age(14)
                    .build();
            session.persist(user);

            session.getTransaction().commit();
        }
    }
}
