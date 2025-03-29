<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문자문자</title>

<style>
	#wrap{
		margin:auto;
		margin-top: 20px;
		width: 800px;
		
		border : 2px solid blue;
		border-radius: 20px;
		background-color: lightblue;
		padding:20px;
		
	
	}
	#content {
		
	}
	
	.message{
		background-color: yellow;
		border:1px solid black;
		border-radius:15px;
		padding: 10px;
		margin-bottom: 10px;
	}

</style>


</head>

<body>
	<jsp:include page="../include/header.jsp" />
	
	
	<div id="wrap" class="container">
		<div id="content">
		
		</div>
	
		<div class="d-flex justify-content-md-end">
			<button class="btn btn-success " onclick="getMessage();">더보기</button>
		</div>
	</div>
	
	<script>
	
		$(function(){
			getMessage();
		});
		
		let pageNo = 1;
		function getMessage(){
			$.ajax({
				url : `message?pageNo=\${pageNo}`,
				type : 'get',
				success : result => {
					console.log(result);
					const messages = result.body;
					
					const outputStr = messages.map(e=>`
						<div class="message">
							<h3 class="category">\${e.DST_SE_NM}</h3>
							<p class="cotent">\${e.MSG_CN}</p>
							<h6 class="region">\${e.RCPTN_RGN_NM}</h6>
						</div>
					`).join('');
					$('#content').append(outputStr);
					pageNo++;
					
					document.querySelector("button.btn-success").scrollIntoView({behavior:"smooth"});
				}
			});
		}
		
	</script>
	
	

	<jsp:include page="../include/footer.jsp" />
</body>

</html>