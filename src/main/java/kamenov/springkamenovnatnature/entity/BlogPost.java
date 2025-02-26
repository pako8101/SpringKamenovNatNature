package kamenov.springkamenovnatnature.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "blog_post")
    public class BlogPost extends BaseEntity{

        private String title;
        private String content;
        private LocalDate date;

    public BlogPost() {
    }

    public String getTitle() {
        return title;
    }

    public BlogPost setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BlogPost setContent(String content) {
        this.content = content;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public BlogPost setDate(LocalDate date) {
        this.date = date;
        return this;
    }
}



