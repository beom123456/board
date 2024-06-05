package com.tjoeun.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.beans.ContentBean;
import com.tjoeun.beans.PageBean;
import com.tjoeun.beans.UserBean;
import com.tjoeun.dao.BoardDAO;

@Service
@PropertySource("/WEB-INF/properties/path.properties")
@PropertySource("/WEB-INF/properties/page.properties")
public class BoardService {
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Value("${path.upload}")
	private String path_upload;
	
	@Value("${page.contentPageCount}")
	private int contentPageCount;
	
	@Value("${page.paginationCount}")
	private int paginationCount;
	
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;
	
	private String saveUploadFile(MultipartFile upload_file) {
		String fileName = 
				System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();
		try {
			  path_upload = path_upload.trim();
			  upload_file.transferTo(new File(path_upload + "/" + fileName));
		}catch(Exception e) {
			  e.printStackTrace();
		}
		return fileName;
	}
	    
	public void addContentInfo(ContentBean writeContentBean) {
		/*
		System.out.println("제목 : " + writeContentBean.getContent_subject());
		System.out.println("내용 : " + writeContentBean.getContent_text());
		System.out.println("파일 : " + writeContentBean.getUpload_file());
		System.out.println("파일 : " + writeContentBean.getUpload_file().getSize());
		*/
		
		MultipartFile upload_file = writeContentBean.getUpload_file();
		
		String fileName;
		if(upload_file.getSize() > 0) {
			fileName = saveUploadFile(upload_file);
			System.out.println("파일 이름 : " + fileName);
		}else {
			// 파일을 첨부하지 않는 경우
			fileName = "";
		}
		
		writeContentBean.setContent_file(fileName);
		
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
		
		boardDAO.addContentInfo(writeContentBean); 
		
	} // addContentInfo

	public String getBoardInfoName(int board_info_idx) {
		return boardDAO.getBoardInfoName(board_info_idx);
	}
	
	public List<ContentBean> getContentList(int board_info_idx, int page){
		
		int start = (page - 1) * contentPageCount;
		RowBounds rowBounds = new RowBounds(start, contentPageCount);
		return boardDAO.getContentList(board_info_idx, rowBounds);
	}
	
	public ContentBean getContentInfo(int content_idx) {
		return boardDAO.getContentInfo(content_idx);
	}
	
	public void modifyContentInfo(ContentBean modifyContentBean) {
		
		MultipartFile upload_file = modifyContentBean.getUpload_file();
		
		if(upload_file.getSize() > 0) {
			String fileName = saveUploadFile(upload_file);
			modifyContentBean.setContent_file(fileName);
		}
		
		boardDAO.modifyContentInfo(modifyContentBean);
	}
	
	public void deleteContentInfo(int content_idx) {
		boardDAO.deleteContentInfo(content_idx);
	}
	
	public PageBean getContentCount(int content_board_idx, int currentPage) {
		int contentCount =  boardDAO.getContentCount(content_board_idx);
	  /*
      int contentCount : (각 게시판 별)전체 게시글 개수
      int currentPage  : 현재 페이지 번호
      int contentPageCount : 페이지 당 게시글 개수 
      int paginationCount  : 페이지 버튼 개수
    */
		PageBean pageBean = new PageBean(contentCount, currentPage, 
                                     contentPageCount, paginationCount);
		
		return pageBean;
	}
	
	
}




