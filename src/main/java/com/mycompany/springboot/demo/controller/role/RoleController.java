package com.mycompany.springboot.demo.controller.role;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.springboot.demo.service.RoleService;
import com.mycompany.springboot.demo.dto.RoleDto;

/**
 * A controller for the {@link RoleDto} page
 * that exposes the endpoints for the admin role.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class RoleController {
	@Autowired
	private RoleService service;

	@GetMapping("/show_role_form")
	protected String showRoleForm(Model model) {
		model.addAttribute("role", new RoleDto());
		return "role/role_form";
	}

	@PostMapping("/save_role")
	protected String saveRole(@ModelAttribute("role") RoleDto role){
		service.save(role);
		return "redirect:/admin/list_roles";
	}

	@GetMapping("/show_update_role_form")
	protected String showUpdateForm(@RequestParam("id") long id, Model model) {
		RoleDto role = service.getById(id);
		model.addAttribute("role", role);
		return "role/role_form";
	}

	@GetMapping("/list_roles")
	protected String listRoles(HttpServletRequest req, Model model) {
		List<RoleDto> roleList = service.getAll();
		// sent all attributes to jsp page
		model.addAttribute("roleList", roleList);
		return "role/roles_list";
	}

	@RequestMapping("/delete_role")
	protected String deleteRole(@RequestParam("id") long id) {
		RoleDto role = service.getById(id);
		if (role != null) {
			service.remove(role);
		}
		return "redirect:/admin/list_roles";
	}

	@InitBinder
	public void initBinder1(WebDataBinder dataBinder) {
		StringTrimmerEditor stringEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringEditor);
	}

}
