package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Post;
import services.PostService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/images/*")
public class Images extends HttpServlet {

    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imgName = req.getPathInfo().substring(1);
        List<Post> posts = postService.getAllPosts();

        for (Post post : posts) {
            if (post.getImgName().equals(imgName)) {
                byte[] img = post.getImg();
                resp.setContentType(getServletContext().getMimeType(imgName));
                resp.setContentLength(img.length);
                resp.getOutputStream().write(img);
                return;
            }
        }
    }
}
