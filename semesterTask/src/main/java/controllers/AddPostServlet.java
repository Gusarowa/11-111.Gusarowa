package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import listeners.InitListener;
import models.Post;
import models.User;
import services.PostService;
import services.UserService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(name = "AddPostServlet", value = "/posts/add")
@MultipartConfig
public class AddPostServlet extends HttpServlet {

    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        Part part = request.getPart("img");
        User user = UserService.getAuthUser();
        String fileName = UUID.randomUUID() + "_" + part.getSubmittedFileName();


        Post post = Post.builder()
                .title(title)
                .text(text)
                .img(part.getInputStream().readAllBytes())
                .imgName(fileName)
                .userID(user.getId())
                .build();

        postService.savePost(post);
        response.sendRedirect("/posts");

    }
}
