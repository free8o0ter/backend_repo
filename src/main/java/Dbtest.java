import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Dbtest {

    public static void main(String[] args){
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + "ms_subject.db");

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        String sql = "Create table test01(x1, x2)";
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement(sql);
            int count = pstmt.executeUpdate();
            System.out.println(count);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(!pstmt.isClosed()){
                    pstmt.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }

        try{
            if(!conn.isClosed()){
                conn.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
