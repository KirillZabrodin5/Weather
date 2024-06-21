package servlet;

import Utils.Validator;
import dao.SessionDao;
import dao.UserDao;
import entity.Session;
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


        //TODO то, что ниже надо выполнять в блоке try
        Validator.isEmptyLogin(login);

        User user = userDao.findById(login).orElseThrow();

        Validator.passwordIsValid(password, user.getPassword());

        UUID uuidSession = UUID.randomUUID();//это должно в ДАО слое происходить, я думаю

        HttpSession session = req.getSession(true);
        session.setAttribute("id", uuidSession);

        Session sessionDB = new Session(uuidSession, user.getId(), LocalDateTime.now().plusDays(1));

        SessionDao sessionDao = new SessionDao();
        sessionDao.save(sessionDB);

        Cookie cookie = new Cookie("sessionId", uuidSession.toString());
        resp.addCookie(cookie);

        //тут надо получить уже с id сессию. То есть записать в бд текущую сессию
        // и взять её заново, но уже с ID и отправить этот id в куки

        //и потом сделать редирект на weather servlet
        //req.getServletContext().getRequestDispatcher("/weather").forward(req, resp);
        resp.sendRedirect("/weather");

        //TODO а если возникнет какая-то ошибка, то пользователя кинуть в метод doGet(?) и
        //сообщить, что за ошибка произошла
    }
}
