package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.EmployeeModel;
import com.service.EmployeeService;

@Controller
public class LoginController { 
	
	@Autowired
	EmployeeService empService;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String redirectToLogin(Model model) {
		return "redirect:/login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(Model model) {
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String submitLoginPage(@ModelAttribute("employeeModel") EmployeeModel employeeModel, Model model) {
		employeeModel = empService.authentication(employeeModel);
		
		if(employeeModel.isStatus()) {
			return "redirect:/employeeDash";
		}
		else {
			model.addAttribute("Error", "Invalid user id or password!");
			return "login";
		}
	}

}
