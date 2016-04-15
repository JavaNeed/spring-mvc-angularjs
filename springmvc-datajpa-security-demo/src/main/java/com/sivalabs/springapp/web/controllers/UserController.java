package com.sivalabs.springapp.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.sivalabs.springapp.entities.User;
import com.sivalabs.springapp.services.UserService;
import com.sivalabs.springapp.web.config.SecurityUser;

@Controller
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	private static UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		UserController.userService = userService;
	}

	public static User getCurrentUser(){
		LOGGER.debug("---- Get Current User  ----");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails){
			String email = ((UserDetails) principal).getUsername();
			User loginUser = userService.findUserByEmail(email);
			return new SecurityUser(loginUser);
		}
		else
			return null;
	}
}

