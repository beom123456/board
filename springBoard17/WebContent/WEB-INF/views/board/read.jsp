<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>TJoenBoard</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet"
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
    src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<style>
    .comment {
        border: 1px solid #ddd;
        padding: 15px;
        margin-bottom: 10px;
        border-radius: 5px;
    }
    .comment h4 {
        margin: 0 0 10px;
        font-size: 1.2em;
    }
    .comment .comment-info {
        font-size: 0.9em;
        color: #555;
    }
    .comment .comment-text {
        font-size: 1.1em;
        margin: 10px 0;
    }
    .comment-actions {
        text-align: right;
    }
</style>
</head>
<body>

    <!-- 상단 메뉴 부분 -->
    <c:import url="/WEB-INF/views/include/top_menu.jsp" />

    <div class="container" style="margin-top: 100px">
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="card shadow">
                    <div class="card-body">
                        <div class="form-group">
                            <label for="board_writer_name">작성자</label>
                            <input type="text" id="board_writer_name" name="board_writer_name" class="form-control" value="${readContentBean.content_writer_name }" disabled="disabled" />
                        </div>
                        <div class="form-group">
                            <label for="board_date">작성날짜</label>
                            <input type="text" id="board_date" name="board_date" class="form-control" value="${readContentBean.content_date }" disabled="disabled" />
                        </div>
                        <div class="form-group">
                            <label for="board_subject">제목</label>
                            <input type="text" id="board_subject" name="board_subject" class="form-control" value="${readContentBean.content_subject }" disabled="disabled" />
                        </div>
                        <div class="form-group">
                            <label for="board_content">내용</label>
                            <textarea id="board_content" name="board_content" class="form-control" rows="10" style="resize: none" disabled="disabled">${readContentBean.content_text }</textarea>
                        </div>
                        <c:if test="${readContentBean.content_file != null }">
                            <div class="form-group">
                                <label for="board_file">첨부 이미지</label>
                                <img src="${root }upload/${readContentBean.content_file}" width="100%" />
                            </div>
                        </c:if>
                        <div class="form-group">
                            <div class="text-right">
                                <a href="${root }board/main?board_info_idx=${board_info_idx}&page=${page}" class="btn btn-primary">목록보기</a>
                                <c:if test="${loginUserBean.user_idx == readContentBean.content_writer_idx }">
                                    <a href="${root }board/modify?board_info_idx=${board_info_idx}&content_idx=${content_idx}&page=${page}" class="btn btn-info">수정하기</a>
                                    <a href="${root }board/delete?board_info_idx=${board_info_idx}&content_idx=${content_idx}" class="btn btn-danger">삭제하기</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3"></div>
        </div>
    </div>

    <div class="container">
        <h4>댓글 목록</h4> 댓글 개수 총 ${commentCount} 개

        <c:forEach var="comment" items="${commentList}">
            <div class="comment">
                <h4>작성자: ${comment.comment_writer_id} <span class="comment-info">(${comment.comment_date})</span></h4>
                <p class="comment-text">내용: ${comment.comment_text}</p>
                <div class="comment-actions">
                    <c:if test="${loginUserBean.userLogin == true && loginUserBean.user_id == comment.comment_writer_id}">
                        <input type="hidden" id="comment_idx_${comment.comment_idx}" name="comment_idx" value="${comment.comment_idx}">
                        <input type="text" id="comment_text_${comment.comment_idx}" name="comment_text" value="${comment.comment_text}" class="form-control mb-2">
                        <button type="button" onclick="modifyComment(${comment.comment_idx})" class="btn btn-sm btn-primary">수정</button>
                        <button type="button" onclick="deleteComment(${comment.comment_idx})" class="btn btn-sm btn-danger">삭제</button>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="container mt-4">
        <div class="comment-form">
            <form:form action="${root}board/comment_proc" modelAttribute="writeCommentBean" method="post">
                <input type="hidden" name="content_idx" value="${content_idx}">
                <input type="hidden" name="board_info_idx" value="${board_info_idx}">
                <input type="hidden" name="page" value="${page}">
                <div class="form-group">
                    <label for="comment_text">댓글 작성</label>
                    <textarea id="comment_text" name="comment_text" class="form-control" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">작성하기</button>
            </form:form>
        </div>
    </div>

    <!-- footer -->
    <c:import url="/WEB-INF/views/include/bottom_info.jsp" />

    <!-- ajax로 댓글 수정하기 -->
    <script>
        function modifyComment(commentIdx) {
            let comment_text = $("#comment_text_" + commentIdx).val();  //name 변수 ,  value 값
            let comment_idx = $("#comment_idx_" + commentIdx).val();
            
            if (comment_text.trim() === "") {
                alert("댓글을 입력해 주세요");
                return;
            }
            
            $.ajax({
                url: "${root}/comment/modifyComment/" + comment_text + "/" + parseInt(comment_idx), // comment_idx 는 int이기 때문에 변환
                type: "post",
                dataType: "text",
                success: function(result) {
                    if (result.trim() === "") {
                        alert("수정이 완료되었습니다.");
                        location.reload();
                    } 
                }
            });
        }
        
        function deleteComment(commentIdx){
            let comment_idx = $("#comment_idx_" + commentIdx).val();
            
            $.ajax({
                url: "${root}/comment/deleteComment/" + comment_idx,
                type: "post",
                dataType: "text",
                success: function(result){
                    if(result.trim()===""){
                        alert("삭제가 완료되었습니다.")
                         location.reload();
                    }
                }
            });
        }
    </script>    

</body>
</html>
