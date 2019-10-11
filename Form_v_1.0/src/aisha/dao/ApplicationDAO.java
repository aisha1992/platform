package aisha.dao;

import java.util.List;

import aisha.bean.Application;


 
public interface ApplicationDAO {
 
    public void addApplication(Application app);
    public Application getApplication(int id);
    public void updateApplication(Application app);
/*    
    
    public void deleteStrategy(int id);
    public List<Strategy> getStrategies();*/

   

		
	public List<Application> listApplications(Application app);
     
}