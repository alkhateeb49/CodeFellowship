package com.example.CodeFellowship;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Integer> {
    @Query(value = "SELECT * FROM post where owner_id = ?1", nativeQuery = true)
    List<Post> findByOwnerId(int id);
}
