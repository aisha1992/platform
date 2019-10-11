package aisha.security.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import aisha.bean.PlatformUser;
import aisha.bean.Strategy;
import aisha.security.beans.SystemUser;

public interface SystemUserService extends UserDetailsService{
	   
	    public void addSystemUser(PlatformUser sysUser);
	    public SystemUser getSystemUser(SystemUser user);
	    public void updateSystemUser(SystemUser user);
		public SystemUser listSystemUsers(SystemUser user);
	 
}
