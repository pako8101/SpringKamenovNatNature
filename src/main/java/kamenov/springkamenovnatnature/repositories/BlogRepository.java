package kamenov.springkamenovnatnature.repositories;

import kamenov.springkamenovnatnature.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost,Long> {


        List<BlogPost> findByTitleContaining(String title);


}
