package com.chapter2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chapter2.model.Customer;
import com.chapter2.service.CustomerService;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
	private CustomerService customerService;

	@Override
	public void init() throws ServletException{
		customerService = new CustomerService();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Customer> customers = customerService.getCustomerList();
		request.setAttribute("customerList", customers);
		request.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(request, response);
	}
}
