package com.tjoeun.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tjoeun.beans.CommentBean;
import com.tjoeun.beans.ContentBean;
import com.tjoeun.beans.PageBean;
import com.tjoeun.beans.UserBean;
import com.tjoeun.service.BoardService;
import com.tjoeun.service.CommentService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	@Autowired
	private CommentService commentService;
  
	@GetMapping("/main")
	public String main(@RequestParam("board_info_idx") int board_info_idx,
			               @RequestParam(value="page", defaultValue="1") int page,
			               Model model) {
		
		String boardName = boardService.getBoardInfoName(board_info_idx);
		List<ContentBean> contentList = boardService.getContentList(board_info_idx, page);
		PageBean pageBean = boardService.getContentCount(board_info_idx, page);
		
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("boardName", boardName);
		model.addAttribute("contentList", contentList);
		model.addAttribute("page", page);
		model.addAttribute("pageBean", pageBean);

		return "board/main";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("board_info_idx") int board_info_idx,
			               @RequestParam("content_idx") int content_idx,
			               @RequestParam("page") int page,
			               @ModelAttribute("writeCommentBean") CommentBean writeCommentBean,
			               Model model) {
		
		ContentBean readContentBean = boardService.getContentInfo(content_idx);
		List<CommentBean> commentList = commentService.getCommentList(content_idx);
		
		
		System.out.println("commentList.size : " +commentList.size() );
		
		int commentCount = commentService.getCommentCount(content_idx);
		
		
		/*
		for(CommentBean comment : CommentList) {
			
			System.out.println(comment.getComment_idx());
			System.out.println(comment.getComment_writer_id());
			System.out.println(comment.getComment_text());
			System.out.println(comment.getComment_date());
		}
		*/
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx);
		model.addAttribute("readContentBean", readContentBean);
		model.addAttribute("loginUserBean", loginUserBean);
		model.addAttribute("page", page);
		model.addAttribute("commentList",commentList);
		model.addAttribute("commentCount",commentCount);
		     
		return "board/read";
	}
	
	@PostMapping("/comment_proc")
	public String commnet_read(@Valid @ModelAttribute("writeCommentBean") CommentBean writeCommentBean,BindingResult result,
			                       @RequestParam("content_idx") int content_idx,
			                       @RequestParam("board_info_idx") int board_info_idx,
			                       @RequestParam("page") int page,
			                       Model model
			                       ) {
		
		
	  model.addAttribute("page",page);
	  model.addAttribute("board_info_idx",board_info_idx);
	  model.addAttribute("content_idx",content_idx);
	  
		if(result.hasErrors()) {
			
			return "board/comment_fail";
		}
		
		
		writeCommentBean.setComment_content_idx(content_idx);
		commentService.addConmmentInfo(writeCommentBean);
	
		
	
	
		return "board/comment_success";
	}
	
	
	
	
	   
	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentBean") ContentBean writeContentBean,
			                @RequestParam("board_info_idx") int board_info_idx) {
		
		writeContentBean.setContent_board_idx(board_info_idx);
		
		return "board/write";
	}
	
	@PostMapping("/write_proc")
	public String write_proc(@Valid @ModelAttribute("writeContentBean") ContentBean writeContentBean,
			                     BindingResult result) {
	
		if(result.hasErrors()) {
			return "board/write";
		}  
		
		boardService.addContentInfo(writeContentBean);
		
		return "board/write_success";
	}
	
	// @RequestParam("board_info_idx") <-- 생략 가능함
	@GetMapping("/modify")
	public String modify(@ModelAttribute("modifyContentBean") ContentBean modifyContentBean,
			                 @RequestParam("board_info_idx") int board_info_idx, 
			                 @RequestParam("content_idx") int content_idx,
			                 @RequestParam("page") int page,
			                 Model model) {
		
		ContentBean tempModifyContentBean = boardService.getContentInfo(content_idx);
		
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx);
		model.addAttribute("page", page);
		modifyContentBean.setContent_writer_name(tempModifyContentBean.getContent_writer_name());
		modifyContentBean.setContent_date(tempModifyContentBean.getContent_date());
		modifyContentBean.setContent_subject(tempModifyContentBean.getContent_subject());
		modifyContentBean.setContent_text(tempModifyContentBean.getContent_text());
		modifyContentBean.setContent_file(tempModifyContentBean.getContent_file());
		modifyContentBean.setContent_writer_idx(tempModifyContentBean.getContent_writer_idx());
		modifyContentBean.setContent_board_idx(board_info_idx);
		modifyContentBean.setContent_idx(content_idx);
		
    return "board/modify";
	}
	
	@PostMapping("/modify_proc")
	public String modify_proc(@Valid @ModelAttribute("modifyContentBean") ContentBean modifyContentBean,
			                      BindingResult result,
			                      @RequestParam("page") int page,
			                      Model model) {
		if(result.hasErrors()) {
			return "board/modify";
		}
		
		boardService.modifyContentInfo(modifyContentBean);
		
		model.addAttribute("page", page);
		
		return "board/modify_success";
	}
	
	@GetMapping("/not_writer")
	public String not_writer() {
		return "board/not_writer";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam int board_info_idx,
			                 @RequestParam int content_idx,
			                 Model model) {
		
		boardService.deleteContentInfo(content_idx);
		model.addAttribute("board_info_idx", board_info_idx);
		
		return "board/delete";
	}
}
