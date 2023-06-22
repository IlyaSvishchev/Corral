package com.example.corral.repos;
import com.example.corral.models.News;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NewsRepo extends CrudRepository<News, Long> {
    Optional<News> findByName(String name);
    Optional<News> deleteByName(String name);
    boolean existsByName(String name);
}
