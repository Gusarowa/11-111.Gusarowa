package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import services.UserService;
import util.MD5;

import java.io.IOException;

import static util.ContextAttributeNames.USER_SERVICE;
import static util.SessionAttributeNames.AUTHENTICATED_USER;
import static util.WebPaths.*;

@WebServlet(name = "SignInServlet", value = SIGN_IN)
public class SignInServlet extends HttpServlet {
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(SIGN_IN_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        String login = request.getParameter("login");
        String password = MD5.hash(request.getParameter("password"));

        User existingUser = userService.findUserByLogin(login);

        if (existingUser == null || !existingUser.getPassword().equals(password)) {
            response.sendRedirect(SIGN_IN);
            return;
        }

        httpSession.setAttribute(AUTHENTICATED_USER, existingUser);
        response.sendRedirect(HOME);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute(USER_SERVICE);
    }
}
