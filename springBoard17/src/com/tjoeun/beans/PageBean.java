package com.tjoeun.beans;

import lombok.Data;

@Data
public class PageBean {
	
	// 시작 페이지
	private int min;
	
	// 끝 페이지
	private int max;
	
	// 이전 버튼 눌렀을 때의 페이지 번호
	private int prevPage;
	
  // 다음 버튼 눌렀을 때의 페이지 번호
	private int nextPage;
	
	// 전체 페이지 개수
	private int pageCount;
	
	// 현재 페이지 번호
	private int currentPage;
	
  private int firstPage;
  
  private int lastPage;
	
  /*
    int contentCount : (각 게시판 별)전체 게시글 개수
    int currentPage  : 현재 페이지 번호
    int contentPageCount : 페이지 당 게시글 개수 
    int paginationCount  : 페이지 버튼 개수
  */
	public PageBean(int contentCount, int currentPage, 
			            int contentPageCount, int paginationCount) {
		
  	// 현재 페이지 번호
		this.currentPage = currentPage;
		
		// 전체 페이지 개수 : 전체 게시글 개수 / 페이지 당 게시글 개수 
		this.pageCount = contentCount / contentPageCount;
		
		if(contentCount % contentPageCount > 0) {
			this.pageCount++;
		}
		
		/*
		  int min
		  0 page : 1  (시작 페이지)
		  1 page : 11 (시작 페이지)
		  2 page : 21 (시작 페이지)
		  3 page : 31 (시작 페이지)
		  4 page : 41 (시작 페이지)
		  ......
		  
		  시작 페이지 : 
		  ((현재 페이지 번호 - 1) / 페이지 당 게시글 개수) * 페이지 당 게시글 개수 + 1 
		*/
		this.min = ((currentPage - 1) / contentPageCount) * contentPageCount + 1;
		
		/*
		  끝 페이지 : 시작 페이지 + 페이지 버튼 개수 - 1
		*/
		this.max = this.min + paginationCount - 1;
		
		/* 끝 페이지가 전체 페이지 개수 보다 큰 경우 */
		if(this.max > this.pageCount) {
			this.max = this.pageCount;
		}
		
		// 이전 버튼 눌렀을 때의 페이지 번호 : 시작 페이지 - 1
		this.prevPage = this.min - 1;
		
	  // 다음 버튼 눌렀을 때의 페이지 번호 : 끝 페이지 + 1
		this.nextPage = this.max + 1;
		
		/*
		  다음 버튼 눌렀을 때의 페이지 번호가 
		  전체 페이지 개수 보다 큰 경우 
		    ㄴ 마지막 페이지에서 다음 버튼 누르는 경우
		*/
		if(this.nextPage > this.pageCount) {
			this.nextPage = this.pageCount;
		}
		
		 this.firstPage=1;
	   this.lastPage= this.pageCount;
	}
	
	
	
	
	
	
}
