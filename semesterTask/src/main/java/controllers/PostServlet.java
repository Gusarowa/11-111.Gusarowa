package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Comment;
import models.Post;
import models.User;
import services.CommentService;
import services.PostService;
import services.UserService;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "PostServlet", urlPatterns = "/posts/*")
public class PostServlet extends HttpServlet {

    private final PostService postService = new PostService();
    private final CommentService commentService = new CommentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long postID = Long.valueOf(request.getPathInfo().substring(1));
            Post post = postService.findPost(postID);

            request.setAttribute("post", post);
            request.setAttribute("comments", commentService.getAllComments().stream()
                    .filter(comment -> comment.getPostID().equals(postID)).collect(Collectors.toList())
            );
            request.getRequestDispatcher("/WEB-INF/jsp/post.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long postID = Long.valueOf(request.getPathInfo().substring(1));
        User user = UserService.getAuthUser();
        String text = request.getParameter("text");
        Comment comment = Comment.builder()
                .text(text)
                .userID(user.getId())
                .postID(postID)
                .build();
        commentService.saveComment(comment);
        response.sendRedirect("/posts/" + postID);
    }
}
