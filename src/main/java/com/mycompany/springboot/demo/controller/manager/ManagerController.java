package com.mycompany.springboot.demo.controller.manager;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.springboot.demo.service.RoleService;
import com.mycompany.springboot.demo.service.UserService;
import com.mycompany.springboot.dto.RoleDto;
import com.mycompany.springboot.dto.UserDto;

/**
 * A controller for the {@link UserDto} page
 * that exposes the endpoints for the manager role.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private UserService service;
	@RequestMapping
	public String showManagerPage() {
		return "manager/manager_page";
	}

	@GetMapping("/show_update_user_form")
	protected String showUpdateForm(@RequestParam("login") String login, Model model) {
		UserDto byLogin = service.getByLogin(login);
		model.addAttribute("user", byLogin);
		return "user/user_form";
	}

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
}
