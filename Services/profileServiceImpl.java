package tn.esprit.main.Services;

import java.util.List;

import org.hibernate.query.criteria.internal.expression.function.LengthFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.main.Entities.Profile;
import tn.esprit.main.Repositories.profileRepository;

@Service
public class profileServiceImpl implements profileService {

	@Autowired
	profileRepository pr;
	@Override
	public void ajouterProfile(Profile profile) {
		pr.save(profile);
		
	}

	@Override
	public List<Profile> retriveAllProfiles() {
		List<Profile> listProfile= (List<Profile>) pr.findAll();
		
		
		return listProfile;
	}

	@Override
	public void deleteProfile(int id) {
		pr.deleteById(id);;
	}

	@Override
	public Profile updateProfile(Profile profile) {
		pr.save(profile);
		return profile;
	}

	@Override
	public Profile updateProfilePhoto(Profile profile) {
		pr.save(profile);
		return profile;
	}


	@Override
	public Profile retriveProfileByName(String nomProfile) {
		
		return null;
	}

	@Override
	public Profile retriveProfileById(int id) {
		// TODO Auto-generated method stub
		Profile p = pr.findById(id).get();
		return p;
	}

	

}
