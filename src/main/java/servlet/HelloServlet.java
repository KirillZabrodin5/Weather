package servlet;

import jakarta.servlet.ServletConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration.successful")
public class HelloServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");

        var context = new Context();
        context.setVariable("username", username);
        var result = templateEngine.process("examle", context);

        resp.getWriter().println(result);
    }
}
