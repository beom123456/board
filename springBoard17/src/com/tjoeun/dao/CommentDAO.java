package com.tjoeun.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tjoeun.beans.CommentBean;
import com.tjoeun.mapper.CommentMapper;

@Repository
public class CommentDAO {
	
	@Autowired
	private CommentMapper commentMapper;
	
	public void addConmmentInfo(CommentBean writeCommentBean) {
		commentMapper.addConmmentInfo(writeCommentBean);
	}	
	public List<CommentBean> getCommentList(int content_idx){
		return commentMapper.getCommentList(content_idx);	
	}
	public void modifyCommentInfo(CommentBean modifyCommnetBean) {
		System.out.println("DAO"+ modifyCommnetBean);
	 	commentMapper.modifyCommentInfo(modifyCommnetBean);
	}
	
	public void deleteCommentInfo(int comment_idx) {
		commentMapper.deleteCommentInfo(comment_idx);
	}
	
	public int getCommentCount(int content_idx) {
		return commentMapper.getCommentCount(content_idx);
	}
	
}
