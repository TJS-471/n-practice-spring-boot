package com.mycompany.springboot.demo.controller.user;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.springboot.demo.service.RoleService;
import com.mycompany.springboot.demo.service.UserService;
import com.mycompany.springboot.dto.RoleDto;
import com.mycompany.springboot.dto.UserDto;


/**
 * A controller for the {@link UserDto} page
 * that exposes the endpoints for the user role.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping("/show_update_user_form")
	public String showUserPage(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String login = (String) session.getAttribute("login");
		UserDto byLogin = service.getByLogin(login);
		model.addAttribute("user", byLogin);
		return "user/user_form";
	}
	
	@PostMapping("/update_user")
	protected String CreateUser(@Valid @ModelAttribute("user") UserDto user,
			BindingResult result) throws IOException {
		if(result.hasErrors()) {
			return "user/user_form";
		}
		service. update(user);
		return "redirect:/user/list_users";
	}
	
	@GetMapping("/list_users")
	protected String listUsers(Model model) {
		List<UserDto> usersList = service.getAll();
		Map<Long, Integer> ages = new HashMap<>();
		for (UserDto user : usersList) {
			java.util.Date birthDate = new Date(user.getBirthDate().getTime());
			Period periodBetween = Period.between(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
					LocalDate.now());
			int age = periodBetween.getYears();
			ages.put(user.getId(), age);
		}
		// sent all attributes to the html page

		model.addAttribute("ages", ages);
		model.addAttribute("usersList", usersList);
		return "user/users_list";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("keyword") String keyword,
						 Model theModel) {
		
		// search the user
		List<UserDto> usersList = service.search(keyword);
		Map<Long, Integer> ages = new HashMap<>();
		for (UserDto user : usersList) {
			java.util.Date birthDate = new Date(user.getBirthDate().getTime());
			Period periodBetween = Period.between(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
					LocalDate.now());
			int age = periodBetween.getYears();
			ages.put(user.getId(), age);
		}
		// add to the spring model
		theModel.addAttribute("ages", ages);
		theModel.addAttribute("usersList", usersList);
		// send to /user/users_list
		return "/user/users_list";
		
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
