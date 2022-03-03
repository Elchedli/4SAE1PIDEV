package pi.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.entity.Discussion;
import pi.entity.Messa;
import pi.enums.LastSender;
import pi.repository.DiscussionRepository;
import pi.repository.MessaRepository;
import pi.service.MessaService;

@Service
public class MessaServiceImp implements MessaService {
	@Autowired
	MessaRepository RepoMessa;
	@Autowired
	DiscussionRepository RepoDisc;

	@Override
	public String SendMessage(String refdisc, String sender, String messagecontent) {
		System.out.println("refdisc : "+refdisc+" sender : "+sender+" message : "+messagecontent);
		Discussion discMessages = RepoDisc.findById(refdisc).orElse(null);
		String[] recepient = refdisc.split("_");
		boolean side = recepient[0].compareTo(sender) == 0;
		discMessages.setLastSender(side ? LastSender.Destinataire : LastSender.Source);
		discMessages.setVue_disc(false);
		Messa message = new Messa();
//		if(messagecontent)
		message.setContenu_msg(messagecontent);
		message.setSender(side ? recepient[0] : recepient[1]);
		if (discMessages.getMessages() == null) {
			List<Messa> messages = new ArrayList<Messa>();
			messages.add(message);
			discMessages.setMessages(messages);
		} else
			discMessages.getMessages().add(message);
		RepoDisc.save(discMessages);
		return messagecontent;
	}

	@Override
	public boolean filterMessage(String mess) {
		// EnumUtils.isValidEnum(MotInterdits.class, mess);
		return true;
	}

}
