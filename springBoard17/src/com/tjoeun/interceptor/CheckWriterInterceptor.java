package com.tjoeun.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.tjoeun.beans.ContentBean;
import com.tjoeun.beans.UserBean;
import com.tjoeun.service.BoardService;

public class CheckWriterInterceptor implements HandlerInterceptor{
	
	private UserBean loginUserBean;
	private BoardService boardService;
	
	public CheckWriterInterceptor(UserBean loginUserBean,BoardService boardService) {
		this.loginUserBean = loginUserBean;
		this.boardService = boardService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String str_content_idx = request.getParameter("content_idx");
		int content_idx = Integer.parseInt(str_content_idx);
		
		ContentBean currentContentBean = boardService.getContentInfo(content_idx);
		
		if(loginUserBean.getUser_idx() != currentContentBean.getContent_writer_idx()) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/board/not_writer");
			return false;
		}
		
		return true;
		
	}
	
	

}
