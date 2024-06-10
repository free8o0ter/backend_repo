<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dbcon.*" %>

<%
    String lat = request.getParameter("LAT");
    String lnt = request.getParameter("LNT");
%>

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
        <input type="text" name="LAT">
        <input type="text" name="LNT">
        <button type="submit">가져오기</button>
    </form>
    <br>

    <table>
        <thead>
            <tr>
                <th scope="col">거리(Km)</th>
                <th scope="col">관리번호</th>
                <th scope="col">자치구</th>
                <th scope="col">와이파이명</th>
                <th scope="col">도로명주소</th>
                <th scope="col">상세주소</th>
                <th scope="col">설치위치(층)</th>
                <th scope="col">설치유형</th>
                <th scope="col">설치기관</th>
                <th scope="col">서비스구분</th>
                <th scope="col">망종류</th>
                <th scope="col">설치년도</th>
                <th scope="col">실내외구분</th>
                <th scope="col">WIFI접속환경</th>
                <th scope="col">X좌표</th>
                <th scope="col">Y좌표</th>
                <th scope="col">작업일자</th>
            </tr>
        </thead>
        <tbody>
            <%

                if (lat == null && lnt == null){
                out.println("<tr><td scope=row colspan=17 align=center>위치 정보를 입력한 후에 조회해 주세요.</td></tr>");
                } else {
                out.println(DbSqlite.geoFind(lat, lnt));
                }
            %>

        </tbody>
    </table>


</body>
</html>