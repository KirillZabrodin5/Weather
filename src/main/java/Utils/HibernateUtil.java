package Utils;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;
    private static final Configuration configuration;

    static {
        configuration = new Configuration();
        configuration.configure("database/hibernate.cfg.xml");
        try {
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void addEntityToConfiguration(Class entity) {
        configuration.addAnnotatedClass(entity);
    }
}
