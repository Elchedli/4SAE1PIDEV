package com.services.Interfaces;

import java.util.List;

import com.entities.Profile;


public interface IProfile {
	public void ajouterProfile(Profile profile,String siteURL);
	public void sendVerificationEmail(Profile profile, String siteURL);
	public boolean isEnabled();
	public boolean isEmailUnique(String email);
	public Profile getProfileByEmail(String email);
	List<Profile> retriveAllProfiles() ;
	
	Profile updateProfile(Profile profile);
	Profile updateProfilePhoto(Profile profile);
	Profile retriveProfileById(Long id);
	Profile retriveProfileByName(String nomProfile);
	void deleteProfile(Long id, String siteURL);
	void sendConfirmationEmail(Profile profile, String siteURL);
	
}
