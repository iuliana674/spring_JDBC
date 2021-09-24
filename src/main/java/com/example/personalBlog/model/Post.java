package com.example.personalBlog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Setter
@Getter
public class Post {
    private int id;
    private Category category;
    private String title;
    private String fullText;
    private Date lastTimeModified;
    private long views;
    private User author;

    public Post(Category category, String title, String fullText, Date lastTimeModified, long views, User author) {
        this.category = category;
        this.title = title;
        this.fullText = fullText;
        this.lastTimeModified = lastTimeModified;
        this.views = views;
        this.author = author;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return String.format("Category: %s | Title: %s | Text: %s | Date: %s | Views: %d | Author: %s",
            category, title, fullText, lastTimeModified.toLocalDate().format(formatter), views, author);
    }
}
