<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


  <jsp:include page="../include/header.jsp"/>
  
  
  
  <h1>전기차 충전소 정보</h1>
  
  <div id="area">
  	
  
  </div>
  
  <script>
  	$(function() {
  		$.ajax({
  			url: 'getInfo',
  			type: 'get',
  			success: result => {
  				console.log(result);
  			}
  			
  			
  		})
  		
  	})
  	
  
  </script>
  
  
  
  <jsp:include page="../include/footer.jsp"/>
</body>
</html>