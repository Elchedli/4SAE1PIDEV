package com.services.Implementations;

import java.util.ArrayList;
import java.util.List;

import com.entities.Post;
import com.entities.Tag;
import com.repositories.PostRepository;
import com.repositories.TagRepository;
import com.services.Interfaces.ITag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TagImpl implements ITag {
	
	@Autowired
	TagRepository tr;
	@Autowired
	PostRepository pr;
	@Override
	public void addTag(Tag t){
		String eng=GoogleTTS_Translate.google_Translate("en",t.getTag());
		t.setTag(eng.toLowerCase());
		System.out.println("Tag="+t.getTag());
		tr.save(t);
	}

	@Override
	public void updateTag(Tag t) {
		if (tr.findById(t.getTag()).isPresent())
			tr.save(t);
		else
			System.out.println("doesnt exist");
		
	}



	@Override
	public List<Tag> allTags() {
		//System.out.println("Tag=");
		List<Tag> tags =  (List<Tag>) tr.findAll();
		System.out.println("tags : "+tags);
		return tags;
	}
	
	@Override
	public int countTags(Tag t){
	
		int count=0;
		System.out.println("TAG="+t.getTag());
		for(Post p:pr.findAll())
		{
			if(p.getTag().contains(t))
				count++;
		}
		System.out.println("COUNT"+count);
		return count;
	}
	
	


	@Override
	public void deleteTag(String tag) {
		// TODO Auto-generated method stub
		
	}

}
