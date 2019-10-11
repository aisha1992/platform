package aisha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aisha.bean.Application;
import aisha.bean.TalentOld;
import aisha.dao.ApplicationDAO;
import aisha.dao.TalentDAO;

 
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
 
@Autowired
 private ApplicationDAO appDAO;
 


@Override
public void addApplication(Application app) {
	appDAO.addApplication(app);
	
}


@Override
public List<Application> listApplications(Application app)
 {
	
		return this.appDAO.listApplications(app);
	
}


@Override
public Application getApplication(int id) {
	// TODO Auto-generated method stub
	return this.appDAO.getApplication(id);
}


@Override
public void updateApplication(Application app) {
	// TODO Auto-generated method stub
	this.appDAO.updateApplication(app);
}
 
}