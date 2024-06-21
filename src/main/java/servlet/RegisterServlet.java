package servlet;

import Utils.Validator;
import dao.UserDao;
import entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import service.Encryption;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
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
        var result = templateEngine.process("register", context);
        resp.getWriter().println(result);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeatedPassword = req.getParameter("Repeat password");

        Validator.isEmptyLogin(login);
        Validator.passwordIsValid(password, repeatedPassword);

        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);

        User user = new User(null, login, password);

        userDao.save(user).orElseThrow();

        resp.sendRedirect("/registration.successful?username=" + login);

        //TODO добавить кнопку с ссылкой на авторизацию
    }
}