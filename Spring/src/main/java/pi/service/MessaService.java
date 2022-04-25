package pi.service;

import pi.entity.Discussion;

public interface MessaService {
	String SendMessage(Discussion disc,String sender,String messagecontent);
	boolean filterMessage(String mess);
}
