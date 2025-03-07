package kamenov.springkamenovnatnature.web;

import kamenov.springkamenovnatnature.entity.BlogPost;
import kamenov.springkamenovnatnature.service.BlogService;
import kamenov.springkamenovnatnature.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin(origins = "http://localhost:3000")
public class BlogController {
    @Autowired
    private  final BlogService service;

    public BlogController(BlogService service) {
        this.service = service;
    }


    @GetMapping("/posts")
    public List<BlogPost> searchPosts(@RequestParam String title) {
        return service.searchPosts(title);
    }
}
