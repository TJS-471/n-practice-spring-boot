package com.mycompany.springboot.demo.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.springboot.demo.service.RoleService;
import com.mycompany.springboot.demo.service.UserService;
import com.mycompany.springboot.demo.dto.RoleDto;
import com.mycompany.springboot.demo.dto.UserDto;

/**
 * A controller for the {@link UserDto} page
 * that exposes the endpoints for the admin role.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService service;
	@Autowired
	private RoleService serviceRole;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showAdminPage() {
		return "admin/admin_page";
	}

	@GetMapping("/show_user_form")
	protected String showUserForm(Model model) {
		model.addAttribute("user", new UserDto());
		return "registration/registration_form";
	}

	@GetMapping("/show_update_user_form")
	protected String showUpdateForm(@RequestParam("login") String login, Model model) {
		UserDto byLogin = service.getByLogin(login);
		List<RoleDto> rolesList = serviceRole.getAll();
		model.addAttribute("rolesList", rolesList);
		model.addAttribute("user", byLogin);
		return "user/user_form";
	}

	@RequestMapping("/delete_user")
	protected String deleteUser(@RequestParam("id") long id) {
		UserDto byId = service.getById(id);
		if(byId != null) {
			service.remove(id);
		}
		return "redirect:/user/list_users";
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
