package com.service;

import java.util.Date;
import java.util.List;

import com.entity.Ad;
import com.enums.PubRegion;
import com.enums.PubType;

public interface AdService {
	boolean AddPubLetter(Ad ad);
	List<Ad> PubRegion(PubRegion region,PubType typepub,String contry);
	List<Object[]> StatsAd(); 
	boolean DeleteAd(int idPub);
	String ConversionTime(Date dateNews);
}
