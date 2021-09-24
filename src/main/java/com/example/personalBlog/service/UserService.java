package com.example.personalBlog.service;

import com.example.personalBlog.dao.UserDao;
import com.example.personalBlog.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserService implements UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Set<User> getAllUsers() {
        String query = "SELECT * FROM User";
        Set<User> users = new HashSet<>();
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                User user = new User(rs.getInt("id"),
                        rs.getString("first_name"), rs.getString("second_name"));
                users.add(user);
            }
            rs.close(); statement.close();
        } catch (SQLException throwable) {
            System.out.println("An exception occurred: " + throwable.getMessage());
            throwable.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        String query = "SELECT * FROM User WHERE id = ?";
        User user = null;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                user = new User(rs.getInt("id"),
                        rs.getString("first_name"), rs.getString("second_name"));
            }
            rs.close(); preparedStatement.close();
        } catch (SQLException throwable) {
            System.out.println("An exception occurred: " + throwable.getMessage());
            throwable.printStackTrace();
        }
        return user;
    }

    @Override
    public int deleteUserById(int id) {
        String query = "DELETE FROM User WHERE id = ?";
        int code = -1;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwable) {
            System.out.println("An exception occurred: " + throwable.getMessage());
            throwable.printStackTrace();
        }
        return code;
    }
}
