package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import static util.WebPaths.HOME;
import static util.WebPaths.HOME_PAGE;

@WebServlet(name = "HomeServlet", urlPatterns = HOME)
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(HOME_PAGE).forward(request, response);
    }
}
