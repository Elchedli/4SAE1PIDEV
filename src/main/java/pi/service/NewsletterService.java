package pi.service;

import java.util.Date;
import java.util.List;

import pi.entity.Newsletter;
import pi.enums.ArticleRegion;
import pi.enums.Countries;

public interface NewsletterService {
	boolean AddNewsletter(Newsletter news);
	List<Newsletter> NewsAll();
	List<Newsletter> NewsAllRegion(ArticleRegion surface,Countries countries);
	String ConversionTime(Date dateNews);
	List<Newsletter> ViewNumberNews();
}
