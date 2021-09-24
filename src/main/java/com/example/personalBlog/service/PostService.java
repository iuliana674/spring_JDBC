package com.example.personalBlog.service;

import com.example.personalBlog.dao.PostDao;
import com.example.personalBlog.model.Category;
import com.example.personalBlog.model.Post;
import com.example.personalBlog.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class PostService implements PostDao {
    private DataSource dataSource;
    private UserService userService;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void insertPost(Post post) {
        String query = "INSERT INTO Post (category, title, full_text," +
            " last_modification, views, id_author) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, post.getCategory().name());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getFullText());
            statement.setDate(4, post.getLastTimeModified());
            statement.setLong(5, post.getViews());
            statement.setInt(6, post.getAuthor().getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public Post getPostById(int id) {
        Post post = null;
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Post WHERE id = " + id);
            if(rs.next()) {
                post = getPostFromDB(rs);
            }
            rs.close(); statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return post;
    }

    @Override
    public Set<Post> getAllPosts() {
        Set<Post> posts = new HashSet<>();
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Post");
            while(rs.next()){
                posts.add(getPostFromDB(rs));
            }
            rs.close(); statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return posts;
    }

    @Override
    public Set<Post> getPostsByCategory(Category category) {
        Set<Post> posts = new HashSet<>();
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.
                    executeQuery("SELECT * FROM Post WHERE category = '" + category.name() + "'");
            while(rs.next()){
                posts.add(getPostFromDB(rs));
            }
            rs.close(); statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return posts;
    }

    @Override
    public Set<Post> getPostsByUser(User user) {
        Set<Post> posts = new HashSet<>();
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery
                    ("SELECT * FROM Post WHERE id_author = '" + user.getId() + "'");
            while(rs.next()){
                posts.add(getPostFromDB(rs));
            }
            rs.close(); statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return posts;
    }

    @Override
    public void updatePost(int id, Post newPost) {

    }

    @Override
    public void deletePost(int id) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Post WHERE id = ? ");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Post getPostFromDB(ResultSet rs){
        Post post = null;
        try {
            int authorId = rs.getInt("id_author");
            User author = userService.getUserById(authorId);
            post = new Post(rs.getInt("id"),
                    Category.valueOf(rs.getString("category")),
                    rs.getString("title"), rs.getString("full_text"),
                    rs.getDate("last_modification"), rs.getInt("views"), author);
        } catch (SQLException throwable){
            throwable.printStackTrace();
        }
        return post;
    }
}
