package servlet;

import Utils.Validator;
import entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.Cookie;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private static final String PREFIX = "templates/";
    private static final String SUFFIX = ".html";

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
        Validator.equalsPassword(password, repeatedPassword);

        User user = new User(null, login, password);
        //save(user) TODO вместо этой строки надо в бд сохранять данные пользователя
        resp.sendRedirect("/hello?username=" + login);
        //TODO подумать, куда редирект делать после регистрации
    }

}