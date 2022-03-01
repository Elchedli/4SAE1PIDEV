package pi.implementation;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.entity.Newsletter;
import pi.enums.ArticleRegion;
import pi.enums.Countries;
import pi.repository.NewsletterRepository;
import pi.service.NewsletterService;
import pi.utils.TimeUtils;
@Service
public class NewsletterServiceImp implements NewsletterService {
	
	@Autowired
	NewsletterRepository RepoNews;
	
	@Override
	public boolean AddNewsletter(Newsletter news) {
		System.out.println("news est : "+news);
		RepoNews.save(news);
		return true;
	}

	@Override
	public List<Newsletter> NewsAll() {
		List<Newsletter> datas = (List<Newsletter>) RepoNews.findAll();
		datas.forEach(element -> {
			element.setPublishedago(ConversionTime(element.getDatenews()));
		});
		return datas;
	}

	@Override
	public List<Newsletter> NewsAllRegion(ArticleRegion surface, Countries countries) {
		List<Newsletter> datas = RepoNews.listerNewsRegions(surface, countries);
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
	public List<Newsletter> ViewNumberNews(){
		//Angular method need cookie account
		return RepoNews.listerNewsViews();
	}


	
}
