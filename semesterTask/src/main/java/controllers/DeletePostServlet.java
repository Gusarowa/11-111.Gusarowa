package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import services.CommentService;
import services.PostService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeletePostServlet", value = "/posts/delete")
public class DeletePostServlet extends HttpServlet {

    private final CommentService commentService = new CommentService();
    private final PostService postService = new PostService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long postID = Long.parseLong(request.getParameter("postID"));

        commentService.getAllComments().forEach(comment -> {
            if (comment.getPostID().equals(postID)) {
                commentService.deleteComment(comment.getId());
            }
        });

        postService.deletePost(postID);

        response.sendRedirect("/posts");
    }
}
