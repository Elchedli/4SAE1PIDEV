package com.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.entity.Ad;
import com.enums.PubRegion;
import com.enums.PubType;
import com.repository.AdRepository;
import com.service.AdService;
import com.utils.TimeUtils;

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
	public List<Ad> PubRegion(PubRegion region, PubType typepub,String country) {
		List<Ad> datas = new ArrayList<>();
		if(region == PubRegion.NATIONNAL) datas = RepoAd.listerPubRegion(typepub,region,country);
		else datas =  RepoAd.listerPubGlobal(typepub);
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

	@Override
	public List<Object[]> StatsAd() {
		List<Object[]> datas = RepoAd.listerStats();
		return datas;
	}
	
	@Scheduled(fixedDelay = 30000)
	public void TotalAds() {
		int datas = RepoAd.countAds();
		System.out.println("il y a "+datas+" publicitées");
	}
	
	

}
