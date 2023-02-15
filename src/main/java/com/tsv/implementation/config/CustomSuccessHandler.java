package com.tsv.implementation.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tsv.implementation.dao.UserRepository;
import com.tsv.implementation.model.User;
import com.tsv.implementation.service.DefaultUserService;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DefaultUserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = null;
		 UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String username = userDetails.getUsername();
         User user = userRepo.findByEmail(username);
         String output = userService.genrateOTPAndSendOnMobile(user);
         if(output=="success") 
        	 redirectUrl="/login/otpVerification";
         else
        	 redirectUrl="/login?otpfailed";
         
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

}
