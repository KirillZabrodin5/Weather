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

        try {
            Validator.isEmptyLogin(login);
            User user = userDao.findById(login).orElseThrow();

            Validator.passwordIsValidWithHash(password, user.getPassword());

            HttpSession session = req.getSession(true);
            UUID uuidSession = (UUID) session.getAttribute("uuidSession");

            if (uuidSession == null) {
                uuidSession = UUID.randomUUID();
            }

            SessionEntity sessionEntity = new SessionEntity(uuidSession,
                    user.getId(), LocalDateTime.now().plusDays(1));

            sessionDao.save(sessionEntity);

            Cookie cookie = new Cookie("uuidSession", uuidSession.toString());
            resp.addCookie(cookie);

            resp.sendRedirect("/weather");

        } catch (Exception e) {
            doGet(req, resp);//возможно на doGet перенаправлять надо
        }


        //TODO а если возникнет какая-то ошибка, то пользователя кинуть в метод doGet(?) и
        //сообщить, что за ошибка произошла
        //для этого наверное и нужен таймлиф, хотя хз
    }
}
