import okhttp3.OkHttp;
import okhttp3.OkHttpClient;

public class test {
    double R = 6371;

    public String distance(String lat_1, String lnt_1, String lat_2, String lnt_2){
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

        return String.format("%.4f", dist);
    }

    public double deg2rad(double num){
        return num * (Math.PI/180);
    }

    public static void main(String[] args){
        test tst = new test();
        String far = tst.distance("37.5544069", "126.8998666", "37.55698", "126.89872");
        System.out.println(far);
        int max = Integer.MAX_VALUE-1;
        System.out.println(max);
        max = max*max;
        System.out.println(max);
    }

}
