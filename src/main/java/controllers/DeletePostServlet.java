package controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.PostService;

import java.io.IOException;

import static util.ContextAttributeNames.POST_SERVICE;
import static util.WebPaths.DELETE_POST;
import static util.WebPaths.POSTS;

@WebServlet(name = "DeletePostServlet", value = DELETE_POST)
public class DeletePostServlet extends HttpServlet {
    private PostService postService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                           IOException {
        Long postId = Long.parseLong(request.getParameter("postID"));

        postService.deletePostById(postId);

        response.sendRedirect(POSTS);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        postService = (PostService)servletContext.getAttribute(POST_SERVICE);
    }
}
