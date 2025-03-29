<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Map is here</title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=279c48b01f19b5ea2404c35b3f33fd4b"></script>
</head>
<body>
	<jsp:include page="../include/header.jsp"/>
	
	
	
	<div id="map" style="width:500px;height:400px; margin:auto"></div>	
	
	<script>
		var container = document.getElementById('map');
		var options = { //지도를 생성할 때 필요한 기본 옵션
				center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
				level: 3 //지도의 레벨(확대, 축소 정도)
			};

		var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	</script>
	<jsp:include page="../include/footer.jsp"/>
</body>
</html>