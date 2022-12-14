package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Post;
import services.PostService;

import java.io.IOException;
import java.util.List;

import static util.ContextAttributeNames.POST_SERVICE;
import static util.WebPaths.*;
import static util.SessionAttributeNames.*;

@WebServlet(name = "ListPostsServlet", value = POSTS)
public class ListPostsServlet extends HttpServlet {
    private PostService postService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Post> posts = postService.getAllPosts();

        request.setAttribute("posts", posts);

        request.getRequestDispatcher(POSTS_PAGE).forward(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        postService = (PostService)servletContext.getAttribute(POST_SERVICE);
    }
}
