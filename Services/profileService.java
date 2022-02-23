package tn.esprit.main.Services;

import java.util.List;

import tn.esprit.main.Entities.Profile;

public interface profileService {
	public void ajouterProfile(Profile profile);
	List<Profile> retriveAllProfiles() ;
	void deleteProfile(int id);
	Profile updateProfile(Profile profile);
	Profile updateProfilePhoto(Profile profile);
	Profile retriveProfileById(int id);
	Profile retriveProfileByName(String nomProfile);
	

}
