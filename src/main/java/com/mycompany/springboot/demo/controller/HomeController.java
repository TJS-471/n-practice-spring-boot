package com.mycompany.springboot.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller for the home page.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Controller
public class HomeController {
	@RequestMapping("/")
	protected String getHomePage(){
		return "home";
	}
}
