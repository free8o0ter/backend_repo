<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dbcon.*" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <style>
        thead {
          background-color: rgb(50,205,102);
          color: white;
          padding: 8px 10px;
          margin: 10px;
        }
        th,
        td {
          border: 1px solid rgb(160 160 160);
          padding: 8px 10px;
        }
        table {
        width : 1500px;
        }
    </style>
</head>
<body>
    <h2>위치 히스토리 목록</h2>
    <p>
        <a href="./index.jsp"> 홈</a>&nbsp|&nbsp
        <a href="./history.jsp"> 위치 히스토리 목록</a>&nbsp|&nbsp
        <a href="./getdata.jsp"> Open API 와이파이 정보 가져오기</a>&nbsp|&nbsp
        <a href="./bookmark_group.jsp"> 북마크 보기</a>&nbsp|&nbsp
        <a href="./bookmark_group.jsp"> 북마크 그룹 관리</a>
    </p><br>
    <br>

    <table>
        <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">X좌표</th>
                <th scope="col">Y좌표</th>
                <th scope="col">조회시간</th>
            </tr>
        </thead>
        <tbody>
            <%
                String history_lst = null;
                history_lst = DbSqlite.getHistory();


                if (history_lst == null){
                    out.println("<tr><td scope=row colspan=4 align=center>히스토리 정보가 존재하지 않습니다.</td></tr>");
                } else {
                out.println(history_lst);
                }
            %>

        </tbody>
    </table>


</body>
</html>