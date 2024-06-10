package dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DbSqlite {
    static double R = 6371;

    public static double distance(String lat_1, String lnt_1, String lat_2, String lnt_2){
        double lat1 = Double.valueOf(lat_1);
        double lnt1 = Double.valueOf(lnt_1);
        double lat2 = Double.valueOf(lat_2);
        double lnt2 = Double.valueOf(lnt_2);


        double dLat = deg2rad(lat1 - lat2);
        double dLnt = deg2rad(lnt1 - lnt2);
        double fst = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.sin(dLnt/2) * Math.sin(dLnt/2);
        double sec = 2 * Math.atan2(Math.sqrt(fst), Math.sqrt(1-fst));
        double dist = R * sec;

        return dist;
    }

    public static double deg2rad(double num){
        return num * (Math.PI/180);
    }



    public static String getInfo(String mgr_no){
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + "ms_js.db");

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        String select_info_sql = "Select * from swifi where X_SWIFI_MGR_NO = ?";
        PreparedStatement pstmt = null;
        Item item = new Item();

        try{
            pstmt = conn.prepareStatement(select_info_sql);
            pstmt.setString(1, mgr_no);
            ResultSet res = pstmt.executeQuery();

            while(res.next()){
                item.distance = 0.0000;
                item.mgr_no = res.getString("X_SWIFI_MGR_NO");
                item.wrdofc = res.getString("X_SWIFI_WRDOFC");
                item.main_nm = res.getString("X_SWIFI_MAIN_NM");
                item.adres1 = res.getString("X_SWIFI_ADRES1");
                item.adres2 = res.getString("X_SWIFI_ADRES2");
                item.instl_floor = res.getString("X_SWIFI_INSTL_FLOOR");
                item.instl_ty = res.getString("X_SWIFI_INSTL_TY");
                item.instl_mby = res.getString("X_SWIFI_INSTL_MBY");
                item.svc_se = res.getString("X_SWIFI_SVC_SE");
                item.cmcwr = res.getString("X_SWIFI_CMCWR");
                item.cnstc_year = res.getString("X_SWIFI_CNSTC_YEAR");
                item.inout_door = res.getString("X_SWIFI_INOUT_DOOR");
                item.remars3 = res.getString("X_SWIFI_REMARS3");
                item.lat = res.getString("LAT");
                item.lnt = res.getString("LNT");
                item.work_dttm = res.getString("X_SWIFI_REMARS3");

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


        return item.infoPrint();
    }


    public static String getHistory(){
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + "ms_js.db");

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        String select_history_sql = "Select * from history";
        PreparedStatement pstmt = null;
        StringBuilder sb = new StringBuilder();

        try{
            pstmt = conn.prepareStatement(select_history_sql);
            ResultSet res = pstmt.executeQuery();




            while(res.next()){
                String ap = String.format("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        res.getInt("id"),
                        res.getString("LAT"),
                        res.getString("LNT"),
                        res.getString("STORE_TIME"));
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

    public static String geoFind(String lat, String lnt){
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + "ms_js.db");

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        String latLike = "\'".concat(lat.substring(0, 5)).concat("%\'");
        String lntLike = "\'".concat(lnt.substring(0, 6)).concat("%\'");

        String sql = "Select * from swifi where LAT LIKE "+latLike+" AND LNT LIKE "+lntLike+" LIMIT 50";
        String insert_history = "insert into history (LAT, LNT, STORE_TIME) VALUES (?, ?, datetime())";
        PreparedStatement pstmt = null;


        try {
            pstmt = conn.prepareStatement(insert_history);
            pstmt.setString(1, lat);
            pstmt.setString(2,lnt);
            int count = pstmt.executeUpdate();

            if (count != 1){
                System.out.println("Someting wrong in insert history query");
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


        StringBuilder sb = new StringBuilder();
        List<Item> lst = new ArrayList<>(50);

        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();


            while(res.next()){
                String req_lat = res.getString("LAT");
                String req_lnt = res.getString("LNT");
                Item item = new Item();
                item.distance = distance(lat, lnt, req_lat, req_lnt);
                item.mgr_no = res.getString("X_SWIFI_MGR_NO");
                item.wrdofc = res.getString("X_SWIFI_WRDOFC");
                item.main_nm = res.getString("X_SWIFI_MAIN_NM");
                item.adres1 = res.getString("X_SWIFI_ADRES1");
                item.adres2 = res.getString("X_SWIFI_ADRES2");
                item.instl_floor = res.getString("X_SWIFI_INSTL_FLOOR");
                item.instl_ty = res.getString("X_SWIFI_INSTL_TY");
                item.instl_mby = res.getString("X_SWIFI_INSTL_MBY");
                item.svc_se = res.getString("X_SWIFI_SVC_SE");
                item.cmcwr = res.getString("X_SWIFI_CMCWR");
                item.cnstc_year = res.getString("X_SWIFI_CNSTC_YEAR");
                item.inout_door = res.getString("X_SWIFI_INOUT_DOOR");
                item.remars3 = res.getString("X_SWIFI_REMARS3");
                item.lat = res.getString("LAT");
                item.lnt = res.getString("LNT");
                item.work_dttm = res.getString("X_SWIFI_REMARS3");



                lst.add(item);
            }

            Collections.sort(lst);

            for (Item elem : lst){
                sb.append(elem.print());
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(!pstmt.isClosed()) pstmt.close();
            } catch (Exception e){
                e.printStackTrace();
            }

        }

        return sb.toString();
    }
}

class Item implements Comparable<Item>{
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
