package aisha.service;

import java.util.List;

import aisha.bean.Application;
import aisha.bean.ApplicationTemplate;

 
public interface ApplicationTemplateService {
 
    public long addApplicationTemplate(ApplicationTemplate app);
    public ApplicationTemplate listApplicationTemplate(ApplicationTemplate app);
    public ApplicationTemplate getApplicationTemplate(ApplicationTemplate app);
    public void updateApplicationTemplate(ApplicationTemplate app);
   /*
  
    public void deleteStrategy(int id);
    */
 
}
