package pi.implementation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.entity.Ad;
import pi.enums.PubRegion;
import pi.enums.PubType;
import pi.repository.AdRepository;
import pi.service.AdService;

@Service
public class AdServiceImp implements AdService {

	@Autowired
	AdRepository RepoAd;

	@Override
	public boolean AddPubLetter(Ad ad) {
		RepoAd.save(ad);
		return true;
	}

	@Override
	public List<Ad> PubRegion(PubRegion region, PubType typepub) {
		return RepoAd.listerPubRegion(region, typepub);
	}

	@Override
	public boolean DeleteAd(int idPub) {
		Ad ad = RepoAd.findById(idPub).orElse(null);
		RepoAd.delete(ad);
		return true;
	}

	@Override
	public String ConversionTime(Date dateNews) {
		// need to work
		return "hallo";
	}

}
