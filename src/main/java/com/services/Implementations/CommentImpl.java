package com.services.Implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.entities.Comment;
import com.entities.Post;
import com.repositories.CommentRepository;
import com.repositories.PostRepository;
import com.services.Interfaces.IComment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentImpl implements IComment {

	public static int idstatic = 0;
	@Autowired
	CommentRepository cr;
	@Autowired
	PostRepository pr;

	@Override
	public void addComment(Comment c) throws IOException  {

		
	
	    
	    	  File file = new File("forum/spam"+ c.getPost().getId()+".txt");
	            if (file.createNewFile()) {
	                System.out.println("New Text File is created!");
	            } else {
	                System.out.println("File already exists.");
	            }
	   
		
	
			if (this.AntiSpam(c)) {

				System.out.println(c.getContent());
				System.out.println(c.getPost());

				if (c.getPost() != null)
					idstatic = c.getPost().getId();
				System.out.print("ITS NOT A SPAM");
				cr.save(c);
				
			}

			else {
				
			  

				
					FileWriter fileWriter = new FileWriter("forum/spam"+c.getPost().getId()+".txt", true);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					printWriter.println(c.getUser().getId() + " " + c.getContent() + " " + c.getDate()); // New
																									// line
					printWriter.close();
					System.out.println("FILE DONE!");
				System.out.println("ID="+c.getId());
				//this.deleteComment(c.getId());
			
			
			}
		
			
		}
	
	
	

	

	@Override
	public void updateComment(Comment c) {
		if (cr.findById(c.getId()).isPresent())
			cr.save(c);
		else
			System.out.println("doesnt exist");

	}

	/*
	 * Post p=pr.findById(c.getIdPost()).orElse(null);
	 * 
	 * 
	 * if(p.getComment().contains(c)) { p.addComment(c); pr.save(p); }
	 */

	@Override
	public void deleteComment(int idc) {

		cr.deleteById(idc);

		/*
		 * Comment c=cmr.getById(idc);
		 * 
		 * Post p=pr.findById(c.getIdPost()).orElse(null);
		 * p.getComment().remove(c); pr.save(p);
		 */
	}

	// USER !!!!

	@Override
	public List<Comment> comments() {
		return cr.findAll();

	}

	@Override
	public List<Comment> comments(Post p) {
		return cr.getAllComments(p);

	}

	@Override
	public List<Comment> commentsUser(long idu) {

		List<Comment> comments = new ArrayList<Comment>();
	/*	if (cr != null)
			for (Comment c : cr.findAll()) {
				System.out.println("22222222222");
				if (c.getUser().getId() == idu)
					comments.add(c);
			}
		System.out.println("33333333333");*/
		return comments;
	}

	@Override
	public List<Comment> commentsUserByDate(long idu) {
		List<Comment> comments = new ArrayList<Comment>();
		for (Comment c : cr.getAllComments()) {
			if (c.getUser().getId() == idu)
				comments.add(c);
		}
		return comments;
	}

	// POST !!!

	@Override
	public Set<Comment> commentsPost(int idp) {
	Post p=pr.findById(idp).orElse(null);
	return p.getComment();
	}

	@Override
	public Set<Comment> commentsPostbyDate(int idp) {
		
		Set<Comment> sorted = new TreeSet<Comment>(new Comparator<Comment>() {
			public int compare(Comment c1, Comment c2) {
				return c1.getDate().compareTo(c2.getDate());
			}});
		
		Set<Comment> comments = commentsPost(idp);
		sorted=comments;

		/*Collections.sort(comments, new Comparator<Comment>() {
			public int compare(Comment c1, Comment c2) {
				return c1.getDate().compareTo(c2.getDate());
			}
		});*/
		return sorted;

	}

	@Override
	public Set<Comment> commentsPostByInteraction(int idp) {
		Set<Comment> sorted = new TreeSet<Comment>(new Comparator<Comment>() {
			public int compare(Comment c1, Comment c2) {
			return Integer.compare(c1.getUp(), c2.getUp());
			}});
		Set<Comment> comments = commentsPost(idp);
		sorted=comments;
		return sorted;

		/*Collections.sort(comments, new Comparator<Comment>() {
			public int compare(Comment c1, Comment c2) {
				return c1.getDate().compareTo(c2.getDate());
			}
		});*/
	}

	@Override
	public void commentUp(int idc) {
		Comment c = cr.getById(idc);
		idstatic = c.getPost().getId();
		c.setUp(c.getUp() + 1);
		cr.save(c);

	}

	@Override
	public void commentDown(int idc) {
		Comment c = cr.getById(idc);
		c.setUp(c.getUp() - 1);
		cr.save(c);
	}

	public boolean AntiSpam(Comment c) throws IOException {
		int p = 0;
		int links = 0;
		int userComments = 0;
		// Links
		String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		Matcher urlMatcher = pattern.matcher(c.getContent());

		while (urlMatcher.find()) {
			links++;
		}
		if (links <= 1)
			p += 2;
		else
			p--;

		System.out.println("P1=" + p);
		// Content length
		if (c.getContent().length() < 20 && links < 1)
			p ++;
		else
			p--;

		System.out.println("P2=" + p);
		// Nb of comments
		if (c.getUser() != null)
			for (Comment comment : commentsUser(c.getUser().getId())) {

				if (comment.getPost().getId() == c.getPost().getId()) {
					p++;
				}

			}

		System.out.println("P3=" + p);
		// parcours fichier
		if (c.getPost() != null) {
			File spamF = new File("forum/spam" + c.getPost().getId() + ".txt");
			String[] wordss = null;

			FileReader frr = new FileReader(spamF);
			BufferedReader brr = new BufferedReader(frr);
			String strr;

			while ((strr = brr.readLine()) != null) {

				wordss = strr.split(" ");
				System.out.println("words[0=>"+wordss[0]+"\n");
				System.out.println("c.getUser().getId())="+c.getUser().getId());
				// Search for the word
				if (wordss[0].equals(Long.toString(c.getUser().getId()))) {
					System.out.print("Spam exists");
					p--;
				}

			
			}

		}
		System.out.println("P4=" + p);
		// Keywords
		File file = new File("forum/badWords.txt");
		String[] words = null;
		String[] bads = null;
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str;

		while ((str = br.readLine()) != null) {

			words = str.split("\n");
			bads = c.getContent().split(" ");
			for (String bad : bads) {
				for (String word : words) {
					// Search for the word
					if (word.equals(bad)) {
						System.out.print("BAD=" + bad);
						p=-2;
					}
				}
			}
		}
		System.out.println("P5=" + p);
		System.out.println("c.getPost()=" + c.getPost().getId());
		// BodyUsed

		Post post=pr.findById(c.getPost().getId()).orElse(null);
		for (Comment comment : post.getComment()) {
		
		if (comment.getContent().equals(c.getContent()) && comment.getId() != c.getId())
			p--;
	}
		System.out.println("P6=" + p);
		// random words
		String cons = "bcdfghjklmnpqrstvwxz";
		char[] consonants = cons.toCharArray();
		int z = 0;
		char[] ch = c.getContent().toLowerCase().toCharArray();
		for (int i = 0; i < c.getContent().length(); i++) {
			boolean isConsonant = false;
			for (int j = 0; j < 20; j++) {
				if (ch[i] == consonants[j]) {
					isConsonant = true;
					break;
				}
			}
			if (isConsonant) {
				z++;
				if (z == 5)
					break;
			} else {
				z = 0;
			}
		}
		if (z == 5)
			p--;
		else if (z == 0)
			p++;

		System.out.println("P7=" + p);

		if (p >= 1)
			return true;
		else

			return false;

	}

}
