package aisha.security.services;


import java.math.BigInteger;
import java.security.MessageDigest;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.passay.CharacterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import aisha.bean.PlatformUser;
import aisha.controller.ApplicationController;
import aisha.security.beans.SystemUser;
import aisha.security.dao.IBasicdbAdapter;
import aisha.security.dao.SystemUserDAO;



public class SystemUserServiceImpl implements UserDetailsService,SystemUserService {

	@Autowired
	SystemUserDAO systemUserDBAdapter;
		

	public SystemUser getSystemUser(SystemUser user)
	{
		user = systemUserDBAdapter.getSystemUser(user);
	
System.out.println("############# shishi : "+user);
		return user;
		
	}
	/* this method add new system User after check it is not exist on dataBase
	*//* (non-Javadoc)
	 * @see com.ebs.commons.services.CommonService#addBean(com.ebs.commons.beans.BaseRequest)
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		System.out.println("######### loadUserByUsername");// TODO Auto-generated method stub
		
		SystemUser user = new SystemUser();
		user.setUserName(arg0);
		//user.setUserName("test");
		//user.setPassword("123456");
		//user.setUserRole("Engineer");

		//user = cypherPassword(user);
		//systemUserDBAdapter.addBean(user);
		user = systemUserDBAdapter.getSystemUser(user);
		user.setEnabled(true);
		user.setLoginTryCount(0);
		//user.setUserFullName("aisha");
		//user.setPassword("aisha");
		//user.setPassword("aisha");
		//user = cypherPassword(user);
		System.out.println("############# after cypher : "+user);
		//user.setUserRole("ROLE_ADMIN");
		//user.setPassword("aisha");
		
		System.out.println("############# shishi : "+user);
		/*ApplicationController.setCurrentUser(user);*/
				return user;
	}
	protected PlatformUser cypherPassword(PlatformUser user) {
		final String thisMethod = "SystemUserController.addBean: ";
	
		
		String password = user.getPassword();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes(), 0, password.length());
			String hashedPass = new BigInteger(1, messageDigest.digest())
					.toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}

			user.setPassword(hashedPass);

		} catch (Exception e) {
			
			
		}

		return user;
	}
	
	
	@Transactional
	public void addSystemUser(PlatformUser user) {
		String password = generatePassayPassword();
		System.out.println("@@@ password : "+password);
		user.setPassword(password);
		PlatformUser cyper = cypherPassword(user);
		systemUserDBAdapter.addSystemUser(cyper);
	}
	
	
	public String generatePassayPassword() {
	    PasswordGenerator gen = new PasswordGenerator();
	    CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
	    CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
	    lowerCaseRule.setNumberOfCharacters(2);
	 
	    CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
	    CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
	    upperCaseRule.setNumberOfCharacters(2);
	 
	    CharacterData digitChars = EnglishCharacterData.Digit;
	    CharacterRule digitRule = new CharacterRule(digitChars);
	    digitRule.setNumberOfCharacters(2);
	 
	    CharacterData specialChars = new CharacterData() {
	        public String getErrorCode() {
	            return "505";
	        }
	 
	        public String getCharacters() {
	            return "!@#$%^&*()_+";
	        }
	    };
	    CharacterRule splCharRule = new CharacterRule(specialChars);
	    splCharRule.setNumberOfCharacters(2);
	 
	    String password = gen.generatePassword(10, splCharRule, lowerCaseRule, 
	      upperCaseRule, digitRule);
	    return password;
	}

	@Override
	public void updateSystemUser(SystemUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SystemUser listSystemUsers(SystemUser user) {
		SystemUser result = systemUserDBAdapter.listSystemUsers(user);
		return result;
	}
	}