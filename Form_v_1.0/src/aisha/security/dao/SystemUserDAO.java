package aisha.security.dao;

import aisha.bean.PlatformUser;
import aisha.security.beans.SystemUser;

	 
	public interface SystemUserDAO {
	 
	    public void addSystemUser(PlatformUser sysUser);
	    public SystemUser getSystemUser(SystemUser user);
	    public void updateSystemUser(SystemUser user);
		public SystemUser listSystemUsers(SystemUser user);
	}

