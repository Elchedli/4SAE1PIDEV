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
import pi.utils.TimeUtils;

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
		List<Ad> datas = RepoAd.listerPubRegion(region, typepub);
		datas.forEach(element -> {
			element.setPublishedago(ConversionTime(element.getDateAd()));
		});
		return datas;
	}

	@Override
	public boolean DeleteAd(int idPub) {
		Ad ad = RepoAd.findById(idPub).orElse(null);
		RepoAd.delete(ad);
		return true;
	}

	@Override
	public String ConversionTime(Date dateAd) {
		long millis = dateAd.getTime();
		String relativeDate = String.valueOf(TimeUtils.getRelativeTime(millis));
		System.out.println("date is : " + relativeDate);
		return relativeDate;
	}

}
