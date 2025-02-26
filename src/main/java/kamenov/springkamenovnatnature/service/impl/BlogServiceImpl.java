package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.entity.BlogPost;
import kamenov.springkamenovnatnature.repositories.BlogRepository;
import kamenov.springkamenovnatnature.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {


        @Autowired
        private final BlogRepository repository;

    public BlogServiceImpl(BlogRepository repository) {
        this.repository = repository;
    }
@Override
    public List<BlogPost> searchPosts(String title) {
            return repository.findByTitleContaining(title);

    }

}
