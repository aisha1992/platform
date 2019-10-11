package aisha.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import aisha.bean.StartupProfile;
import aisha.dao.StartupProfileDAO;
import aisha.security.beans.SystemUser;

 
@Service
@Transactional
public class StartupProfileServiceImpl implements StartupProfileService {
 
@Autowired
 private StartupProfileDAO StartupProfileDAO;
protected static Logger logger = Logger.getLogger(StartupProfileServiceImpl.class);


@Override
public long addStartupProfile(StartupProfile profile) {
	logger.debug("Inside method StartupProfileServiceImpl.addStartupProfile, add bean : " + profile);
	// profile.setCreationTime(new Date());
     profile.setStatus("active");
     //SystemUser currentUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // profile.setCreatedBy(currentUser.getUserName());
	long result = StartupProfileDAO.addStartupProfile(profile);
	logger.debug("Inside method StartupProfileServiceImpl.addStartupProfile, after adding bean with id: " + result);
	return result;
	
}


@Override
public StartupProfile listStartupProfiles(StartupProfile profile)
 {
	logger.debug("Inside method StartupProfileServiceImpl.listStartupProfiles");
		StartupProfile result =  this.StartupProfileDAO.listStartupProfiles(profile);
		logger.debug("Inside method StartupProfileServiceImpl.listStartupProfiles, after listing beans : " + result.getResults());
		return result;
	
}


@Override
public StartupProfile getStartupProfile(int id) {
	logger.debug("Inside method StartupProfileServiceImpl.getStartupProfile");
	StartupProfile result = this.StartupProfileDAO.getStartupProfile(id);
	logger.debug("Inside method StartupProfileServiceImpl.getStartupProfile, after get StartupProfile : " + result);
	return result;
}


@Override
public void updateStartupProfile(StartupProfile profile) {
	logger.debug("Inside method StartupProfileServiceImpl.updateStartupProfile");
	//profile.setLastModificationTime(new Date());
    SystemUser currentUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //profile.setLastModifiedBy(currentUser.getUserName());
	this.StartupProfileDAO.updateStartupProfile(profile);
	logger.debug("Inside method StartupProfileServiceImpl.updateStartupProfile, after update StartupProfile : " + profile);
}
 
}