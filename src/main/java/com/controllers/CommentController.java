package com.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Comment;
import com.services.Interfaces.IComment;

//NULL fname w lname and affect isnt working
@RestController
@EnableScheduling
@RequestMapping("/forum")
public class CommentController {
 //   @Autowired

	@Autowired
	IComment ic;

	//CRUD********************************************************************

	@PostMapping("addComment")
	void addComment(@RequestBody Comment c) throws IOException
	{
		ic.addComment(c);
		
		//CommentImpl.idstatic=c.getPost().getId();
	}
	
	
	@DeleteMapping("dComment/{idc}")
	void deleteComment(@PathVariable("idc") int idc)
	{
		
		ic.deleteComment(idc);
	}
	
	@PutMapping("updateComment")
	void updateComment(@RequestBody Comment c)
	{

	ic.updateComment(c);
	}
	
	
	
	@GetMapping("comments")

	List<Comment> getallcomments()
	{
		return ic.comments();
	}

	//ENDcrud********************************************************************


	@PutMapping("addUp/{idc}")
	void commentUp(@PathVariable("idc") int idc)
	{
		
ic.commentUp(idc);


	}
	@PutMapping("addDown/{idc}")
	void commentDown(@PathVariable("idc") int idc)
	{
		
ic.commentDown(idc);


	}

	
	
	@GetMapping("comments/{idp}")

	Set<Comment> getallcommentByPost(@PathVariable("idp") int idp)
	{
	return ic.commentsPost(idp);
	}
	
	
	@GetMapping("commentsup/{idp}")

	Set<Comment> getallcommentByPostInteraction(@PathVariable("idp") int idp)
	{
		return ic.commentsPostByInteraction(idp);
	}
	
	@GetMapping("commentsdate/{idp}")

	Set<Comment> getallcommentByPostDate(@PathVariable("idp") int idp)
	{
		return ic.commentsPostbyDate(idp);
	}
	
	
	@GetMapping("userComments/{idu}")
	@ResponseBody
	List<Comment> getallcommentByUser(@PathVariable("idu") int idu)
	{
		return	ic.commentsUser(idu);
	}
	
	
	
    
    
}