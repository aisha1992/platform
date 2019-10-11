package aisha.security.controllers;


import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import aisha.security.beans.SystemUser;



public class AuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {



	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		SecurityContextHolder.getContext().getAuthentication()
		.getPrincipal();
		System.out.println("###### SecurityContextHolder : " +	SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal());
		
		SystemUser col = 	 (SystemUser)  SecurityContextHolder.getContext().getAuthentication()
		.getPrincipal();
		System.out.println("### i am authentication : "+authentication);
		super.onAuthenticationSuccess(request, response, authentication);
		
		System.out.println("################ i am collection : "+col);
	}

}
