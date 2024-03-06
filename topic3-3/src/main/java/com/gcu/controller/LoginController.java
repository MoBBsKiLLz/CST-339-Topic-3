package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.OrdersBusinessServiceInterface;
import com.gcu.business.SecurityBusinessService;
import com.gcu.model.LoginModel;
import com.gcu.model.OrderModel;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {
	// Inject and autowire from respective objects
	@Autowired
	private OrdersBusinessServiceInterface service;
	@Autowired
	private SecurityBusinessService security;
	
	/**
	 * Login Controller that returns a View and Model.
	 * Invoke using / URI.
	 * 
	 * @return Login form
	 */
	@GetMapping("/")
	public String display(Model model)
	{
		model.addAttribute("title", "Login Form");
		model.addAttribute("loginModel", new LoginModel());
		return "login";
	}
	
	/**
	 * Login Controller that print the form values.
	 * Invoke using /doLogin URI.
	 * 
	 * @return Login View
	 */
	@PostMapping("/doLogin")
	public String doLogin(@Valid LoginModel loginModel, BindingResult bindingResult, Model model) 
	{
		// Check for validation errors
		if (bindingResult.hasErrors())
		{
			model.addAttribute("title", "Login Form");
			return "login";
		}
		
		// Create some Orders
		List<OrderModel> orders = service.getOrders();
		
		// Setting model attributes
	    model.addAttribute("title", "My Orders");
	    model.addAttribute("orders", orders);
	    
	    // Call service
	    service.test();
	    security.authenticate(loginModel.getUsername(), loginModel.getPassword());
		
		// Navigate back to orders view
		return "orders";
	}
}
