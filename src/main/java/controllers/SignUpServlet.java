package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import services.UserService;
import services.UserSignUpService;
import util.MD5;

import java.io.IOException;

import static util.ContextAttributeNames.USER_SERVICE;
import static util.ContextAttributeNames.USER_SIGN_UP_SERVICE;
import static util.WebPaths.*;

@WebServlet(name = "SignUpServlet", value = SIGN_UP)
public class SignUpServlet extends HttpServlet {
    private UserService userService;
    private UserSignUpService userSignUpService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(SIGN_UP_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        String login = request.getParameter("login");
        String password = MD5.hash(request.getParameter("password"));

        User existingUser = userService.findUserByLogin(login);

        if (existingUser == null) {
            User user = User.builder()
                    .login(login)
                    .password(password)
                    .build();
            userSignUpService.signUp(user);
            response.sendRedirect(SIGN_IN);
        } else {
            response.sendRedirect(SIGN_UP);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute(USER_SERVICE);
        userSignUpService = (UserSignUpService) servletContext.getAttribute(USER_SIGN_UP_SERVICE);
    }
}
