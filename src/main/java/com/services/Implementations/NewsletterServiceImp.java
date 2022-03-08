package com.services.Implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.repositories.NewsletterRepository;
import com.services.Interfaces.NewsletterService;
import com.Utils.AcceuilUtils;
import com.Utils.TimeUtils;
import com.entities.Newsletter;
import com.entities.enums.ArticleRegion;

@Service
public class NewsletterServiceImp implements NewsletterService {

	@Autowired
	NewsletterRepository RepoNews;

	@Override
	public boolean AddNewsletter(Newsletter news){
		// System.out.println("news est : " + news);
		String[] geo = null;
		switch (news.getArticleRegion()) {
		case Nationnal:
			try {
				geo = AcceuilUtils.geoData();
			} catch (IOException | GeoIp2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (geo[0] == null)
				return false;
			news.setCountry(geo[0]);
			break;
		case Regionnal:
			try {
				geo = AcceuilUtils.geoData();
			} catch (IOException | GeoIp2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (geo[1] == null)
				return false;
			news.setCountry(geo[0]);
			news.setCity(geo[1]);
			break;
		default:
			break;
		}
		RepoNews.save(news);
		return true;
	}

	// @Override
	// public List<Newsletter> NewsAll() {
	// List<Newsletter> datas = (List<Newsletter>) RepoNews.findAll();
	// datas.forEach(element -> {
	// element.setPublishedago(ConversionTime(element.getDatenews()));
	// });
	// return datas;
	// }

	@Override
	public List<Newsletter> NewsAllRegion(ArticleRegion articleRegion) throws IOException, GeoIp2Exception{
		List<Newsletter> datas = new ArrayList<>();
		String[] geo = null;
		switch (articleRegion) {
		case International:
			datas = RepoNews.listerNewsInt(ArticleRegion.International);
			break;
		case Nationnal:
			geo = AcceuilUtils.geoData();
			if (geo[0] != null)
				datas = RepoNews.listerNewsCountry(ArticleRegion.Nationnal,geo[0]);
			break;
		case Regionnal:
			geo = AcceuilUtils.geoData();
			if (geo[1] != null)
				datas = RepoNews.listerNewsCity(ArticleRegion.Regionnal,geo[0], geo[1]);
			break;
		default:
			datas = RepoNews.listerNewsInt(ArticleRegion.International);
			break;
		}

		datas.forEach(element -> {
			element.setPublishedago(ConversionTime(element.getDatenews()));
		});

		return datas;
	}

	@Override
	public String ConversionTime(Date dateNews) {
		long millis = dateNews.getTime();
		String relativeDate = String.valueOf(TimeUtils.getRelativeTime(millis));
		System.out.println("date is : " + relativeDate);
		return relativeDate;
	}

	@Override
	public List<Newsletter> ViewNumberNews() {
		// Angular method need cookie account
		return RepoNews.listerNewsViews();
	}

}
