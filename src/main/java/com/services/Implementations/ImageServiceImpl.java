package com.services.Implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Image;
import com.entities.Profile;
import com.repositories.ImageRepository;
import com.repositories.ProfileRepository;

@Service
public class ImageServiceImpl {
	
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	ProfileRepository profileRepository;
	
	public int addImage(Image image) {
		imageRepository.save(image);
		return image.getId();
	}
	
	public int affectImageToProfile(int idImage, int idProfile) {
		List<Image> listImage =new ArrayList<>();
		Image image =imageRepository.findById(idImage).get();
		Profile profile = profileRepository.findById(idProfile).get();
		if(profile.getPicture()!=null)
			listImage=profile.getPicture();
		listImage.add(image);
		profile.setPicture(listImage);
		profileRepository.save(profile);
		return 0;
	}
	
	
}
