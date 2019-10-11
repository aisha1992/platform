package aisha.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import aisha.bean.Application;
import aisha.bean.ApplicationTemplate;
import aisha.bean.BasicBean;
import aisha.bean.StartupProfile;
import aisha.bean.Talent;
import aisha.security.beans.SystemUser;
import aisha.service.ApplicationService;
import aisha.service.ApplicationTemplateService;
import aisha.service.StartupProfileService;
//import aisha.test.getFromXML;
import aisha.util.service.getFromXML;

@Controller
@RequestMapping(value = "/StartupProfileController")
public class StartupProfileController {
	protected StartupProfile currentProfile = new StartupProfile();

	protected static Logger logger = Logger.getLogger(StartupProfileController.class);

	public void setCurrentStartupProfile(StartupProfile currentProfile) {
		this.currentProfile = currentProfile;
	}

	public StartupProfile getCurrentStartupProfile() {
		return this.currentProfile;
	}

	@Autowired
	private StartupProfileService startupProfileService;


	@RequestMapping(value = "/getStartupProfiles", method = RequestMethod.GET)
	public String getStartupProfiles(Model model, HttpServletRequest request) {
		logger.debug("Entering method ApplicationController.getSubmittedApplications");
		String currentUser =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		//model.addAttribute("role", currentUser.getUserRole());
		int pageNumber = 1;
		if (request.getParameter("currentPage") != null) {
			int currentPageNumber = Integer.parseInt(request.getParameter("currentPage"));
			if (request.getParameter("left") != null)
				pageNumber = currentPageNumber - 1;
			else if (request.getParameter("right") != null)
				pageNumber = currentPageNumber + 1;
		}
		pageNumber--;

		StartupProfile profile = new StartupProfile();
		//profile.setId(1);
		profile.setFirstPage(pageNumber * 50);
		profile.setMaxResult(50);
		HashMap<String, Object> criteria = new HashMap<>();
		HashMap<String, Object> dateFilter = new HashMap<>();
		ArrayList<String> searchFields = ApplicationTemplate.getSearchFields();
		model.addAttribute("beanName", "StartupProfile");

		for (int i = 0; i < searchFields.size(); i++) {
			if (searchFields.get(i).equals("fromDate") || searchFields.get(i).equals("toDate")) {
				if (request.getParameter(searchFields.get(i)) != null
						&& !request.getParameter(searchFields.get(i)).isEmpty())

				{
					dateFilter.put(searchFields.get(i), request.getParameter(searchFields.get(i)));

				}
			}
			if (!searchFields.get(i).equals("fromDate") && !searchFields.get(i).equals("toDate")
					&& request.getParameter(searchFields.get(i)) != null)
				criteria.put(searchFields.get(i), request.getParameter(searchFields.get(i)));
			if (!dateFilter.isEmpty())
				criteria.put("submTime", dateFilter);
		}
		model.addAttribute("controllerName", "StartupProfileController");
		//profile.setSearchCriteria(criteria);
		logger.debug(
				"Inside method ApplicationController.getSubmittedApplications , before retrieving submitted applications from database");
		StartupProfile profileList = startupProfileService.listStartupProfiles(profile);
		List<BasicBean> resultList = profileList.getResults();
		int totalCount = profile.getTotalResult();
		logger.debug(
				"Inside method ApplicationController.getSubmittedApplications , after retrieving submitted applications from database, no of records : "
						+ totalCount);
		model.addAttribute("beanList", resultList);
		model.addAttribute("tableFields", StartupProfile.getTableFields());
		Integer nOfRecords = totalCount;
		Integer nOfPages = (totalCount + 4) / 5;
		if (nOfPages == 0)
			nOfPages = 1;
		model.addAttribute("nOfRecords", nOfRecords);
		model.addAttribute("nOfPages", nOfPages);

		if (request.getParameter("currentPage") == null) {
			model.addAttribute("beanList", resultList);
			model.addAttribute("currentPage", "1");
		} else {

			Integer currentPage;
			if (request.getParameter("left") == null)
				currentPage = new Integer(request.getParameter("currentPage")) + 1;
			else
				currentPage = new Integer(request.getParameter("currentPage")) - 1;
			model.addAttribute("beanList", resultList);

			model.addAttribute("currentPage", currentPage.toString());
		}
		logger.debug("Exiting method ApplicationController.getSubmittedApplications");
		return "JobTemp_getStartups";

	}


	@RequestMapping(value = "/getCurrentStartupProfile", method = RequestMethod.GET)
	public String getCurrentStartupProfile(@ModelAttribute("bean") StartupProfile profile, @RequestParam("id") String id, Model model)
			throws ParserConfigurationException, SAXException, IOException {
		logger.debug("Entering method StartupProfileController.getCurrentStartupProfile");
		model.addAttribute("tableFields", StartupProfile.getTableFields());
/*
		if (currentUser != null) {
			model.addAttribute("userRoleType", currentUser.getUserRole());
			String evaluator = currentUser.getUserType();
			if(currentUser.getUserRole().equals("AdminEvaluator"))
			{	model.addAttribute("adminEvaFields", ApplicationTemplate.getAllFields());}
			if (evaluator != null)
				model.addAttribute("evaFields", ApplicationTemplate.getEvaluatorFields(Integer.valueOf(evaluator)));
			model.addAttribute("interviewFields", ApplicationTemplate.getInterviewerFields());
		}*/
		logger.debug("Inside method StartupProfileController.getCurrentStartupProfile, before get bean with id: " + id);
		StartupProfile temp = startupProfileService.getStartupProfile(Integer.parseInt(id));
		currentProfile = temp;
		model.addAttribute("xmlFields", aisha.test.getFromXML.getFormFields(1));
		model.addAttribute("bean", temp);
		model.addAttribute("beanName", "ApplicationTemplate");
		logger.debug("Exiting method StartupProfileController.getCurrentStartupProfile");
		return "JobTemp_viewStartupProfile";
	}


	@RequestMapping(value = "/updateStartupProfile", method = RequestMethod.POST)
	public String updateStartupProfile(@ModelAttribute("bean") StartupProfile profile, Model model) {
		logger.debug("Entering method StartupProfileController.updateStartupProfile");
		startupProfileService.updateStartupProfile(currentProfile);
		model.addAttribute("message1", "We have successfully updated profile!");
		logger.debug("Exiting method StartupProfileController.updateStartupProfile");
		return "JobTemp_submissionCompleted";
	}

	 @RequestMapping(value = "/addStartup", method = RequestMethod.GET)
	 public String addStartup(@ModelAttribute("controllerName") String controllerName,Model model) {
		logger.debug("Entering method StartupController.addStartup");
	 	
	 	model.addAttribute("bean", new StartupProfile());
	 	model.addAttribute("beanName","StartupProfile");
	    model.addAttribute("addFields", StartupProfile.getAddFields());
	    logger.debug("Exiting method StartupProfileController.addStartup");
	 	return "JobTemp_addStartup";
	 }

	 @RequestMapping(value= "/submitAddStartup", method = RequestMethod.POST)
	 public String submitAddStartup(Model model,HttpServletRequest request, @ModelAttribute("bean") @Valid StartupProfile profile, BindingResult result) {

		/*String dateString = request.getParameter("startDate");
		Date startDate = null;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		//profile.setdate
		long startupID = startupProfileService.addStartupProfile(profile);

			model.addAttribute("bean", new StartupProfile());
			try {
				model.addAttribute("xmlFields", aisha.test.getFromXML.getFormFields(1));
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("searchBean", new StartupProfile());
			model.addAttribute("beanName", "StartupProfile");
			model.addAttribute("addFields", StartupProfile.getAddFields());
			model.addAttribute("controllerName", "StartupProfileController");
			logger.debug("Exiting method StartupProfileController.submitAddStartup");
			model.addAttribute("message1","We have successfully recieved your application, your application ID is :" + startupID);
				model.addAttribute("message2", "Please use it in any future corespondence");
			
				return "JobTemp_submissionCompleted";
		
	 	}

	 @RequestMapping(value = "/viewStartupProfile", method = RequestMethod.GET)
	 public String viewStartupProfile(Model model) throws ParserConfigurationException, SAXException, IOException {
		 logger.debug("Entering method TalentController.viewProfile");
		 
		 logger.debug("Inside method TalentController.viewProfile, before set talent bean : ");
		 String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		 String userId = null ;
			if (principal != "anonymousUser")
				{
				SystemUser currentUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				 userId = currentUser.getUserKey();
				 model.addAttribute("userName", currentUser.getUserName());
			
	}
			else
				model.addAttribute("role", "Guest");
		/*List<Field> f = getFromXML.getFormFields("Talent");
		Iterator<Field> itr = f.iterator();
		while(itr.hasNext())
		{Field field = itr.next();
		if(field.getfEnabledFor().contains("Evaluator")
				{}}
		*/ 
		StartupProfile profile = startupProfileService.getStartupProfile(Integer.valueOf(userId));
	    model.addAttribute("xmlFields",  getFromXML.getFormFields("Startup"));
		model.addAttribute("bean",  profile);
	    model.addAttribute("controllerName","profileController");
	    logger.debug("Exiting method profileController.viewProfile");
	 	return "JobTemp_viewStartupProfile";
	 }
	 

}
