package com.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.entity.Newsletter;
import com.enums.ArticleRegion;
import com.maxmind.geoip2.exception.GeoIp2Exception;

public interface NewsletterService {
	boolean AddNewsletter(Newsletter news);
//	List<Newsletter> NewsAll();
	List<Newsletter> NewsAllRegion(ArticleRegion art) throws IOException, GeoIp2Exception ;
	String ConversionTime(Date dateNews);
	List<Newsletter> ViewNumberNews();
}
