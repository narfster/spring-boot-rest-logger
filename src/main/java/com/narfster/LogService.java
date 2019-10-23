package com.narfster;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		
		//current time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String time = dtf.format(now);
		
		logMessages.add(new LogModel(time,"html headers 1", "Ta De Dam"));
		logMessages.add(new LogModel(time,"htmls headers 2", "Ta De Dassaasasasm"));
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
