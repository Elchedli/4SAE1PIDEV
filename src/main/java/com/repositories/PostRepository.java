package com.repositories;

import java.util.List;

import com.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository  extends JpaRepository<Post,Integer>{
	@Query("SELECT p FROM Post p")
	List<Post> getAllPosts();
}
