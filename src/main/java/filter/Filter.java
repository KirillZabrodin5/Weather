package filter;

import exception.InvalidParameterException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class Filter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");

        try {
            HttpSession session = req.getSession(false);
            if ((session == null || session.getAttribute("uuidSession") == null)
                    && req.getRequestURI().equals("/weather")) {
                //res.sendRedirect("/auth");
                req.getRequestDispatcher("/auth").forward(req, res);
            }

            res.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
            //иначе перенаправляем на страницу sign up

//            Cookie[] cookies = req.getCookies();
//            if (cookies == null) {
//                res.sendRedirect("/auth");
//            } else {
//                boolean isExistUuidSession = false;
//                for (Cookie cookie : cookies) {
//                    if (cookie.getName().equals("uuidSession")) {
//                        isExistUuidSession = true;
//                        break;
//                    }
//                }
//                if (isExistUuidSession) {
//                    res.sendRedirect("/weather");
//                }
//            }


        } catch (Exception e) {
            //res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().println(e.getMessage());
            //writeExceptionToResponse(response, HttpServletResponse.SC_BAD_REQUEST, e);
        }
    }
}
