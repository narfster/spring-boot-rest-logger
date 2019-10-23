package com.narfster;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogController {

	@RequestMapping(value = "/log", method = { RequestMethod.GET, RequestMethod.POST })
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "Log";
	}

	@RequestMapping(value = "/log2", method = { /*RequestMethod.GET,*/ RequestMethod.POST })
	public String log(Model model, @RequestHeader Map<String, String> headers, HttpServletRequest request, @RequestBody String s) {


		// Get all HTML headers
		String res = new String();
		res += String.format("'%s' = %s\r\n", "protocol", request.getProtocol());
		res += String.format("'%s' = %s\r\n", "path", request.getRequestURI());
		res += String.format("'%s' = %s\r\n", "method", request.getMethod());

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			res += String.format("'%s' = %s\r\n", entry.getKey(), entry.getValue());
		}
		
		
		LogModel m = new LogModel(res,s);
		model.addAttribute("msgInfo", m.getMsgInfo());
		model.addAttribute("htmlInfo", m.getHtmlInfo());
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