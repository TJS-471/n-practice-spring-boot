package com.mycompany.springboot.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.springboot.demo.service.UserService;
import com.mycompany.springboot.demo.dto.UserDto;

/**
 * A controller for the registration page.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    @Autowired
    private UserService userService;

	@InitBinder
	public void initBinder1(WebDataBinder dataBinder) {
		StringTrimmerEditor stringEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringEditor);
	}
	@InitBinder
	public void initBinder2(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		dataBinder.registerCustomEditor(Date.class, editor);
	}

	@GetMapping("/show_registration_form")
	public String showMyLoginPage(Model theModel) {

		theModel.addAttribute("user", new UserDto());

		return "registration/registration_form";
	}

	@PostMapping("/process_registration_form")
	public String processRegistrationForm(@Valid @ModelAttribute("user") UserDto theUser,
			BindingResult theBindingResult, Model theModel) {

		String userName = theUser.getUserName();

		// form validation
		if (theBindingResult.hasErrors()) {
			return "registration/registration_form";
		}

		// check the database if user already exists
		UserDto userByLogin = userService.getByLogin(userName);
		if (userByLogin != null) {
			theModel.addAttribute("user", new UserDto());
			theModel.addAttribute("registrationError", "User name already exists.");
			return "registration/registration_form";
		}

		// create user account
		userService.save(theUser);

		return "registration/registration_confirmation";
	}
}
