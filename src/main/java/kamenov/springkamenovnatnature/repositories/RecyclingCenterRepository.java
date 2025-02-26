package kamenov.springkamenovnatnature.repositories;

import kamenov.springkamenovnatnature.entity.RecyclingCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingCenterRepository extends JpaRepository<RecyclingCenter, Long> {

    List<RecyclingCenter> findByCity(String city);
}
