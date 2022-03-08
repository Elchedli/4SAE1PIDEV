package com.services.Interfaces;

import java.util.Date;
import java.util.List;

import com.entities.Ad;
import com.entities.enums.PubRegion;
import com.entities.enums.PubType;

public interface AdService {
	boolean AddPubLetter(Ad ad);
	List<Ad> PubRegion(PubRegion region,PubType typepub,String contry);
	List<Object[]> StatsAd(); 
	boolean DeleteAd(int idPub);
	String ConversionTime(Date dateNews);
}
