package com.mycompany.springboot.demo.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * A controller for the login page.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Controller
public class LoginController {

	@GetMapping("/show_my_login_page")
	public String showMyLoginPage() {

		return "login/login";

	}

	// add request mapping for /access_denied

	@GetMapping("/access_denied")
	public String showAccessDenied() {

		return "error/access_denied";

	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringEditor);
	}
}
