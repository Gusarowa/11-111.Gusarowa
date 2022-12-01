package repositories;

import config.PostgresConnectionProvider;
import models.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CommentsRepository {
    public void save(Comment comment) {
        // language=SQL
        String save = "insert into comments_table (text, user_id, post_id) values (?, ?, ?)";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(save);
            statement.setString(1, comment.getText());
            statement.setLong(2, comment.getUserID());
            statement.setLong(3, comment.getPostID());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Comment> findAll() {
        // language=SQL
        String findAll = "select * from comments_table";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findAll);
            ResultSet resultSet = statement.executeQuery();

            List<Comment> comments = new LinkedList<>();

            while (resultSet.next()) {
                Comment comment = Comment.builder()
                        .id(resultSet.getLong("id"))
                        .text(resultSet.getString("text"))
                        .userID(resultSet.getLong("user_id"))
                        .postID(resultSet.getLong("post_id"))
                        .build();


                comments.add(comment);
            }

            return comments;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void delete(Long id) {
        // language=SQL
        String delete = "delete from comments_table where id = ?";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setLong(1, id);

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
