package pi.implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.repository.DiscussionRepository;
import pi.service.DiscussionService;
@Service
public class DiscussionServiceImp implements DiscussionService {

	@Autowired
	DiscussionRepository Repo;
	
}
