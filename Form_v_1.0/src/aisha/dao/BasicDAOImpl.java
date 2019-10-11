package aisha.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import aisha.bean.Talent;

 
//@Repository
public class BasicDAOImpl implements TalentDAO {
 
 @Autowired
 private SessionFactory sessionFactory;
 protected static Logger logger = Logger.getLogger(BasicDAOImpl.class);
 
 private Session getCurrentSession() {
  return sessionFactory.getCurrentSession();
 }
 
 public long addTalent(Talent talent) {
	 logger.debug("Inside method TalentDAOImpl.addTalent");
	 //talent.setCreationTime(new Date());
  long result = (long) getCurrentSession().save(talent);
  logger.debug("Inside method TalentDAOImpl.addTalent, after add talent with id :" + result);
  return result;
 }
 
 
 
 @Override
	@Transactional
	public Talent listBeans(Talent talent) {
	 logger.debug("Inside method TalentDAOImpl.listBeans");
	 Criteria criteria= sessionFactory.getCurrentSession().createCriteria(Talent.class);

		List<Talent> sList = null;
/*		if (talent.getFilter() != null)
		{
			List<String> keysList = new ArrayList<String>(talent.getFilter().keySet());
			Iterator<String> keyListIterator = keysList.iterator();
			while (keyListIterator.hasNext()) {
				String searchCriteriaKey = keyListIterator.next();
				
				
				Object searchCreteriaValue = talent.getFilter()
						.get(searchCriteriaKey);
				
				
				if(searchCreteriaValue != null && searchCreteriaValue.toString().isEmpty()!= true)
				{	
					criteria.add(Restrictions.eq(searchCriteriaKey,
							searchCreteriaValue));
				}
			}
			
		}*/
/*	
	if (talent.isOrderAsc() == true) {
		criteria.addOrder(Order.asc(talent.getOrderBy()));
	} else {
		criteria.addOrder(Order.desc(talent.getOrderBy()));
	}*/
	
	logger.debug("Inside method TalentDAOImpl.listBeans, criteria :" + criteria);
	criteria.setProjection(null);
	//disabled for testing since no data
	//criteria.setFirstResult(talent.getFirstResults());
	//criteria.setMaxResults(talent.getMaxResults());

		
	
	sList=criteria.list();
	logger.debug("Inside method TalentDAOImpl.listBeans, result size :" + sList.size());
	logger.debug("Inside method TalentDAOImpl.listBeans, result list :" + sList);
	int numerOfRows = sList.size();
//	talent.setTotalResults(numerOfRows);
	
		//talent.setTalentList(sList);
		logger.debug("Inside method TalentDAOImpl.listBeans, criteria :" );
		return talent;
		
	}

 @Override
 @Transactional
	public Talent getTalent(int talentID) {
	 logger.debug("Inside method TalentDAOImpl.getTalent");
		
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Talent.class);
	
		
		criteria.add(Restrictions.eq("id", Long.valueOf(String.valueOf(talentID))));
		List<Talent> sList = null;
		logger.debug("Inside method TalentDAOImpl.getTalent, get talent with id : "+talentID);
		sList=criteria.list();
		logger.debug("Inside method TalentDAOImpl.getTalent, get talent result : "+talentID);

		
		System.out.println("################# :" + sList.size());
		try
		{logger.debug("Inside method TalentDAOImpl.getTalent, get talent result : " + sList.get(0));
		return sList.get(0);}
		catch(Exception e)
		{System.out.println("#######  exception : "+e.getMessage());return null;}
	}

 @Transactional
	public void updateTalent(Talent talent) {
	 logger.debug("Inside method TalentDAOImpl.updateTalent : " + talent);
		Session session = this.sessionFactory.getCurrentSession();
		session.update(talent);
		logger.debug("Inside method TalentDAOImpl.updateTalent, after update talent");
}

@Override
public Talent listBeansCustom(Talent talent) {
	// TODO Auto-generated method stub
	return null;
}
}