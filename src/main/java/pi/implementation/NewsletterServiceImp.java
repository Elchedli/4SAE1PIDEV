package pi.implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.repository.NewsletterRepository;
import pi.service.NewsletterService;
@Service
public class NewsletterServiceImp implements NewsletterService {

	@Autowired
	NewsletterRepository RepoNews;
	
}
