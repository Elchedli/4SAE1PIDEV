package pi.implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.repository.MessaRepository;
import pi.service.MessaService;
@Service
public class MessaServiceImp implements MessaService {

	@Autowired
	MessaRepository Repo;
	
}
