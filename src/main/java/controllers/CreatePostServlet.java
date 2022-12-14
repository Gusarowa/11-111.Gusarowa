package controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Post;
import models.User;
import services.PostService;

import java.io.IOException;
import java.util.UUID;

import static util.ContextAttributeNames.POST_SERVICE;
import static util.SessionAttributeNames.AUTHENTICATED_USER;
import static util.WebPaths.*;

@WebServlet(name = "AddPostServlet", value = CREATE_POST)
@MultipartConfig
public class CreatePostServlet extends HttpServlet {
    private PostService postService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        request.getRequestDispatcher(CREATE_POST_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                           IOException {
        HttpSession httpSession = request.getSession();

        String title = request.getParameter("title");
        String text = request.getParameter("text");
        Part imagePart = request.getPart("img");

        User user = (User)httpSession.getAttribute(AUTHENTICATED_USER);

        String fileName = UUID.randomUUID() + "_" + imagePart.getSubmittedFileName();

        Post post = Post.builder()
            .title(title)
            .text(text)
            .imageData(imagePart.getInputStream().readAllBytes())
            .imageName(fileName)
            .userId(user.getId())
            .build();

        postService.savePost(post);

        response.sendRedirect(POSTS);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        postService = (PostService)servletContext.getAttribute(POST_SERVICE);
    }
}
