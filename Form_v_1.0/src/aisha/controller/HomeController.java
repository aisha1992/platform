package aisha.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import aisha.security.beans.SystemUser;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Model model) throws ParserConfigurationException, SAXException, IOException {
		model.addAttribute("controllerName", "ApplicationController");
		String x = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		SystemUser user = new SystemUser();
		if (x != "anonymousUser")
		 {
			user = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("role", user.getUserRole());
			model.addAttribute("userName","shishi");
			
		 }

		else
			model.addAttribute("role", "Guest");

		return "JobTemp_homePage";

	}

	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	public String loginError(Model model) throws ParserConfigurationException, SAXException, IOException {
		model.addAttribute("error", "Wrong User Name or Password, Please try again ");
		return "JobTemp_login";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() throws ParserConfigurationException, SAXException, IOException {
		return "JobTemp_login";
	}

}
