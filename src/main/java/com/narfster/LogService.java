package com.narfster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;

@Service
public class LogService {

	private static List<LogModel> logMessages = new ArrayList<LogModel>();

	static {

		logMessages.add(new LogModel("htmls headers 1", "Ta De Dam"));
		logMessages.add(new LogModel("htmls headers 2", "Ta De Dassaasasasm"));
	}
	
	public void addLogMessage(LogModel m) {
		logMessages.add(m);
	}

	public List<LogModel> getLogMessages() {
		return logMessages;
	}
	
	public List<LogModel> getLogMessagesNewest() {
		
		 List<LogModel> reverce = new ArrayList<LogModel>(logMessages);
		 Collections.reverse(reverce);
		 return reverce;
	}

}
