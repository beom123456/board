package com.tjoeun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.tjoeun.beans.BoardInfoBean;

public interface TopMenuMapper {

	/*
	  1	자유게시판   <-- BoardInfoBean 객체 하나
    2	유머게시판   <-- BoardInfoBean 객체 하나
    3	정치게시판   <-- BoardInfoBean 객체 하나
    4	스포츠게시판 <-- BoardInfoBean 객체 하나
    
    List<BoardInfoBean> <-- BoardInfoBean 객체 4 개를 담는 List
	*/
	@Select("select * from board_info_table " +
	        "order by board_info_idx")
	List<BoardInfoBean> getTopMenuList();
	
}
