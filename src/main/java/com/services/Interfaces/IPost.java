package com.services.Interfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import com.entities.Post;
import com.entities.Tag;

import net.sf.extjwnl.JWNLException;

public interface IPost {
public String addPost(Post p);
public void updatePost(Post p);
public void deletePost(int idp);
public List<Post> allPost();
public void addTag(int idp,String tag);




public void follow(int idp,int idu);
public void unfollow(int idp,int idu);
public void mailFollowers(Post p,String content);
public void validateComment(int idp,int idc);


public  Map<Integer, Post> topPosts(int days);
List<Integer> relatedposts(int p) throws IOException, JWNLException;




//test
public  List<String>  relatedtags(String t);

public String cleanContent(int p)  throws IOException;
public int relatedContent(int p, int rp) throws IOException, JWNLException;


public String TranslateString(int idp,String lang) throws Exception;
public Post TranslatePost(int idp,String lang) throws Exception;

//public void findByCity();

//GEO

public Map<String,Double> findCities(String country) throws IOException;
public Map<String, Double> findCities(String country, int index) throws IOException;
public Map<String,Double> findCityRange(double range,String city) throws IOException;


//ADVANCED SEARCH
public Map<Post, Double> searchPosts(Map<String, Double> cities, String country) throws IOException;
public Map<Post, Double> searchPosts(Map<String, Double> cities) throws IOException;
public Map<Post, Double> advancedSearch(String search) throws Exception;


//TAG
public List<Post> findTagPosts(Tag t);
void news() throws GeneralSecurityException, IOException;
Map<String, String> breakingNews();




//public List<Post> tagPosts(Tag t);


}