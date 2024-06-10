<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dbcon.*" %>

<%
    String mgr_no = request.getParameter("MGR_NO");
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
    <form action"./index.jsp" method="GET">
        <select name="bookmark">
        <option value="">북마크 그룹 이름 선택</option>
        <%
            out.print(BookMarkObj.getBookOption());
        %>
        </select>
        <button type="submit"> 북마크 추가하기 </button>
    </form>
    <br>
    <% out.println(DbSqlite.getInfo(mgr_no)); %>


</body>
</html>