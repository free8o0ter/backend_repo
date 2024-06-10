<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dbcon.*" %>

<%!
    String str1 = "JSP";
    String str2 = "안녕하세요";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
</head>
<body>
    <h2 text-align="center">와이파이 정보 구하기</h2>
    <p>
       <%   int first = 1;
            int last = 1000;
            int totalCount = -1;
            totalCount = GetDataObj.getData(first, last);
            if (totalCount == -1) out.println("first req error");

            first += 1000;
            last += 1000;

            while(first <= totalCount){
                GetDataObj.getData(first, last);
                first += 1000;
                last += 1000;
            }


            out.println(totalCount);

       %> 개의 WIFI 정보를 정상적으로 저장하였습니다.
       <br>
       <a href="./index.jsp" text-align="center">홈으로 가기</a>
    </p>
</body>
</html>