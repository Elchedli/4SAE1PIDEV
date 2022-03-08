package com.controllers;

import java.util.List;

import com.entities.Post;
import com.entities.Tag;
import com.repositories.PostRepository;
import com.repositories.TagRepository;
import com.services.Implementations.GoogleTTS_Translate;
import com.services.Interfaces.IPost;
import com.services.Interfaces.ITag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forum")
public class TagController {
	@Autowired
	TagRepository tr;
	@Autowired 
	ITag it;
	@Autowired
IPost ip;
	
	
	@PutMapping("addTag/{idp}/{tag}")
	void addtags(@PathVariable("idp") int idp,@PathVariable("tag") String tag)
	{
		 ip.addTag(idp,tag);

	}
	
	@PostMapping("addTag")
	void addComment(@RequestBody Tag t)
	{
it.addTag(t);
		
		//CommentImpl.idstatic=c.getPost().getId();
	}
	
	
	@GetMapping("tags")
	List<Tag> getallTag()
	{
		return it.allTags();
	}
	
	
	
	
	
	@GetMapping("countTag/{tag}")
	@ResponseBody
	int countTag(@PathVariable("tag") String s)
	{	String eng=GoogleTTS_Translate.google_Translate("en",s);
	s=eng.toLowerCase();
		Tag tag=tr.findById(s).orElse(null);
		
		return it.countTags(tag);
	}
	
}
