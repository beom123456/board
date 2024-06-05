package com.tjoeun.beans;


import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CommentBean {

	private int comment_idx;
	
	@NotBlank
	private String comment_text;
	
	private int comment_content_idx;
	
	private String comment_date;
	
	private String comment_writer_id;
	
}
