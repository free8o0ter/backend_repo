<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dbcon.*" %>

<%
    String bookmark_name = request.getParameter("bookmark_name");
    String b_order = request.getParameter("b_order");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <style>
        thead, th{
          background-color: rgb(50,205,102);
          color: white;
          padding: 8px 10px;
          margin: 10px;
        }
        td {
          border: 1px solid rgb(160 160 160);
          padding: 8px 10px;
        }
        table {
                width : 700px;
        }
    </style>
</head>
<body>
    <h2>와이파이 정보 구하기</h2>
    <p>
        <a href="./index.jsp"> 홈</a>&nbsp|&nbsp
        <a href="./history.jsp"> 위치 히스토리 목록</a>&nbsp|&nbsp
        <a href="./getdata.jsp"> Open API 와이파이 정보 가져오기</a>&nbsp|&nbsp
        <a href="./bookmark_group.jsp"> 북마크 보기</a>&nbsp|&nbsp
        <a href="./bookmark_group.jsp"> 북마크 그룹 관리</a>
    </p><br>
    <%
        if(bookmark_name != null && b_order != null){
            int check = BookMarkObj.addBookMarkGroup(bookmark_name, b_order);
            if(check == 1){
                out.println("(" + bookmark_name + "이 등록되었습니다.");
            } else {
                out.println("(" + bookmark_name + " 등록에 실패하였습니다..");
            }

        }
    %>
    <form action="./bookmark_group_add.jsp" method="GET">
        <table>
            <tr><th>북마크 이름</th><td><input type="text" name="bookmark_name"></td></tr>
            <tr><th>순서</th><td><input type="text" name="b_order"></td></tr>
            <tr><td colspan=2 align=center><button type="submit">추가</button></td></tr>
        </table>
    </form>


</body>
</html>