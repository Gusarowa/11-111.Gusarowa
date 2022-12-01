package repositories;

import config.PostgresConnectionProvider;
import models.Post;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PostsRepository {
    public List<Post> findAll() {
        // language=SQL
        String findAll = "select * from posts_table";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findAll);
            ResultSet resultSet = statement.executeQuery();

            List<Post> list = new LinkedList<>();

            while (resultSet.next()) {
                Post post = Post.builder()
                        .id(resultSet.getLong("id"))
                        .title(resultSet.getString("title"))
                        .text(resultSet.getString("text"))
                        .img(resultSet.getBytes("img"))
                        .imgName(resultSet.getString("imgname"))
                        .userID(resultSet.getLong("user_id"))
                        .build();

                list.add(post);
            }

            return list;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Optional<Post> findById(Long id) {
        // language=SQL
        String find = "select * from posts_table where id = ?";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(find);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Post.builder()
                                .id(resultSet.getLong("id"))
                                .title(resultSet.getString("title"))
                                .text(resultSet.getString("text"))
                                .img(resultSet.getBytes("img"))
                                .imgName(resultSet.getString("imgname"))
                                .userID(resultSet.getLong("user_id"))
                                .build()
                );
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void save(Post post) {
        // language=SQL
        String save = "insert into posts_table (title, text, img, user_id, imgname) values (?, ?, ?, ?, ?)";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(save);
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getText());
            statement.setBytes(3, post.getImg());
            statement.setLong(4, post.getUserID());
            statement.setString(5, post.getImgName());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void delete(Long id) {
        // language=SQL
        String delete = "delete from posts_table where id = ?";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setLong(1, id);

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void update(Post post) {
        // language=SQL
        String update = "update posts_table set title = ?, text = ?, img = ?, imgname = ? where id = ?";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getText());
            statement.setBytes(3, post.getImg());
            statement.setString(4, post.getImgName());
            statement.setLong(5, post.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
