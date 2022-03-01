package pi.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.entity.Discussion;
import pi.entity.Messa;
import pi.repository.MessaRepository;
import pi.service.MessaService;

@Service
public class MessaServiceImp implements MessaService {
	 @Autowired
	 MessaRepository RepoMessa;
	 
	@Override
	public String SendMessage(Discussion disc, String sender,String messagecontent) {
		Messa message = new Messa();
		message.setContenu_msg(messagecontent);
		disc.getMessages().add(message);
		RepoMessa.save(message);
		return messagecontent;
	}

	@Override
	public boolean filterMessage(String mess) {
//		EnumUtils.isValidEnum(MotInterdits.class, mess);
		return true;
	}



}
