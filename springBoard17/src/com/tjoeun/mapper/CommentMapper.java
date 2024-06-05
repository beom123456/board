package com.tjoeun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.tjoeun.beans.CommentBean;

public interface CommentMapper {
	
	@SelectKey(statement="select comment_seq.nextval from dual", keyProperty="comment_idx", before=true, resultType=int.class)
	
	@Insert("INSERT INTO comment_table (comment_idx, comment_text, comment_content_idx, comment_date, comment_writer_id) " + 
			"VALUES (comment_seq.nextval, #{comment_text}, #{comment_content_idx}, SYSDATE, #{comment_writer_id})")
   void addConmmentInfo(CommentBean writeCommentBean);
	
	
	@Select(" select comment_idx, comment_writer_id, comment_text, " + 
			"content_idx comment_content_idx, " + 
			"comment_date " + 
			"from comment_table cm  , content_table ct " + 
			"where cm.comment_content_idx = ct.content_idx " + 
			"and ct.content_idx =#{content_idx} " +
			"order by content_idx desc")
    List<CommentBean> getCommentList(int content_idx);
	
	@Update("update comment_table " + 
			"set comment_text =#{comment_text} " + 
			"where comment_idx =#{comment_idx}")
	void modifyCommentInfo(CommentBean modifyCommnetBean);
	
	@Delete("delete from comment_table " + 
			"where comment_idx = #{comment_idx}")
	void deleteCommentInfo(int comment_idx);  
	
  @Select("select count(*) from comment_table " + 
  		"where comment_content_idx =#{content_idx}")
  int getCommentCount(int content_idx);
}
 


  