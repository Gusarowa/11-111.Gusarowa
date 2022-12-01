package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import services.UserService;
import utils.MD5HF;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AuthServlet", value = "/auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/auth.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = MD5HF.hashPassword(request.getParameter("password"));
        UserService userService = new UserService();
        List<User> allUsers = userService.getAllUsers();

        for (User user : allUsers) {
            if (user.getLogin().equals(login)) {
                if (user.getPassword().equals(password)) {
                    request.getSession().setAttribute("authUser", user);
                    response.sendRedirect("/home");
                    return;
                }
            }
        }

        response.sendRedirect("/auth");
    }
}
