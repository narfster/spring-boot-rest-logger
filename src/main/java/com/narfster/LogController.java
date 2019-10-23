package com.narfster;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogController {

	@Autowired
	LogService logService;

	@RequestMapping(value = "/api/log2", method = { RequestMethod.POST })
	public String log(Model model, @RequestHeader Map<String, String> headers, HttpServletRequest request,
			@RequestBody String s) {

		// Get all HTML headers
		String res = new String();
		res += String.format("'%s' = %s\r\n", "protocol", request.getProtocol());
		res += String.format("'%s' = %s\r\n", "path", request.getRequestURI());
		res += String.format("'%s' = %s\r\n", "method", request.getMethod());

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			res += String.format("'%s' = %s\r\n", entry.getKey(), entry.getValue());
		}

		// Get current date time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String time = dtf.format(now);

		logService.addLogMessage(new LogModel(time,res, s));

		model.addAttribute("logMessages", logService.getLogMessages());
		return "Log";
	}

	/*
	 * Return view of Logs
	 */
	@RequestMapping(value = "/log2", method = { RequestMethod.GET })
	public String showLogs(Model model) {
		model.addAttribute("logMessages", logService.getLogMessagesNewest());
		return "Log";
	}

	/*
	 * print echo back the header of the http request
	 */
	@RequestMapping(value = "/listHeaders", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<String> listAllHeaders(@RequestHeader Map<String, String> headers,
			HttpServletRequest request) {

		String res = new String();

		res += String.format("'%s' = %s\r\n", "protocol", request.getProtocol());
		res += String.format("'%s' = %s\r\n", "path", request.getRequestURI());
		res += String.format("'%s' = %s\r\n", "method", request.getMethod());

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			res += String.format("'%s' = %s\r\n", entry.getKey(), entry.getValue());
		}

		return new ResponseEntity<String>(String.format(res, headers.size()), HttpStatus.OK);
	}

}