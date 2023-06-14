package com.example.corral.repos;
import com.example.corral.models.News;
import org.springframework.data.repository.CrudRepository;
public interface NewsRepo extends CrudRepository<News, Long>{

}
