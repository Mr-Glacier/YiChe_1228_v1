import org.example.Until.MD5Until;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Test_main {


   static MD5Until md5Until = new MD5Until();

    public static void main(String[] args) {
        String url = "https://mhapi.yiche.com/hcar/h_car/api/v1/param/get_param_details?cid=508&param={\"carIds\":\"164527,158156,167742,167743\",\"cityId\":\"201\"}";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String param = "{\"carIds\":\"164527,158156,167742,167743\",\"cityId\":\"201\"}";

        String o =  "19DDD1FBDFF065D3A4DA777D2D7A81EC";
        String s = "cid=" + 508 + "&param=" + param + o + timestamp;
        String md5_Str = md5Until.Method_Make_MD5(s);
        System.out.println(md5_Str);



        String main_url = "https://mhapi.yiche.com/hcar/h_car/api/v1/param/get_param_details?cid=508&param=%7B%22carIds%22%3A%22164527%2C158156%2C167742%2C167743%22%2C%22cityId%22%3A%22201%22%7D";
        String resultJson = "Error";
        String Cookie ="CIGUID=849ec451-0627-4ee7-8139-7d0a7233d10a; auto_id=6618b3b2d19037859fee9321a2165348; UserGuid=849ec451-0627-4ee7-8139-7d0a7233d10a; CIGDCID=1f1c18bfd1a13ef3a7c0b2edee9ef3ca; G_CIGDCID=1f1c18bfd1a13ef3a7c0b2edee9ef3ca; selectcity=110100; selectcityid=201; selectcityName=%E5%8C%97%E4%BA%AC; Hm_lvt_610fee5a506c80c9e1a46aa9a2de2e44=1702474832,1703750079; isWebP=true; locatecity=110100; bitauto_ipregion=101.27.236.186%3A%E6%B2%B3%E5%8C%97%E7%9C%81%E4%BF%9D%E5%AE%9A%E5%B8%82%3B201%2C%E5%8C%97%E4%BA%AC%2Cbeijing; yiche_did=5e58467469f6b2c8732f3492175f2a13_._1000_._0_._847319_._905548_._W2311281141108493357; csids=8014_2556_2855_5476_10188_6435_6209_2573_3750_5786; Hm_lpvt_610fee5a506c80c9e1a46aa9a2de2e44=1703842561";
        try{
            Connection.Response res = Jsoup.connect(main_url).method(Connection.Method.GET)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("X-City-Id", "201")
                    .header("X-Ip-Address", "101.27.236.186")
                    .header("X-Platform", "pc")
                    .header("X-Sign", md5_Str)
                    .header("X-User-Guid", "849ec451-0627-4ee7-8139-7d0a7233d10a")
                    .header("Cookie", Cookie)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("Cid", "508")
                    .header("Sec-Ch-Ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"")
                    .header("Sec-Ch-Ua-Mobile", "?0")
                    .header("Sec-Ch-Ua-Platform", "\"Windows\"")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-site")
                    .header("X-Timestamp", timestamp)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true).execute();
            resultJson = res.body();
            Thread.sleep(2080);

            System.out.println(resultJson);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
