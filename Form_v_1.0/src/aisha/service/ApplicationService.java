package aisha.service;

import java.util.List;

import aisha.bean.Application;

 
public interface ApplicationService {
 
    public void addApplication(Application app);
    public List<Application> listApplications(Application app);
    public Application getApplication(int id);
    public void updateApplication(Application app);
   /*
  
    public void deleteStrategy(int id);
    */
 
}
