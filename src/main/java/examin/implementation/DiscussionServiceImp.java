package examin.implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import examin.repository.TestRepository;
import examin.service.DiscussionService;
@Service
public class DiscussionServiceImp implements DiscussionService {

	@Autowired
	TestRepository Repo;
	
}
