package aisha.service;



import aisha.bean.StartupProfile;

 
public interface StartupProfileService {
 
    public long addStartupProfile(StartupProfile profile);
    public StartupProfile listStartupProfiles(StartupProfile profile);
    public StartupProfile getStartupProfile(int id);
    public void updateStartupProfile(StartupProfile profile);
    
}
