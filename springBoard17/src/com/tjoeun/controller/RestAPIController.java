package com.tjoeun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.beans.CommentBean;
import com.tjoeun.service.CommentService;
import com.tjoeun.service.UserService;

@RestController
public class RestAPIController {
   @Autowired
   private UserService userService;
   
   @Autowired
   private CommentService commentService;
   
   
   @GetMapping("/user/checkUserIdExist/{user_id}")
   public String checkUserIdExist(@PathVariable String user_id) {
  	 boolean checkId = userService.checkUserIdExist(user_id);
  	 return checkId + ""; 
   }
   
   @PostMapping("/comment/modifyComment/{comment_text}/{comment_idx}")
   public String modifyComment(@PathVariable String comment_text,
		                       @PathVariable int comment_idx,
		                       @ModelAttribute("modifyCommentBean") CommentBean modifyCommnetBean) {
	   
	   // mapper 에서 2개의 변수만 있는 파라미터를 받지못해 modifyCommnetBean 객체 생성후 comment_text,comment_idx set함
	   
	   System.out.println("controller : " + comment_text);
	   
	   modifyCommnetBean.setComment_text(comment_text);
	   modifyCommnetBean.setComment_idx(comment_idx);
	   
	   commentService.modifyCommentInfo(modifyCommnetBean);
	 
	   
	   
	   return ""; 
   }
   
   @PostMapping("/comment/deleteComment/{comment_idx}")
   public String deleteComment(@PathVariable int comment_idx) {
	   
	   commentService.deleteCommentInfo(comment_idx);
	   
	   return "";
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}
