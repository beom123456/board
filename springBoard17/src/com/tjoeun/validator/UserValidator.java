package com.tjoeun.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tjoeun.beans.UserBean;

public class UserValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		UserBean userBean = (UserBean)target;
		
		String beanName = errors.getObjectName();
		System.out.println("유효성 검사 대상 객체 : " + beanName);
		
		if(beanName.contentEquals("joinUserBean") || beanName.contentEquals("modifyUserBean")) {
  		// 비밀번호 확인하기 (user_pw 와 user_pw2 가 일치하는지 확인함)
  		if(userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
  			errors.rejectValue("user_pw", "NoEquals");
  			errors.rejectValue("user_pw2", "NoEquals");
  		}
		}  
		if(beanName.contentEquals("joinUserBean")){
  	  // 회원 가입할 때 입력한 아이디에 대해서
  		// 중복확인을 하지 않고 회원가입 버튼을 눌렀을 때....
  		if(userBean.isUserIdExist() == false) {
  			errors.rejectValue("user_id", "DontCheckUserIdExist");
  		}
		}
	}
}
