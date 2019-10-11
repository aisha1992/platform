package aisha.security.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import aisha.security.beans.SystemUser;



@Controller
@RequestMapping("/LogoutController")
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
/*	@Autowired
	@Qualifier("systemUserService")
	protected IBasicService systemUserService;
*/
	@Override
	@RequestMapping("/logout")
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		final String thisMethod = "onLogoutSuccessHandler: ";
		logger.debug(thisMethod + "Entering.............. ");
		try{
		SystemUser currentSystemUser = (SystemUser) authentication
				.getPrincipal();
		//currentSystemUser.setLastLoginDate(new Date());
		//currentSystemUser.setHasLoggedOut(true);

	//	BaseRequest baseRequest = new BaseRequest();
	//	baseRequest.setRequestData(currentSystemUser);
		//baseRequest.setRequestTime(new Date());
		//try {
		/*	systemUserService.updateBean(baseRequest);

		} catch (Exception e) {
			logger.error(thisMethod + e);
		}*/
		
		// TODO add audit log for system user logout
		if (authentication != null) {
			// do something
		}

		setDefaultTargetUrl("/");
		super.onLogoutSuccess(request, response, authentication);
		}
		catch(Exception e){
			super.onLogoutSuccess(request, response, authentication);
		}
	}

}