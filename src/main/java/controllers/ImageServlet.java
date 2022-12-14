package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Post;
import services.PostService;

import java.io.IOException;

import static util.ContextAttributeNames.POST_SERVICE;
import static util.WebPaths.IMAGE;

@WebServlet(name = "ImageServlet", value = IMAGE)
public class ImageServlet extends HttpServlet {

    private PostService postService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        HttpSession httpSession = request.getSession();

        String imageName = request.getPathInfo().substring(1);

        Post post = postService.findPostByImageName(imageName);

        if (post != null) {
            byte[] img = post.getImageData();
            response.setContentType(getServletContext().getMimeType(imageName));
            response.setContentLength(img.length);
            response.getOutputStream().write(img);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        postService = (PostService)servletContext.getAttribute(POST_SERVICE);
    }
}
