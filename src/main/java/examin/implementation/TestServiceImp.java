package examin.implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import examin.repository.TestRepository;
import examin.service.TestService;
@Service
public class TestServiceImp implements sService {

	@Autowired
	TestRepository Repo;
	
}
