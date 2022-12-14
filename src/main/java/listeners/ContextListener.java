package listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import config.PostgresConnectionProvider;
import jakarta.servlet.annotation.WebListener;
import repositories.CommentRepository;
import repositories.PostRepository;
import repositories.UserRepository;
import services.CommentService;
import services.PostService;
import services.UserService;
import services.UserSignUpService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static util.ContextAttributeNames.*;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final String CREATE_USER_TABLE = "create table if not exists users_table (" +
            "id bigserial primary key ," +
            "login varchar(20)," +
            "role varchar(20) default 'user'," +
            "password varchar(100)," +
            "info varchar(1000) default '')";

    private static final String CREATE_POST_TABLE = "create table if not exists posts_table (" +
            "id bigserial primary key," +
            "title varchar(1000)," +
            "text varchar(2000)," +
            "imgName varchar(1000)," +
            "img bytea," +
            "user_id bigint references users_table(id))";

    // language=SQL
    private static final String CREATE_COMMENTS_TABLE = "create table if not exists comments_table (" +
            "id bigserial primary key," +
            "text varchar(1000)," +
            "user_id bigint references users_table(id)," +
            "post_id bigint references posts_table(id))";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        createTables();

        ServletContext servletContext = sce.getServletContext();

        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();
        CommentRepository commentRepository = new CommentRepository();
        UserService userService = new UserService(userRepository);
        UserSignUpService userSignUpService = new UserSignUpService(userRepository);
        PostService postService = new PostService(postRepository, commentRepository);
        CommentService commentService = new CommentService(commentRepository);

        servletContext.setAttribute(USER_REPOSITORY, userRepository);
        servletContext.setAttribute(POST_REPOSITORY, postRepository);
        servletContext.setAttribute(COMMENT_REPOSITORY, commentRepository);
        servletContext.setAttribute(USER_SERVICE, userService);
        servletContext.setAttribute(USER_SIGN_UP_SERVICE, userSignUpService);
        servletContext.setAttribute(POST_SERVICE, postService);
        servletContext.setAttribute(COMMENT_SERVICE, commentService);
    }

    private void createTables() {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement;

            statement = connection.prepareStatement(CREATE_USER_TABLE);
            statement.execute();

            statement = connection.prepareStatement(CREATE_POST_TABLE);
            statement.execute();

            statement = connection.prepareStatement(CREATE_COMMENTS_TABLE);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
