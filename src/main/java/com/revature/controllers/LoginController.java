package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.revature.beans.User;
import com.revature.services.UserService;

public class LoginController {
	private Logger log = Logger.getRootLogger();
	private UserService us = new UserService();

	public void delegateGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Get request in Login controller");
		request.getRequestDispatcher("/static/login.html").forward(request, response);

	}

	public void delegatePost(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Post request delegated to Login controller");
		String actualURL = request.getRequestURI().substring(request.getContextPath().length() + "/".length());

		if ("".equals(actualURL)) {
			login(request, response);

		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		try {

			String json = request.getReader() // Get the buffered reader for reading request body
					.lines() // Stream it
					.reduce((acc, curr) -> acc + curr) // Convert lines to one String
					.get(); // Get that single string value
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(json, User.class);
			log.trace("username received: " + user.getUsername());
			log.trace("password received: " + user.getPassword());
			User actualUser = us.login(user);
			if (actualUser == null) {
				response.setStatus(401);
			} else {
				response.setStatus(200);
				request.getSession().setAttribute("userId", actualUser.getUserId());
				request.getSession().setAttribute("roleId", actualUser.getRoleId());
				int userId = (int) request.getSession(false).getAttribute("userId");
				String userSend = new Gson().toJson(userId);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(userSend);

			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
