package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Post;
import services.PostService;

import java.io.IOException;
import java.util.UUID;

import static util.ContextAttributeNames.POST_SERVICE;
import static util.WebPaths.*;

@WebServlet(name = "UpdatePostServlet", value = UPDATE_POST)
@MultipartConfig
public class UpdatePostServlet extends HttpServlet {
    private PostService postService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        Long postId = Long.parseLong(request.getParameter("postID"));
        Post post = postService.findPostById(postId);

        request.setAttribute("post", post);
        request.getRequestDispatcher(UPDATE_POST_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                           IOException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        Part part = request.getPart("img");

        Long postId = Long.parseLong(request.getParameter("postID"));
        Post post = postService.findPostById(postId);
        post.setTitle(title);
        post.setText(text);

        if (!part.getSubmittedFileName().equals("")) {
            String imageName = UUID.randomUUID() + "_" + part.getSubmittedFileName();
            post.setImageName(imageName);
            post.setImageData(part.getInputStream().readAllBytes());
        }

        postService.updatePost(post);

        String postWebPath = POSTS + '/' + postId;

        response.sendRedirect(postWebPath);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        postService = (PostService)servletContext.getAttribute(POST_SERVICE);
    }
}
