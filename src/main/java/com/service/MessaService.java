package com.service;

public interface MessaService {
//	String SendMessage(Discussion disc,String sender,String messagecontent);
	String SendMessage(String refdisc, String sender, String messagecontent);
	boolean filterMessage(String mess);
}
