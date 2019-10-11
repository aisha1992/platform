package aisha.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import aisha.bean.Talent;
import aisha.dao.TalentDAO;

 
@Service
@Transactional
public class TalentServiceImpl implements TalentService {
 
@Autowired
 private TalentDAO talentDAO;
protected static Logger logger = Logger.getLogger(TalentServiceImpl.class);


@Override
public long addTalent(Talent talent) {
	logger.debug("Inside method TalentServiceImpl.addTalent, add bean : " + talent);
	/*talent.setStatus("active");
talent.setCreationTime(new Date());*/
	long result = talentDAO.addTalent(talent);
	logger.debug("Inside method TalentServiceImpl.addTalent, after adding bean with id: " + result);
	return result;
	
}


@Override
public Talent listTalents(Talent talent)
 {
	logger.debug("Inside method TalentServiceImpl.listTalents");
		Talent result =  this.talentDAO.listBeans(talent);
	//	logger.debug("Inside method TalentServiceImpl.listTalents, after listing beans : " + result.getTalentList());
		return result;
	
}

@Override
public Talent listBeansCustom(Talent talent) {
	logger.debug("Inside method TalentServiceImpl.getTalent");
	Talent result = this.talentDAO.listBeansCustom(talent);
	logger.debug("Inside method TalentServiceImpl.getTalent, after get talent : " + result);
	return result;
}


@Override
public Talent getTalent(int id) {
	logger.debug("Inside method TalentServiceImpl.getTalent");
	Talent result = this.talentDAO.getTalent(id);
	logger.debug("Inside method TalentServiceImpl.getTalent, after get talent : " + result);
	return result;
}


@Override
public void updateTalent(Talent talent) {
	logger.debug("Inside method TalentServiceImpl.updateTalent");
	
	this.talentDAO.updateTalent(talent);
	logger.debug("Inside method TalentServiceImpl.updateTalent, after update talent : " + talent);
}
 
}