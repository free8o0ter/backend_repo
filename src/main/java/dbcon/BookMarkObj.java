package dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookMarkObj {


    public static String getBookOption(){
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + "ms_js.db");

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        String select_history_sql = "Select * from BOOKMARK_group";
        PreparedStatement pstmt = null;
        StringBuilder sb = new StringBuilder();

        try{
            pstmt = conn.prepareStatement(select_history_sql);
            ResultSet res = pstmt.executeQuery();




            while(res.next()){
                String ap = String.format("<option value=%d>%s</option>",
                        res.getInt("id"),
                        res.getString("bookmark_name"));
                sb.append(ap);

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(!pstmt.isClosed()) pstmt.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }


        return sb.toString();
    }

    public static String getBookMark(){
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + "ms_js.db");

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        String select_history_sql = "Select * from BOOKMARK_GROUP";
        PreparedStatement pstmt = null;
        StringBuilder sb = new StringBuilder();

        try{
            pstmt = conn.prepareStatement(select_history_sql);
            ResultSet res = pstmt.executeQuery();




            while(res.next()){
                String ap = String.format("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        res.getInt("id"),
                        res.getString("bookmark_name"),
                        res.getString("b_order"),
                        res.getString("STORE_TIME"),
                        res.getString("FIX_TIME"));
                sb.append(ap);

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(!pstmt.isClosed()) pstmt.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }


        return sb.toString();
    }

    public static int addBookMarkGroup(String name, String b_order){
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + "ms_js.db");

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        String insert_history = "insert into BOOKMARK_GROUP (BOOKMARK_NAME, B_ORDER, STORE_TIME) VALUES (?, ?, datetime())";
        PreparedStatement pstmt = null;

        int count=0;
        try {
            pstmt = conn.prepareStatement(insert_history);
            pstmt.setString(1, name);
            pstmt.setString(2,b_order);
            count = pstmt.executeUpdate();

            if (count != 1){
                System.out.println("Someting wrong in insert BOOKMARKGROUP query");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(!pstmt.isClosed()) pstmt.close();
            } catch (Exception e){
                e.printStackTrace();
            }

        }



        return count;
    }
}

class BookMark implements Comparable<Item>{
    public double distance;
    public String mgr_no;
    public String wrdofc;
    public String main_nm;
    public String adres1;
    public String adres2;
    public String instl_floor;
    public String instl_ty;
    public String instl_mby;
    public String svc_se;
    public String cmcwr;
    public String cnstc_year;
    public String inout_door;
    public String remars3;
    public String lat;
    public String lnt;
    public String work_dttm;

    public String print(){
        return String.format("<tr><td>%.4f</td><td>%s</td><td>%s</td><td><a href=./info.jsp?MGR_NO=%s>%s</a></td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
                distance, mgr_no, wrdofc, mgr_no, main_nm, adres1, adres2, instl_floor, instl_ty, instl_mby, svc_se, cmcwr, cnstc_year,inout_door, remars3, lat,lnt, work_dttm);
    }

    public String infoPrint(){
        return "<table>\n" +
                "        </tr><th scope=\"col\">거리(Km)</th><td>"+distance+"</td></tr>\n" +
                "        <tr><th scope=\"col\">관리번호</th><td>"+mgr_no+"</td></tr>\n" +
                "        <tr><th scope=\"col\">자치구</th><td>"+wrdofc+"</td></tr>\n" +
                "        <tr><th scope=\"col\">와이파이명</th><td>"+main_nm+"</td></tr>\n" +
                "        <tr><th scope=\"col\">도로명주소</th><td>"+adres1+"</td></tr>\n" +
                "        <tr><th scope=\"col\">상세주소</th><td>"+adres2+"</td></tr>\n" +
                "        <tr><th scope=\"col\">설치위치(층)</th><td>"+instl_floor+"</td></tr>\n" +
                "        <tr><th scope=\"col\">설치유형</th><td>"+instl_ty+"</td></tr>\n" +
                "        <tr><th scope=\"col\">설치기관</th><td>"+instl_mby+"</td></tr>\n" +
                "        <tr><th scope=\"col\">서비스구분</th><td>"+svc_se+"</td></tr>\n" +
                "        <tr><th scope=\"col\">망종류</th><td>"+cmcwr+"</td></tr>\n" +
                "        <tr><th scope=\"col\">설치년도</th><td>"+cnstc_year+"</td></tr>\n" +
                "        <tr><th scope=\"col\">실내외구분</th><td>"+inout_door+"</td></tr>\n" +
                "        <tr><th scope=\"col\">WIFI접속환경</th><td>"+remars3+"</td></tr>\n" +
                "        <tr><th scope=\"col\">X좌표</th><td>"+lat+"</td></tr>\n" +
                "        <tr><th scope=\"col\">Y좌표</th><td>"+lnt+"</td></tr>\n" +
                "        <tr><th scope=\"col\">작업일자</th><td>"+work_dttm+"</td></tr>\n" +
                "    </table>";
    }

    @Override
    public int compareTo(Item o){
        return distance < o.distance ? -1: 1;
    }


}
