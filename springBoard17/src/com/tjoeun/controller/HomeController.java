package com.tjoeun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tjoeun.beans.UserBean;

@Controller
public class HomeController {
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		// project 가 실행되면서 Session Scope 에
		// loginUserBean 이 생성되는지 확인하기
		System.out.println("loginUserBean IN session : " + loginUserBean);
		
		System.out.println("ContextPath");
		System.out.println(request.getServletContext().getRealPath("/"));
				
		return "redirect:/main";
	}
	  
}
