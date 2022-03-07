package com.repositories;

import java.util.List;

import com.entities.Comment;
import com.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface CommentRepository extends JpaRepository<Comment,Integer> {
	@Query("SELECT c FROM Comment c")
	List<Comment> getAllComments();
	@Query("SELECT c FROM Comment c where c.post=:post ")
	public List<Comment> getAllComments(@Param("post") Post post);
}
