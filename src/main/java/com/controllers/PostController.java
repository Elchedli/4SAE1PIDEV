package com.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.entities.Post;
import com.entities.Tag;
import com.repositories.PostRepository;
import com.repositories.TagRepository;
import com.services.Implementations.FileUploadUtil;
import com.services.Interfaces.IComment;
import com.services.Interfaces.IPost;

import net.sf.extjwnl.JWNLException;
//NULL fname w lname and affect isnt working
@RestController
@EnableScheduling
@RequestMapping("/forum")
public class PostController {
 //   @Autowired
	@Autowired
	TagRepository tr;
	@Autowired
	IPost ip;
	@Autowired
	IComment ic;
	@Autowired
	PostRepository pr;
	
	
	
	//CRUD********************************************************************
	/*@PostMapping("addPost")
	@ResponseBody
	String addPost(@RequestBody Post p)
	{
		return ip.addPost(p);
	}
	

	*/
	

	@PostMapping("addPost")
	@ResponseBody
	public String ajoutervoyage(@RequestPart Post post,@RequestParam("file") MultipartFile file) throws IOException{
		if(!file.isEmpty())
		{ System.out.println("Hereeee");
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());   
    post.setFile(file.getBytes());
    String uploadDir = "postsFiles/";
    FileUploadUtil.saveFile(uploadDir, fileName, file);
		}
 //   return fileName;
	return ip.addPost(post);
	}
	
	
	
	
	
	
	@DeleteMapping("dPost/{idp}")
	void deletePost(@PathVariable("idp") int idp)
	{
		ip.deletePost(idp);
	}
	
	@PutMapping("updatePost")
	void updatePost(@RequestBody Post p)
	{

	ip.updatePost(p);
	}
	
	
	
	@GetMapping("posts")

	List<Post> getallposts()
	{
		return ip.allPost();
	}
	
	
	

	@GetMapping("tagPosts/{tag}")
	@ResponseBody
	List<Post> tagPosts(@PathVariable("tag") String t)
	{
		Tag tag=tr.findById(t).orElse(null);
	
		return ip.findTagPosts(tag);
	}
 
	
	
	
	
	
	
	//ENDcrud********************************************************************
	
	
	
	
    @GetMapping("/trends/{days}")
	@ResponseBody
    public Map<Integer, Post> greeting(@PathVariable("days") int days) {
     return ip.topPosts(days);
    }
    
    //FOLLOW *************************************************************
    
    
	@PutMapping("follow/{idp}/{idu}")
	public void follow(@PathVariable("idp") int idp,@PathVariable("idu") int idu) {
		ip.follow(idp,idu);
	}
	
	@PutMapping("unfollow/{idp}/{idu}")
	public void unfollow(@PathVariable("idp") int idp,@PathVariable("idu") int idu) {
		ip.unfollow(idp,idu);
			
		}

    
        
	@PutMapping("validate/{idp}/{idc}")
    public void validateComment(@PathVariable("idp") int idp,@PathVariable("idc") int idc) {
	
    	ip.validateComment(idp,idc);
    }
	
	
	//RELATED POSTS
	
	 @GetMapping("/syn/{s}")
		@ResponseBody
	    public List<String> syn(@PathVariable("s") String s) {
	     return ip.relatedtags(s);
	    }
	 
	 @GetMapping("/clean/{idp}")
		@ResponseBody
	    public String clean(@PathVariable("idp") int idp) throws IOException {
	     return ip.cleanContent(idp);
	    }
	 
	 @GetMapping("/related/{idp}/{idr}")
		@ResponseBody
	    public int related(@PathVariable("idp") int idp,@PathVariable("idr") int idr) throws IOException, JWNLException {
	     return ip.relatedContent(idp,idr);
	    }
	 
	 
	 
	 @GetMapping("/relatedP/{idp}")
		@ResponseBody
	    public List<Integer> relatedPost(@PathVariable("idp") int idp) throws IOException, JWNLException {
		 
	     return ip.relatedposts(idp);
	    }
	 
	 
	 
	 //TRANSLATION
	 @GetMapping("/translates/{idp}/{s}")
		@ResponseBody
	    public String translates(@PathVariable("idp") int idp,@PathVariable("s") String s) throws Exception {
		
	     return ip.TranslateString(idp,s);
	    }
	 
	 @GetMapping("/translate/{idp}/{s}")
		@ResponseBody
	    public Post translate(@PathVariable("idp") int idp,@PathVariable("s") String s) throws Exception {
		
	     return ip.TranslatePost(idp,s);
	     
	    }
	
	    
	 
	
	 //GEO
	 @GetMapping("/countries/{s}")
		@ResponseBody
	    public Map<String, Double> countries(@PathVariable("s") String s) throws IOException {
		
	  
return ip.findCities(s);
	   
	    }
	 
	 @GetMapping("/cities/{range}/{city}")
		@ResponseBody
	    public Map<String, Double> countriess(@PathVariable("range") Double range,@PathVariable("city") String city) throws IOException {
		
	
return ip.findCityRange(range, city);
	   
	    }
    
	 /*
	 @GetMapping("/pSearch/{search}")
		@ResponseBody
	    public List<Post> countriess(@PathVariable("search") String search) throws Exception {
		
	  
//return ip.searchPosts(search);
	   
	    } */
	 
	 
	 @GetMapping("/advancedSearch/{search}")
		@ResponseBody
	    public Map<Post, Double> advSearch(@PathVariable("search") String search) throws Exception {
		
	  
return ip.advancedSearch(search);
	   
	    } 
	 
	 
	 @GetMapping("/spos/{search}")
		@ResponseBody
	    public Map<Post, Double> advvSearch(@PathVariable("search") String search) throws Exception {
		
		Map<String, Double> cities = ip.findCities(search);
return ip.searchPosts(cities,"Tunisia");
	   
	    } 
	 
	 
	 
	 
	 @GetMapping("/google")
		@ResponseBody
	    public void News() throws Exception {

 ip.news();
	   
	    } 

	 @GetMapping("/news")
		@ResponseBody
	    public Map<String, String> BreakingNews() throws Exception {

return  ip. breakingNews();
	   
	    }  
	
	 
	 
	 
	 
	 
	 
		
	 
	 
}