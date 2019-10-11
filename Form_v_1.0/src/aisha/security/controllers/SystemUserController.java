package aisha.security.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import aisha.bean.PlatformUser;
import aisha.bean.Talent;
import aisha.security.beans.SystemUser;
import aisha.security.services.SystemUserService;
import aisha.util.service.getFromXML;

@Controller
@RequestMapping(value="/SystemUserController")
public class SystemUserController {
	
	@Autowired
	private SystemUserService sysUserService; 
	
	protected SystemUser currentSystemUser;
	public void setCurrentBean(SystemUser user)
	{
		this.currentSystemUser = user;
		}
	
	public SystemUser getCurrentBean()
	{return this.currentSystemUser;}
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }
    
	@RequestMapping(value= "/addPlatformUser", method = RequestMethod.GET)
	public String addPlatformUser(Model model){
	
			model.addAttribute("addFields",  SystemUser.getTableFields());
			model.addAttribute("bean",  new PlatformUser());


	 	return "JobTemp_addStartup";
	 }

	
	 @RequestMapping(value= "/submitAddSystemUser", method = RequestMethod.POST)
	 public String submitAddSystemUser(HttpServletRequest request, @ModelAttribute("bean") PlatformUser sysUser, BindingResult result, Model model){

		 PlatformUser resultSysUser = new PlatformUser();


	 	
		 sysUserService.addSystemUser(sysUser);

	 	return "redirect:"+"addPlatformUser";
	 	}
	 
}
