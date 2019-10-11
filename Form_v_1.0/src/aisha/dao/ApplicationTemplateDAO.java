package aisha.dao;

import java.util.List;

import aisha.bean.ApplicationTemplate;


 
public interface ApplicationTemplateDAO {
 
    public long addApplicationTemplate(ApplicationTemplate app);
    public ApplicationTemplate getApplicationTemplate(ApplicationTemplate app);
    public void updateApplicationTemplate(ApplicationTemplate app);
/*    
    
    public void deleteStrategy(int id);
    public List<Strategy> getStrategies();*/

   

		
	public ApplicationTemplate listApplicationTemplates(ApplicationTemplate app);
     
}