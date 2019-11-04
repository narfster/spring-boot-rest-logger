package com.narfster;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogController {

	@Autowired
	LogService logService;

	@RequestMapping(value = "/api/log2", method = { RequestMethod.POST })
	public ResponseEntity<String> log(@RequestHeader Map<String, String> headers, HttpServletRequest request,
			@RequestBody String s) {

		// Get all HTML headers
		String res = new String();
		res += String.format("'%s' = %s\r\n", "protocol", request.getProtocol());
		res += String.format("'%s' = %s\r\n", "path", request.getRequestURI());
		res += String.format("'%s' = %s\r\n", "method", request.getMethod());
		res += String.format("'%s' = %s\r\n", "content-type", request.getContentType());

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			res += String.format("'%s' = %s\r\n", entry.getKey(), entry.getValue());
		}

		// Get current date time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String time = dtf.format(now);
		
		logService.addLogMessage(new LogModel(time, res, s));
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/blog2", method = { RequestMethod.POST })
	public ResponseEntity<String> binary_log(@RequestHeader Map<String, String> headers, HttpServletRequest request,
			@RequestBody byte[] byteArr ) {

		String s = bytesToHex(byteArr);
		String res = new String("");
		
		// debug
		System.out.println(s);

		// Get current date time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String time = dtf.format(now);
		
		logService.addLogMessage(new LogModel(time, res, s));
		
		
		return new ResponseEntity<String>(HttpStatus.OK);
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
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2 + bytes.length ];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        
	        hexChars[j * 3] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
	        
	        // add whitespace
	        hexChars[j * 3 + 2] = ' ';
	        
	    }
	    return new String(hexChars);
	}
	

}