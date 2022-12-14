package repositories;

import models.Post;
import config.PostgresConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PostRepository implements EntityRepository<Post> {
    private static final Function<ResultSet, Post> POST_MAPPER = resultSet -> {
        try {
            return Post.builder()
                    .id(resultSet.getLong("id"))
                    .title(resultSet.getString("title"))
                    .text(resultSet.getString("text"))
                    .imageData(resultSet.getBytes("img"))
                    .imageName(resultSet.getString("imgname"))
                    .userId(resultSet.getLong("user_id"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    private static final String SELECT_ALL = "select * from posts_table";

    private static final String SELECT_BY_ID = "select * from posts_table where id = ?";

    private static final String SELECT_BY_IMAGE_NAME = "select * from posts_table where imgname = ?";

    private static final String INSERT = "insert into posts_table (title, text, img, user_id, imgname) values (?, ?, ?, ?, ?)";

    private static final String UPDATE = "update posts_table set title = ?, text = ?, img = ?, imgname = ? where id = ?";

    private static final String DELETE = "delete from posts_table where id = ?";

    @Override
    public List<Post> findAll() {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Post> posts = new ArrayList<>();

            while (resultSet.next()) {
                posts.add(POST_MAPPER.apply(resultSet));
            }

            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(POST_MAPPER.apply(resultSet));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Post> findByImageName(String imageName) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_IMAGE_NAME)) {
            preparedStatement.setString(1, imageName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(POST_MAPPER.apply(resultSet));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getText());
            statement.setBytes(3, post.getImageData());
            statement.setLong(4, post.getUserId());
            statement.setString(5, post.getImageName());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                post.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Post post) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getText());
            statement.setBytes(3, post.getImageData());
            statement.setString(4, post.getImageName());
            statement.setLong(5, post.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Post post) {
        deleteById(post.getId());
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
}
