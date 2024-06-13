import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        var resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        var context = new Context();
        context.setVariable("name", "Bunny");
        context.setVariable("date", LocalDate.now().toString());

        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        var result = templateEngine.process("index", context);
        System.out.println(result);
        //TODO: код для добавления в БД сущности
//        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(User.class);
//        configuration.addAnnotatedClass(Location.class);
//        configuration.configure();
//
//        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
//            Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            Location location = Location.builder()
//                    .name("Moscow")
//                    .userId(1)
//                    .latitude(new BigDecimal("10.50"))
//                    .longitude(new BigDecimal("11.7"))
//                    .build();
//            session.persist(location);
//            session.getTransaction().commit();
//        }
    }
}
