package kamenov.springkamenovnatnature.service;

import kamenov.springkamenovnatnature.entity.BlogPost;

import java.util.List;

public interface BlogService {
    List<BlogPost> searchPosts(String title);
}
