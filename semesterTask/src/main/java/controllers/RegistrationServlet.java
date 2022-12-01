package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import services.UserService;
import utils.MD5HF;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RegistrationServlet", value = "/reg")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/reg.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = MD5HF.hashPassword(request.getParameter("password"));
        UserService userService = new UserService();
        List<User> allUsers = userService.getAllUsers();

        boolean alreadyExists = allUsers.stream()
                .anyMatch(user -> user.getLogin().equals(login));

        if (!alreadyExists) {
            User user = User.builder().login(login).password(password).build();
            userService.save(user);

            response.sendRedirect("/reg");
        } else {
            response.sendRedirect("/reg");
        }
    }
}
