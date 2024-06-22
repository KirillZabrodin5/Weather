package servlet;

import Utils.Validator;
import dao.SessionDao;
import dao.UserDao;
import entity.SessionEntity;
import entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private static final String PREFIX = "templates/";
    private static final String SUFFIX = ".html";
    private static final UserDao userDao = new UserDao();
    private static final SessionDao sessionDao = new SessionDao();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setTemplateMode("HTML");
        classLoaderTemplateResolver.setPrefix(PREFIX);
        classLoaderTemplateResolver.setSuffix(SUFFIX);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = new Context();
        var result = templateEngine.process("auth", context);
        resp.getWriter().println(result);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");


        //TODO подумать еще над этим try catch
        try {
            Validator.isEmptyLogin(login);
            User user = userDao.findById(login).orElseThrow();

            Validator.passwordIsValidWithHash(password, user.getPassword());

            UUID uuidSession;

            HttpSession session = req.getSession(true);
            UUID uuidHttpSession = (UUID) session.getAttribute("UUID");
            if (uuidHttpSession != null) {
                uuidSession = uuidHttpSession;
                sessionDao.findById(uuidSession).get();
                Cookie cookie = new Cookie("uuidSession", uuidSession.toString());
                resp.addCookie(cookie);
            } else {
                uuidSession = UUID.randomUUID();//это должно в ДАО слое происходить, я думаю
                session.setAttribute("UUID", uuidSession);

                SessionEntity sessionEntity = new SessionEntity(uuidSession,
                        user.getId(), LocalDateTime.now().plusDays(1));
                sessionDao.save(sessionEntity);
            }

            Cookie cookie = new Cookie("uuidSession", uuidSession.toString());
            resp.addCookie(cookie);

            //тут надо получить уже с id сессию. То есть записать в бд текущую сессию
            // и взять её заново, но уже с ID и отправить этот id в куки

            //и потом сделать редирект на weather servlet
            //req.getServletContext().getRequestDispatcher("/weather").forward(req, resp);
            resp.sendRedirect("/weather");
        } catch (Exception e) {
            resp.sendRedirect("/auth");
        }


        //TODO а если возникнет какая-то ошибка, то пользователя кинуть в метод doGet(?) и
        //сообщить, что за ошибка произошла
        //для этого наверное и нужен таймлиф, хотя хз
    }
}
