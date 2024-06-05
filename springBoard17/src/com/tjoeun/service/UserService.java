package com.tjoeun.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.beans.UserBean;
import com.tjoeun.dao.UserDAO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	public boolean checkUserIdExist(String user_id) {
		String user_name = userDAO.checkUserIdExist(user_id);
		
		if(user_name == null) {
			// 사용할 수 있는 아이디인 경우
			return true;
		}else {
			// 사용할 수 없는 아이디인 경우
			return false;
		}
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		userDAO.addUserInfo(joinUserBean);
	}
	
	public void getLoginUserInfo(UserBean tempLoginUserBean) {
		UserBean tempLoginUserBean2 = userDAO.getLoginUserInfo(tempLoginUserBean);
		
		if(tempLoginUserBean2 != null) {
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_id(tempLoginUserBean2.getUser_id());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUser_pw(tempLoginUserBean2.getUser_pw());
			loginUserBean.setUserLogin(true);
		}
	}
	
	public void getModifyUserInfo(UserBean modifyUserBean) {
		UserBean tempUserBean = userDAO.getModifyUserInfo(loginUserBean.getUser_idx());
		// System.out.println("modifyUserBean (service) : " + modifyUserBean);
		modifyUserBean.setUser_name(tempUserBean.getUser_name());
		modifyUserBean.setUser_id(tempUserBean.getUser_id());
		
		
	}
	
	public boolean modifyUserInfo(UserBean modifyUserBean) {
		// modifyUserBean.getUser_pw() : 새로 입력한 비밀번호
		// loginUserBean.getUser_pw()  : 기존 입력한 비밀번호
		if(modifyUserBean.getUser_pw().equals(loginUserBean.getUser_pw())) {
			return false;
		}else {
			modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
			userDAO.modifyUserInfo(modifyUserBean);
      return true;			
		}
	}
	
}
