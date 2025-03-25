<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ㅎㅎ</title>
</head>
<body>

	<jsp:include page="../include/header.jsp"/>
	<h1>AJAX</h1>

	<pre>
		Asynchronous JavaScript And XML
		서버로부터 데이터를 응답 받아서 전체 페이지를 새롭게 렌더링하지 않고,
		일부만을 새롭게 갱신할 수 잇는 기법
		
		참고로 기존에 우리가 a태그 / location.href/ form 태그를 이용한 요청 == 동기식 요청!
		=> 응답 페이지 전체가 돌아와야만 화면을 새롭게 만들어낼 수 있음
		
		* 동기식 / 비동기식 요청 차이
		
		- 동기식 : 요청 처리 후 응답 HTML 데이터를 받아 화면에 렌더링 한 뒤에만 작업이 가능 
				 만약에 서버에서 응답데이터를 돌려주는 시간이 지연되면 무작정 기다림
				 전체 화면이 새로고침됨 ->  페이지가 깜빡!
				 
		- 비동기식 : 현재 페이지는 그대로 유지하면서 중간중간 추가적인 요청을 보낼 수 있음
				  응답이 돌아온다고해서 다른 페이지로 넘어가지 않음(페이지가 유지)
				  요청을 보내놓고 응답이 올때까지 다른 작업을 할 수 있음
		  사용예시) 아이디 중복체크 기능, 검색어 자동완성, 댓글
		  
		  ** 추가로 **
		  모든 요청 및 응답 처리를 AJAX로 구현
		
	</pre>
	
	<br>
	
	<pre>
		* 실전압축 jQuery 사용법
		
		기존
		document.querySelector('CSS 선택자')
		.addEventListener('click', () => {
			이벤트 발생 시 수행할 코드
		});
		
		=====
		
		jQuery
		$('CSS선택자');
		.click(()=>{
			이벤트 발생시 수행할 코드
		})
		
		===
		3. 콘텐트 변
		.innerHtml = '대입값';
		.html('대입하고 싶은 값')
		
		==
		4. 스타일변경
		.style.세부스타일 = '스타일 속성';		
		
	</pre>
	<h3 id="h3">하하호호 나에게 접근해라</h3>


	<script>
		
		window.onload = function() {
			
		/*  const h3E1 = document.querySelector('#h3');
			h3E1.addEventListener('click',()=> {
				
				alert("hihi");
			}) 
			h3E1.innerHTML ="<h1>zz</h1>"
			*/
			const h3E1 = $('#h3');
			
		
			h3E1.click(() => {
				alert("하핳호호");
				
			}).html("<h1> 어쩔ㅋ </h1>").css('background', 'red');
			
			console.log(h3E1);
			
			
		}

	</script>
	
	<hr>
	<h1>jQuery를 사용한 AJAX 통신</h1>
	<pre>
		* jQuery를 사용한 AJAX 통신	
		
		[표현법]
		$.ajax({ 
			속성명 : 값,
			속성명 : 값,
			속성명 : 값
			...
		});
		
		속성명 기억할 거 주요 4가지 
			- type : 요청전송 방식(GET/POST..PUT,DELETE)
					GET방식 : 조회요청(SELECT)
					POST 방식 : 데이터 생성요청(INSERT)
					PUT 방식 : 데이터 갱신 쇼청(UPDATE)
					DELETE 방식 : 데이터 삭제요청(DELETE) 
			- url : 요청할 URL(필수작성) =>  form 태그로 따지면 action 속성
			- success : AJAX 통신 성공시 실행할 함수를 정의  
			- data : 요청 시 전달값({KEY: VALUE}) => form태그의 input요소 입력값
		나머지는 알아서 찾아쓰삼
	</pre>
	
	
  <hr>
  
 	<h2>jQuery 방식으로 AJAX로 요청을 보내고  응답을 받아서 화면상에 출력</h2>
	
	<h3>1. 버튼을 클릭했을 때 GET방시으로 서버에 데이터를 전송하고 키받아오기</h3>
	
	<div class="form-group py-3">
		<div class="form-control">
			입력 : <input type="text" id="ajaxInput">
		</div>
		<div class="form-control">
			<button class="btn btn-sm btn-primary" id="ajaxBtn">뻐튼</button>
		</div>
	</div>
	
	응답 : <label id="resBox">현재 응답 없음</label>
	
	
	<script>
		$('#ajaxInput').keyup(()=>{
			const inputValue = $('#ajaxInput').val();
			console.log(inputValue);
		});
	
		$('#ajaxBtn').click(()=>{
			const inputValue = $('#ajaxInput').val();
			
			$.ajax({
				url : `test?input=\${inputValue}`,
				type : 'GET',
				success : function(res){
					console.log(res);
					$('#resBox').text(res);
				}
			})
		})
	
	</script>
	
	
	
	
	
	
	<h3>VO 단일 객체 조회해서 출력하기 </h3>
	
	<div>
		게시글 제목 : <p id="title"></p>
		게시글 작성자 : <p id="writer"></p>
		게시글 내용 : <p id="content"></p>
		게시글 작성일 : <p id="date"></p>
		
		<hr>
		
		<img id ="board-img"/>
		
		<hr>
		
		<div id="reply-area">
			
		</div>
	</div>
	
	
	게시글 번호 : <input type="text" id="replyNo">
	<button onclick="hi()">댓글 보여주세요</button>
	
	<script>
		function hi(){
			const replyNo = document.getElementById('replyNo').value;
			
			$.ajax({
				url: `study?replyNo=\${replyNo}`,
				type: 'GET',
				success: result => {
					console.log(result);
					// 응답받은 데이터 화면에 출력
					$('#title').text(result.boardTitle);
					$('#writer').text(result.boardWriter);
					$('#content').text(result.boardContent);
					$('#date').text(result.createDate);
					
					if(result.changeName){
						$('#board-img').attr('src', result.changeName);
					} else {
						$('#board-img').attr('src', "");
					}
					
					const reply = result.replyList;
					console.log(reply);
					
					const elements = reply.map(e => {
						return (
							`<div>
							<label> 댓글 작성자 : \${e.replyWriter}</label>
							<label> 댓글 내용 : \${e.replyContent}</label>
							<label> 작성일 : \${e.createDate}</label>
							</div>`)
					}).join('');
					
					document.querySelector('#reply-area').innerHTML = elements; 
				}
			});
			
		}
		
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
</body>
</html>