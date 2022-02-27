package pi.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		message.setDiscussion(disc);
		message.setContenu_msg(messagecontent);
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse("2/24/2022");
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		message.setDatetemps_msg(date1);
		RepoMessa.save(message);
		return messagecontent;
	}

	@Override
	public boolean filterMessage(String mess) {
//		EnumUtils.isValidEnum(MotInterdits.class, mess);
		return true;
	}



}
