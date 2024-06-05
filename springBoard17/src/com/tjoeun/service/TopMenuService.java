package com.tjoeun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.beans.BoardInfoBean;
import com.tjoeun.dao.TopMenuDAO;

@Service
public class TopMenuService {
  
	@Autowired
	private TopMenuDAO topMenuDAO;
	
	public List<BoardInfoBean> getTopMenuList(){
		List<BoardInfoBean> topMenuList = topMenuDAO.getTopMenuList();
		return topMenuList;
	}
	
}
