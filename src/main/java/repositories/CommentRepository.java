package repositories;

import models.Comment;
import config.PostgresConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CommentRepository implements EntityRepository<Comment> {
    private static final Function<ResultSet, Comment> COMMENT_MAPPER = resultSet -> {
        try {
            return Comment.builder()
                    .id(resultSet.getLong("id"))
                    .text(resultSet.getString("text"))
                    .userId(resultSet.getLong("user_id"))
                    .postId(resultSet.getLong("post_id"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    private static final String SELECT_ALL = "select * from comments_table";

    private static final String SELECT_BY_ID = "select * from comments_table where id = ?";

    private static final String SELECT_BY_POST_ID = "select * from comments_table where post_id = ?";

    private static final String INSERT = "insert into comments_table (text, user_id, post_id) values (?, ?, ?)";

    private static final String UPDATE = "update comments_table set text = ?, user_id = ?, post_id = ? where id = ?";

    private static final String DELETE = "delete from comments_table where id = ?";

    private static final String DELETE_BY_POST_ID = "delete from comments_table where post_id = ?";

    @Override
    public List<Comment> findAll() {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Comment> comments = new ArrayList<>();

            while (resultSet.next()) {
                comments.add(COMMENT_MAPPER.apply(resultSet));
            }

            return comments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(COMMENT_MAPPER.apply(resultSet));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> findByPostId(Long postId) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_POST_ID)) {
            preparedStatement.setLong(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Comment> comments = new ArrayList<>();

            while (resultSet.next()) {
                comments.add(COMMENT_MAPPER.apply(resultSet));
            }

            return comments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Comment comment) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, comment.getText());
            statement.setLong(2, comment.getUserId());
            statement.setLong(3, comment.getPostId());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                comment.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Comment comment) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, comment.getText());
            statement.setLong(2, comment.getUserId());
            statement.setLong(3, comment.getPostId());
            statement.setLong(4, comment.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Comment comment) {
        deleteById(comment.getId());
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByPostId(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_POST_ID)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
