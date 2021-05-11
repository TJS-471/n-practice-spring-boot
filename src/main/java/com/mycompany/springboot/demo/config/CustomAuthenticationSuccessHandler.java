package com.mycompany.springboot.demo.config;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mycompany.springboot.demo.entity.User;
import com.mycompany.springboot.demo.service.impl.UserDetailsServiceImpl;

/**
 * A class that implements the interface {@link org.springframework.security.web.authentication.AuthenticationSuccessHandler}
 * and provides a custom authentication success handler.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserDetailsServiceImpl userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {

		String userName = authentication.getName();

		User theUser =  userService.findByUserName(userName);
		
		// now place in the session
		HttpSession session = request.getSession();
		String login = theUser.getUserName();
		session.setAttribute("login", login);
		
		// forward to home page
		
		response.sendRedirect(request.getContextPath() + "/");
	}

}
