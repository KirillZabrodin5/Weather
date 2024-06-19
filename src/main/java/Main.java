import dao.UserDao;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {
        User user = User.builder()
                .login("fea.z")
                .password("pass")
                .build();

        UserDao userDao = new UserDao();
        System.out.println(userDao.findById("fea.z").orElseThrow());

//        //TODO: код для добавления в БД сущности
//        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(User.class);
//        //configuration.addAnnotatedClass(Location.class);
//        configuration.configure();
//
//        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
//            Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//
//
//            session.persist(user);
//            session.getTransaction().commit();
//        }
    }
}
