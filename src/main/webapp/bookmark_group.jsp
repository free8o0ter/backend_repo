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
    <button type="button" onclick="location.href='./bookmark_group_add.jsp'">북마크 그룹 이름 추가</button>

    <br>

    <table>
        <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">북마크 이름</th>
                <th scope="col">순서</th>
                <th scope="col">등록일자</th>
                <th scope="col">수정일자</th>
                <th scope="col">비고</th>
            </tr>
        </thead>
        <tbody>
            <%
                out.print(BookMarkObj.getBookMark());
            %>

        </tbody>
    </table>


</body>
</html>