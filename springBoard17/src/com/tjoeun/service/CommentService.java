package com.tjoeun.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.beans.CommentBean;
import com.tjoeun.beans.UserBean;
import com.tjoeun.dao.CommentDAO;

@Service
public class CommentService {
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;

	public void addConmmentInfo(CommentBean writeCommentBean) {
		
		//댓글 단 유저 아이디를 화면에 띄우고 두 유저아이디가 일치할 때만 보이게 하기 위해서
		writeCommentBean.setComment_writer_id(loginUserBean.getUser_id());
		
		commentDAO.addConmmentInfo(writeCommentBean);
	}
	
	public List<CommentBean> getCommentList(int content_idx){
		return commentDAO.getCommentList(content_idx);	
	}
	
	public void modifyCommentInfo(CommentBean modifyCommnetBean) {
		System.out.println("service" + modifyCommnetBean);
		 commentDAO.modifyCommentInfo(modifyCommnetBean);
	}
	
	public void deleteCommentInfo(int comment_idx) {
		commentDAO.deleteCommentInfo(comment_idx);
	}
	
	public int getCommentCount(int content_idx) {
		return commentDAO.getCommentCount(content_idx);
	}
	
}
