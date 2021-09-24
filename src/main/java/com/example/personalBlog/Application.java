package com.example.personalBlog;

import com.example.personalBlog.model.Category;
import com.example.personalBlog.model.Post;
import com.example.personalBlog.model.User;
import com.example.personalBlog.service.PostService;
import com.example.personalBlog.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = userService.getUserById(1);
        PostService postService = context.getBean("postService", PostService.class);
        Post post = postService.getPostById(1);
        Post stylePost = new Post(Category.Style, "Trendy colors",
            "Pastel color are trendy", Date.valueOf(LocalDate.now()), 1, user);
        //postService.insertPost(stylePost);
        Set<Post> stylePosts = postService.getPostsByCategory(Category.Style);
        Set<Post> postSet = postService.getAllPosts();
        System.out.println("Retrieved user: ");
        System.out.println(user);
        System.out.println("Post with id = 1: ");
        System.out.println(post);
        System.out.println("Inserted post: ");
        System.out.println(stylePosts);
        System.out.println("All posts from DB: ");
        postSet.forEach(System.out::println);
    }
}
