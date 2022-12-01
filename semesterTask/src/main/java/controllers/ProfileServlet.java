package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import services.UserService;

import java.io.IOException;

@WebServlet(name = "ProfileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {

    private final UserService userService  = new UserService();
    private User user;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.user = UserService.getAuthUser();

        request.setAttribute("login", user.getLogin());
        request.setAttribute("info", user.getInfo());

        request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String info = request.getParameter("info");
        this.user.setLogin(login);
        this.user.setInfo(info);

        userService.updateUser(this.user);
        response.sendRedirect("/profile");
    }
}
