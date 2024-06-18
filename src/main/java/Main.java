import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


public class Main {
    public static void main(String[] args) {
        //TODO в сервлете
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setTemplateMode("HTML");
        classLoaderTemplateResolver.setPrefix("templates/"); //сделать константу префикса и суффикса
        classLoaderTemplateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);

        var context = new Context();
        context.setVariable("username", new String("Kirill"));
        var result = templateEngine.process("examle", context);
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
