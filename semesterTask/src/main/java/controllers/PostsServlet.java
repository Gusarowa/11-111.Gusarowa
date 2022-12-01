package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Post;
import services.PostService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "PostsServlet", value = "/posts")
public class PostsServlet extends HttpServlet {

    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Post> posts = postService.getAllPosts();

        String query = request.getParameter("query");
        if (query != null) {
            posts = posts.stream().filter(post -> post.getTitle().contains(query)).collect(Collectors.toList());
        }

        request.setAttribute("posts", posts);
        request.getRequestDispatcher("/WEB-INF/jsp/posts.jsp").forward(request, response);
    }


}
