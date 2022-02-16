package examin.implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import examin.repository.TestRepository;
import examin.service.MessaService;
@Service
public class MessaServiceImp implements MessaService {

	@Autowired
	TestRepository Repo;
	
}
