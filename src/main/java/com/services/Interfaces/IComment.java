package com.services.Interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.entities.Comment;
import com.entities.Post;



public interface IComment {
	public void addComment(Comment c) throws IOException;
	public void updateComment(Comment c);
	public void deleteComment(int idc);
	public List<Comment> comments();
	public List<Comment> comments(Post p);
	public List<Comment> commentsUser(long idu);
	public List<Comment> commentsUserByDate(long idu);

	
	public Set<Comment> commentsPost(int idp);
	public Set<Comment> commentsPostbyDate(int idu);
	public Set<Comment> commentsPostByInteraction(int idu);
	
	public void commentUp(int idc);
	public void commentDown(int idc);
	
}
