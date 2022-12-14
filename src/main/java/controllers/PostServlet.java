package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Comment;
import models.Post;
import models.User;
import services.CommentService;
import services.PostService;

import java.io.IOException;
import java.util.List;

import static util.ContextAttributeNames.COMMENT_SERVICE;
import static util.ContextAttributeNames.POST_SERVICE;
import static util.SessionAttributeNames.AUTHENTICATED_USER;
import static util.WebPaths.*;

@WebServlet(name = "PostServlet", value = POST)
public class PostServlet extends HttpServlet {
    private PostService postService;
    private CommentService commentService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        Long postId = Long.valueOf(request.getPathInfo().substring(1));

        Post post = postService.findPostById(postId);
        List<Comment> comments = commentService.findCommentsByPostId(postId);

        request.setAttribute("post", post);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher(POST_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                           IOException {
        HttpSession httpSession = request.getSession();

        User user = (User)httpSession.getAttribute(AUTHENTICATED_USER);

        Long postId = Long.valueOf(request.getPathInfo().substring(1));
        String text = request.getParameter("text");

        Comment comment = Comment.builder()
            .text(text)
            .userId(user.getId())
            .postId(postId)
            .build();

        commentService.saveComment(comment);

        String postWebPath = POSTS + '/' + postId;

        response.sendRedirect(postWebPath);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        postService = (PostService)servletContext.getAttribute(POST_SERVICE);
        commentService = (CommentService)servletContext.getAttribute(COMMENT_SERVICE);
    }
}
