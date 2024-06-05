package com.tjoeun.beans;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserBean {
	private int user_idx;
	
	@Size(min=2, max=4)
	@Pattern(regexp="[가-힣]*")
	private String user_name;
	
	@Size(min=8, max=30)
	@Pattern(regexp="[a-zA-Z0-9]*")
	private String user_id;
	
	@Size(min=8, max=30)
	@Pattern(regexp="[a-zA-Z0-9]*")	
	private String user_pw;
	
	@Size(min=8, max=30)
	@Pattern(regexp="[a-zA-Z0-9]*")		
	private String user_pw2;
	
	// 회원가입할 때 입력한 아이디가 사용할 수 있는지 아닌지 
	// boolean 값을 저장하는 멤버변수 추가
	private boolean userIdExist;
	
	// 로그인했는지 안 했는지 상태를 저장하는 멤버변수 추가
	private boolean userLogin;
	
}




