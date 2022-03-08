package com.services.Interfaces;

import java.util.List;

import com.entities.Tag;

public interface ITag {
	public void addTag(Tag t);
	public void updateTag(Tag t);

	public List<Tag> allTags();
	public int countTags(Tag t);
	//public List<Post> findTagPosts(Tag t);
	//public void deleteTag(int tag);
	public void deleteTag(String tag);


}
