package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//серлет для отображения погоды на экране, для отображения
// главной страницы авторизированного пользователя
@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO надо добавлять сюда логики
        resp.setContentType("text/html");
        resp.getWriter().println("<h1>Hello World</h1>");
    }
}
