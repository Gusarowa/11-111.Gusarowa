package repositories;

import config.PostgresConnectionProvider;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserRepository {
    public List<User> findAll() {
        // language=SQL
        String findAll = "select * from users_table";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findAll);
            ResultSet resultSet = statement.executeQuery();

            List<User> list = new LinkedList<>();

            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .role(resultSet.getString("role"))
                        .info(resultSet.getString("info"))
                        .build();

                list.add(user);
            }

            return list;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void save(User user) {
        // language=SQL
        String save = "insert into users_table (login, password) values (?,?)";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(save);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void update(User user) {
        // language=SQL
        String update = "update users_table set login = ?, info = ? where id = ?";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getInfo());
            statement.setLong(3, user.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
