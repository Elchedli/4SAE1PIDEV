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
@Service
public class NewsletterServiceImp implements NewsletterService {
	
	@Autowired
	NewsletterRepository RepoNews;
	
	@Override
	public boolean AddNewsletter(Newsletter news) {
		RepoNews.save(news);
		return true;
	}

	@Override
	public List<Newsletter> NewsAll() {
		return (List<Newsletter>) RepoNews.findAll();
	}

	@Override
	public List<Newsletter> NewsAllRegion(ArticleRegion surface, Countries countries) {
		return RepoNews.listerNewsRegions(surface, countries);
	}

	@Override
	public String ConversionTime(Date dateNews) {
		return "hello";
	}

	@Override
	public List<Newsletter> ViewNumberNews(){
		//Angular method need cookie account
		return RepoNews.listerNewsViews();
	}


	
}
