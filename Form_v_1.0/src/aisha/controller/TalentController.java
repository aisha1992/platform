package aisha.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import aisha.bean.Talent;
import aisha.security.beans.SystemUser;
import aisha.security.services.SystemUserService;
import aisha.service.TalentService;
import aisha.util.service.getFromXML;

@Controller
@RequestMapping(value="/TalentController")
public class TalentController {
	
	protected static Logger logger = Logger.getLogger(TalentController.class);
	
	@Autowired
	private TalentService talentService; 
	@Autowired
	private SystemUserService userService;
	 @RequestMapping(value = "/addTalent", method = RequestMethod.GET)
	 public String addTalent(Model model) {
		logger.debug("Entering method TalentController.addTalent");
		try {
			model.addAttribute("xmlFields",  getFromXML.getFormFields("Talent"));
			model.addAttribute("bean",  new Talent());
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

	 	return "JobTemp_addTalent";
	 }
	 
		@RequestMapping(value= "/submitAddTalent", method = RequestMethod.POST)
		 public String submitAddTalent(HttpServletRequest request, @ModelAttribute("bean") Talent talent, BindingResult result, Model model) throws IOException{
			//@RequestParam("uploadedFileName") MultipartFile multipart, , consumes = {"multipart/form-data"}
			/*String dateString = request.getParameter("dateOfBirth");
			String declaration = request.getParameter("declaration");
			Date dateOfBirth = null;
			try {
				 dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			talent.setDateOfBirth(dateOfBirth);
			if(declaration!=null)
			talent.setDeclaration(declaration);
			else
		    talent.setDeclaration("No");*/

			Long talentId = talentService.addTalent(talent);
			SystemUser sysUser = new SystemUser();
			sysUser.setUserName(talent.getField1());
			sysUser.setUserRole("Talent");
			sysUser.setUserKey(talentId.toString());
			//userService.addSystemUser(sysUser);
			logger.debug("Entering method TalentController.submitAddTalent");
			
			/*if(multipart.getSize() != 0)
			{
				String fileName = multipart.getOriginalFilename();
			
			 byte[] bytes = multipart.getBytes();
	         Path path = Paths.get("//home//CVs//" + talent.getFullName() + ".docx");
	         Files.write(path, bytes);

			talent.setFilePath(fileName);
			}*/
			
			logger.debug("Inside method TalentController.submitAddTalent, before add bean : " + talent);
			
			model.addAttribute("message1", "Your profile has been created successfully");
			model.addAttribute("message2","You will recieve an email soon containing your crednetials");
		logger.debug("Exiting method TalentController.submitAddTalent");
		return "JobTemp_submissionCompleted";

		 	}

		@RequestMapping(value= "/submitUpdateTalent", method = RequestMethod.POST)
		 public String submitUpdateTalent(HttpServletRequest request, @ModelAttribute("bean") Talent talent, BindingResult result, Model model) throws IOException{
			//@RequestParam("uploadedFileName") MultipartFile multipart, , consumes = {"multipart/form-data"}
			/*String dateString = request.getParameter("dateOfBirth");
			String declaration = request.getParameter("declaration");
			Date dateOfBirth = null;
			try {
				 dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			talent.setDateOfBirth(dateOfBirth);
			if(declaration!=null)
			talent.setDeclaration(declaration);
			else
		    talent.setDeclaration("No");*/
			talent.setId(6848512);
			talentService.updateTalent(talent);
			logger.debug("Entering method TalentController.submitAddTalent");
			
			/*if(multipart.getSize() != 0)
			{
				String fileName = multipart.getOriginalFilename();
			
			 byte[] bytes = multipart.getBytes();
	         Path path = Paths.get("//home//CVs//" + talent.getFullName() + ".docx");
	         Files.write(path, bytes);

			talent.setFilePath(fileName);
			}*/
			
			logger.debug("Inside method TalentController.submitAddTalent, before add bean : " + talent);
			
			model.addAttribute("message1", "Your profile has been updated successfully");
			
		logger.debug("Exiting method TalentController.submitAddTalent");
		return "JobTemp_submissionCompleted";

		 	}

		@RequestMapping(value = "/viewProfile", method = RequestMethod.GET)
		 public String viewProfile(Model model) throws ParserConfigurationException, SAXException, IOException {
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
			Talent result = talentService.getTalent(Integer.valueOf(userId));
		    model.addAttribute("xmlFields",  getFromXML.getFormFields("Talent"));
			model.addAttribute("bean",  result);
		    model.addAttribute("controllerName","TalentController");
		    logger.debug("Exiting method TalentController.viewProfile");
		 	return "JobTemp_viewProfile";
		 }
		 
	/* @RequestMapping(value = "/viewTalentApplications", method = RequestMethod.GET)
	 public String viewTalentApplications(HttpServletRequest request,Model model) throws ParserConfigurationException, SAXException, IOException {
		 logger.debug("Entering method TalentController.viewProfile");
		 
		 logger.debug("Inside method TalentController.viewProfile, before set talent bean : ");
		 int pageNumber = 1;

			if (request.getParameter("currentPage") != null) {
				int currentPageNumber = Integer.parseInt(request.getParameter("currentPage"));
				if (request.getParameter("left") != null)
					pageNumber = currentPageNumber - 1;
				else if (request.getParameter("right") != null)
					pageNumber = currentPageNumber + 1;
			}
			pageNumber--;

			Application app = new Application();
			app.setId(1);
			app.setFirstPage(pageNumber * 5);
			app.setMaxResult(5);
			Talent search = new Talent();
			HashMap<String, Object> criteria = new HashMap<>();
			HashMap<String, Object> dateFilter = new HashMap<>();

			ArrayList<String> searchFields = TalentApplications.getSearchFields();
			search.setSearchCriteria(criteria);
		 model.addAttribute("tableFields",  TalentApplications.getTableFields());
		 
		 model.addAttribute("xmlFields",  getFromXML.getFormFields("Talent"));
	    model.addAttribute("controllerName","TalentController");
	    Talent result = talentService.listBeansCustom(new Talent());
	    
	    List<Talent> appList = result.getResults();
		int totalCount = appList.size();
		logger.debug(
				"Inside method ApplicationController.getApplicationList , after retrieving applications from database. no of records:"
						+ totalCount);
		Integer nOfRecords = totalCount;
		Integer nOfPages = (totalCount) / 2;
		if (nOfPages == 0)
			nOfPages = 1;
		model.addAttribute("nOfRecords", nOfRecords);
		model.addAttribute("nOfPages", nOfPages);

		if (request.getParameter("currentPage") == null) {
			model.addAttribute("beanList", appList);
			model.addAttribute("currentPage", "1");
		} else {

			Integer currentPage;
			if (request.getParameter("left") == null)
				currentPage = new Integer(request.getParameter("currentPage")) + 1;
			else
				currentPage = new Integer(request.getParameter("currentPage")) - 1;
			model.addAttribute("beanList", appList);

			model.addAttribute("currentPage", currentPage.toString());
		}


		
	    model.addAttribute("beanList",result.getResults());
	     logger.debug("Exiting method TalentController.viewProfile");
	 	return "JobTemp_viewTalentApplications";
	 }
*/	
	 @RequestMapping(value="/getTalentList", method=RequestMethod.GET)
	 public String getTalentList(Model model) {
		 logger.debug("Entering method TalentController.getTalentList");
		 Talent talent = new Talent();
		 Talent resultTalent = new Talent();
		 List<Talent> beanList = new ArrayList<Talent>();
		 //talent.setFirstResults(10);
		// talent.setMaxResults(10);
		 
		 talent.setOrderBy("fullName");	 
		 model.addAttribute("controllerName","TalentController");
		// model.addAttribute("tableFields",Talent.getTableFields());
		// logger.debug("Inside method TalentController.getTalentList, before list talents with criteria : " + talent.getFilter());
		// resultTalent = talentService.listTalents(talent);
		// beanList =   resultTalent.getTalentList();
		 model.addAttribute("beanList", beanList);
		 logger.debug("Exiting method TalentController.getTalentList");
		 return "JobTemp_getTalentList";
	
	 }
	 
	 
	 @RequestMapping(value="/getTalent", method=RequestMethod.GET)
	 public String getTalent(Model model,@RequestParam("id") Integer id) {
		 logger.debug("Entering method TalentController.getTalent");
	   model.addAttribute("beanName","Talent");
	   model.addAttribute("controllerName","TalentController");
	   Talent talent = new Talent();
	   talent = talentService.getTalent(id);
	   model.addAttribute("bean", talent);
	 //  model.addAttribute("viewFields", Talent.getAddFields());
	   logger.debug("Exiting method TalentController.getTalent");
	   return "JobTemp_getTalent";
	 }
 

	 @RequestMapping(value = "/viewTalentDetailsForm", method = RequestMethod.GET)
	 public String viewTalentApplication(Model model) throws ParserConfigurationException, SAXException, IOException {
		 logger.debug("Entering method TalentController.viewTalentDetailsForm");
		 Talent t = new Talent();
		 logger.debug("Inside method TalentController.viewTalentDetailsForm, before set talent bean : " + t);
	 
	 	model.addAttribute("emptyBean", new Talent());
	// 	model.addAttribute("xmlFields",  getFromXML.getFormFields(1));
	 	model.addAttribute("bean", t);
	 	model.addAttribute("searchBean", new Talent());
	 	model.addAttribute("beanName","Talent");
	   // model.addAttribute("addFields", Talent.getAddFields());
	    model.addAttribute("controllerName","TalentController");
	     logger.debug("Exiting method TalentController.viewTalentDetailsForm");
	 	return "talentDetailsPage";
	 }

		

	@InitBinder
	public void initBinder(final WebDataBinder binder){
	  final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	  binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
