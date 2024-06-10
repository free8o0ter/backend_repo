import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;


import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args){
        String key = "7a667a7971646c743635566a725078";
        System.out.println(key);
        OkHttpClient cli = new OkHttpClient.Builder().build();
        Request req = new Request.Builder()
                .url("http://openapi.seoul.go.kr:8088/"+key+"/json/TbPublicWifiInfo/1/24593/")
                .get()
                .build();
        Response res = null;
        String resultJson = null;
        try {
            res = cli.newCall(req).execute();
            if (res.isSuccessful()){
                ResponseBody body = res.body();
                if(body != null){
                    resultJson = body.string();
                    //System.out.println("Response : " + body.string());
                }
            }
        } catch (IOException e){
            System.out.print("Error1\n" + e);
        }




        if(resultJson == null) System.err.println("result wifi list = null");

        Gson gson = new Gson();
        JsonObject jsonobject = JsonParser.parseString(resultJson).getAsJsonObject();
        JsonObject wifiInfo = jsonobject.get("TbPublicWifiInfo").getAsJsonObject();
        int total_list_count = wifiInfo.get("list_total_count").getAsInt();

        //DB 연결
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + "ms_js.db");

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        String sql = "insert into swifi (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;

        // 데이터 처리
        JsonArray row = wifiInfo.get("row").getAsJsonArray();
        for(int idx = 0; idx < row.size(); idx++){
            System.out.println("idx :" + idx + " X_SWIFI_NO : " + row.get(idx).getAsJsonObject().get("X_SWIFI_MGR_NO").getAsString());
            try {
                pstmt = conn.prepareStatement(sql);
                JsonObject wifi = row.get(idx).getAsJsonObject();
                pstmt.setString(1, wifi.get("X_SWIFI_MGR_NO").getAsString());
                pstmt.setString(2, wifi.get("X_SWIFI_WRDOFC").getAsString());
                pstmt.setString(3, wifi.get("X_SWIFI_MAIN_NM").getAsString());
                pstmt.setString(4, wifi.get("X_SWIFI_ADRES1").getAsString());
                pstmt.setString(5, wifi.get("X_SWIFI_ADRES2").getAsString());
                pstmt.setString(6, wifi.get("X_SWIFI_INSTL_FLOOR").getAsString());
                pstmt.setString(7, wifi.get("X_SWIFI_INSTL_TY").getAsString());
                pstmt.setString(8, wifi.get("X_SWIFI_INSTL_MBY").getAsString());
                pstmt.setString(9, wifi.get("X_SWIFI_SVC_SE").getAsString());
                pstmt.setString(10, wifi.get("X_SWIFI_CMCWR").getAsString());
                pstmt.setString(11, wifi.get("X_SWIFI_CNSTC_YEAR").getAsString());
                pstmt.setString(12, wifi.get("X_SWIFI_INOUT_DOOR").getAsString());
                pstmt.setString(13, wifi.get("X_SWIFI_REMARS3").getAsString());
                pstmt.setString(14, wifi.get("LAT").getAsString());
                pstmt.setString(15, wifi.get("LNT").getAsString());
                pstmt.setString(16, wifi.get("WORK_DTTM").getAsString());
                int count = pstmt.executeUpdate();
                System.out.println(count);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try {
                    if(!pstmt.isClosed()) pstmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

        try{
            if(!conn.isClosed()){
                conn.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(jsonobject);
        System.out.println(total_list_count);

        //Type wifiList = new TypeToken<List<WifiDto>>() {}.getType();

    }
}
