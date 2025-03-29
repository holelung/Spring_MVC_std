<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	#searchBox{
		margin:auto;
		display:flex;
		flex-direction: column;
		align-items: center;
	}

	#items{
		display:flex;
		margin:auto;
		flex-wrap: wrap;
		gap:15px;
		width: 1000px;
	}
	
	.item-img{
		width:300px;
		height:200px;
	}

</style>

</head>
<body>

<jsp:include page="../include/header.jsp"/>

<div id="searchBox">
	<h1> 상품 검색 레츠고 </h1>
	
	<div id="searchTab">
		<input type="text" id="query">
		<button onclick="naverSearch();">검색!</button>
	</div>

	<div id="items">
		
	</div>

</div>


<script>
	function naverSearch(){
		const query = document.querySelector('#query').value;
		$.ajax({
			url:`naver-shopping?query=\${query}`,
			type:'GET',
			success: result =>{
				console.log(result);

				const items = result.items;
				
				const item = items.map(e => `
							<div style="width :300px; height:450px; padding:10px; display:inline-block">
								<h5>\${e.brand}</h5>
								<br>
								<p>\${e.title}</p>
								<p>가격 : \${e.lprice}</p>
								<img src="\${e.image}" class="item-img"/>
								<a href="\${e.link}" target="_blank">보러가기</a>				
							</div>
						
						`).join('');
				document.querySelector('#items').innerHTML = item;
			}
			
		});
		
	}
	

</script>

<jsp:include page="../include/footer.jsp"/>

</body>
</html>