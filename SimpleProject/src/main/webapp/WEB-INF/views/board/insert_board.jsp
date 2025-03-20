<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245); 
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #enrollForm>table {width:100%;}
        #enrollForm>table * {margin:5px;}
        #img-area{
            width : 100%;
            margin : auto;
            text-align: center;
        }
        #img-area > img{
            width : 80%;
        }
    </style>
</head>
<body>
        
    <jsp:include page="../include/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 작성하기</h2>
            <br>

            <form id="enrollForm" method="post" action="boards" enctype="multipart/form-data">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${ sessionScope.loginMember.memberId }" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td><input onchange="changeImage(this);" type="file" id="upfile" class="form-control-file border" name="upfile"></td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required></textarea></td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <div id="img-area">
                                <img src="data:image/jpeg;base64,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" alt="">
                            </div>
                        </th>
                    </tr>
                </table>
                <br>

				<script>
					function changeImage(file) {
						//console.log(file)
						//console.log(file.files);
						// files : 선택된 파일의 정보가 들어있는 객체
						const imgEl = document.querySelector('#img-area>img');
						if(file.files.length){ // 파일이 첨부
							const reader = new FileReader();
							reader.readAsDataURL(file.files[0]);
							
							reader.onload = function(e) {
								const url = e.target.result;
								imgEl.src = url; 
							}
						}else{
							imgEl.src = "data:image/jpeg;base64,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"
						}
					}
				</script>

                <div align="center">
                    <button type="submit" class="btn btn-primary">등록하기</button>
                    <button type="reset" class="btn btn-danger">취소하기</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../include/footer.jsp" />
    
</body>
</html>