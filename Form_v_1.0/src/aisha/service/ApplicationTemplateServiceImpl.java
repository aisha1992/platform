package aisha.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aisha.bean.ApplicationTemplate;
import aisha.bean.Talent;
import aisha.controller.ApplicationController;
import aisha.dao.ApplicationTemplateDAO;
import aisha.dao.TalentDAO;
import aisha.security.beans.SystemUser;

 
@Service
@Transactional
public class ApplicationTemplateServiceImpl implements ApplicationTemplateService {
 
@Autowired
private ApplicationTemplateDAO appDAO;
protected static Logger logger = Logger.getLogger(ApplicationTemplateServiceImpl.class);


@Override
public long addApplicationTemplate(ApplicationTemplate app) {
	logger.debug("Inside method ApplicationTemplateServiceImpl.addApplicationTemplate");
	return appDAO.addApplicationTemplate(app);
	
}


@Override
public ApplicationTemplate listApplicationTemplate(ApplicationTemplate app)
 {
	logger.debug("Inside method ApplicationTemplateServiceImpl.listApplicationTemplate");
	String x = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
	HashMap<String, Object> criteria = new HashMap<>();
	if (x != "anonymousUser")
	{
		SystemUser currentUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentUser.getUserRole().equals("Evaluator"))
		{
			
			criteria.put("appStatus", "Screened");
		}
		
	}
	app.setCriteria(criteria);
	ApplicationTemplate result = this.appDAO.listApplicationTemplates(app);
	List<ApplicationTemplate> resultList = new ArrayList<ApplicationTemplate>();
	resultList = result.getResults();

		result.setResults(resultList);
		return result;
	
}


@Override
public ApplicationTemplate getApplicationTemplate(ApplicationTemplate app) {
	logger.debug("Inside method ApplicationTemplateServiceImpl.getApplicationTemplate");
	return this.appDAO.getApplicationTemplate(app);
}


@Override
public void updateApplicationTemplate(ApplicationTemplate app) {
	logger.debug("Inside method ApplicationTemplateServiceImpl.updateApplicationTemplate");
int count = 0;


	if(app.getAppStatus() == "Screened")
	{
		logger.debug("Inside method ApplicationTemplateServiceImpl.updateApplicationTemplate, app status : " + app.getAppStatus());
for(int j = 0; j < app.getNoOfEvaluators() ; j++)
	{
		if(app.getEvaluation(j+1) != null)
		count = count +1;
	if(count == app.getNoOfEvaluators())	
		app.setAppStatus("Evaluated");

	}
	}
	logger.debug("Inside method ApplicationTemplateServiceImpl.updateApplicationTemplate, before calling appDAO.updateApplicationTemplat");
	this.appDAO.updateApplicationTemplate(app);
}
 
}