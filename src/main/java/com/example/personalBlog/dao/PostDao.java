package com.example.personalBlog.dao;

import com.example.personalBlog.model.Category;
import com.example.personalBlog.model.Post;
import com.example.personalBlog.model.User;

import java.util.Set;

public interface PostDao {

    public void insertPost(Post post);

    public Post getPostById(int id);

    public Set<Post> getAllPosts();

    public Set<Post> getPostsByCategory(Category category);

    public Set<Post> getPostsByUser(User user);

    public void updatePost(int id, Post newPost);

    public void deletePost(int id);
}
