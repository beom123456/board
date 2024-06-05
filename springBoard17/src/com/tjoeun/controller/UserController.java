package com.tjoeun.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.tjoeun.beans.UserBean;
import com.tjoeun.service.UserService;
import com.tjoeun.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}
  
	@GetMapping("/login")
	public String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
			                @RequestParam(value="failure", defaultValue="false") boolean failure,
			                Model model) {
		
		model.addAttribute("failure", failure);
		
		return "user/login";
	}
	
	@PostMapping("/login_proc")
	public String login_proc(@Valid @ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
			                     BindingResult result ) {
		
		if(result.hasErrors()) {
			return "user/login";
		}
		
		userService.getLoginUserInfo(tempLoginUserBean);
		
		if(loginUserBean.isUserLogin() == true) {
			  return "user/login_success";
		}else {
		    return "user/login_failure";
		}
	}
	
	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBeanBean) {
		return "user/join";
	}    
	      
	@PostMapping("/join_proc")
	public String join_proc(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean,
			                    BindingResult result) {
		System.out.println("userIdExist : " + joinUserBean.isUserIdExist());
		
		if(result.hasErrors()) {
			return "user/join";
		}
		
		userService.addUserInfo(joinUserBean); 
		
		return "user/join_success";
	}  
	
	@GetMapping("/modify")
	public String modify(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) {
		
		userService.getModifyUserInfo(modifyUserBean);
		// System.out.println("modifyUserBean (controller) : " + modifyUserBean);
		System.out.println(modifyUserBean.getUser_name());
		System.out.println(modifyUserBean.getUser_id());
		
		return "user/modify";
	}
	
	@PostMapping("/modify_proc")
	public String modify_proc(@Valid @ModelAttribute("modifyUserBean") UserBean modifyUserBean,
			                      BindingResult result) {
		if(result.hasErrors()) {
			return "user/modify";
		}
		
		boolean comparison = userService.modifyUserInfo(modifyUserBean);
		
		if(comparison == true) {
			return "user/modify_success";
		}
		return "user/modify_failure";
	}
	   
	@GetMapping("/logout")
	public String logout() {
		
		loginUserBean.setUserLogin(false);
		loginUserBean.setUser_idx(0);
		loginUserBean.setUser_id("");
		loginUserBean.setUser_pw("");
		
		return "user/logout";
	}
	
	@GetMapping("/not_login")
	public String not_login() {
		return "user/not_login";
	}
	
}

