package pi.service;

import java.util.Date;
import java.util.List;

import pi.entity.Ad;
import pi.enums.PubRegion;
import pi.enums.PubType;

public interface AdService {
	boolean AddPubLetter(Ad ad);
	List<Ad> PubRegion(PubRegion region,PubType typepub);
	boolean DeleteAd(int idPub);
	String ConversionTime(Date dateNews);
}
