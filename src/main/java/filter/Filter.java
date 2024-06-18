package filter;

import exception.InvalidParameterException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class Filter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");

        try {
            res.setStatus(HttpServletResponse.SC_OK);
            super.doFilter(req, res, chain);
        } catch (InvalidParameterException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //writeExceptionToResponse(response, HttpServletResponse.SC_BAD_REQUEST, e);
        }
    }
}
