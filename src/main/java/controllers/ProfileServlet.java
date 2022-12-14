package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import services.UserService;

import java.io.IOException;

import static util.ContextAttributeNames.USER_SERVICE;
import static util.SessionAttributeNames.AUTHENTICATED_USER;
import static util.WebPaths.PROFILE;
import static util.WebPaths.PROFILE_PAGE;

@WebServlet(name = "ProfileServlet", value = PROFILE)
public class ProfileServlet extends HttpServlet {
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        HttpSession httpSession = request.getSession();

        User user = (User)httpSession.getAttribute(AUTHENTICATED_USER);

        request.setAttribute("login", user.getLogin());
        request.setAttribute("info", user.getInfo());

        request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                           IOException {
        HttpSession httpSession = request.getSession();

        User user = (User)httpSession.getAttribute(AUTHENTICATED_USER);

        String login = request.getParameter("login");
        String info = request.getParameter("info");

        user.setLogin(login);
        user.setInfo(info);

        userService.updateUser(user);

        response.sendRedirect(PROFILE);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        userService = (UserService)servletContext.getAttribute(USER_SERVICE);
    }
}
