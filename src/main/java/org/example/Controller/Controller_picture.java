package org.example.Controller;

import com.alibaba.fastjson.JSON;
import org.example.Dao_pic.Dao_brand_pic;
import org.example.Dao_pic.Dao_model_mid;
import org.example.Dao_pic.Dao_model_pic;
import org.example.Entity_pic.Model_pic;
import org.example.Entity_pic.brand_pic;
import org.example.Entity_pic.model_pic_mid;
import org.example.Until.MD5Until;
import org.example.Until.ReadUntil;
import org.example.Until.SaveUntil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Controller_picture {
//    这个类用于下载易车的车型图片

    private SaveUntil saveUntil = new SaveUntil();
    private ReadUntil readUntil = new ReadUntil();

    public String Method_Down(String url) {
        try {
            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            Thread.sleep(550);
            return document.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public void Method_1_DownBrand(String savePath, String fileName, String url) {
        String content = Method_Down(url);
        saveUntil.Method_SaveFile(savePath + fileName, content);
    }

    public void Method_2_AnalysisBrands(String savePath, String fileName) {
        Dao_brand_pic dao_brand_pic = new Dao_brand_pic(1, 0);
        String content = readUntil.Method_ReadFile(savePath + fileName);

        Document document = Jsoup.parse(content);

//        String url = "https://photo.yiche.com/";
        try {
//            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            Elements Items1 = document.select(".car-tree-master").select(".car-tree-master-group");

            for (int i = 0; i < Items1.size(); i++) {

                Element item2 = Items1.get(i);

                String letter = item2.select(".car-tree-master-letter").text();

                System.out.println(letter);
                Elements Items3 = item2.select(".car-tree-master-info");

                for (Element Items4 : Items3) {
                    String brand_url = "https://photo.yiche.com" + Items4.select(".car-tree-master-item.ka").attr("href");
                    String brand_id = Items4.select(".car-tree-master-item.ka").attr("data-id");
                    String brand_name = Items4.select(".car-tree-master-item-name").text();
//                    System.out.println(brand_id + brand_name + "\n" + brand_url);
                    System.out.println(brand_name);
                    brand_pic bean = new brand_pic();
                    bean.setC_brand_id(brand_id);
                    bean.setC_brand_name(brand_name);
                    bean.setC_brand_url(brand_url);
                    bean.setC_letter(letter);
                    bean.setDownState("否");
                    dao_brand_pic.MethodInsert(bean);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Method_3_Down_Model(String savePath_first) {
        Dao_brand_pic dao_brand_pic = new Dao_brand_pic(1, 0);

        ArrayList<Object> beanList = dao_brand_pic.MethodFind();

        for (Object bean : beanList) {
            brand_pic brand_pic = (org.example.Entity_pic.brand_pic) bean;

            String brand_id = brand_pic.getC_brand_id();
            String brand_url = brand_pic.getC_brand_url();

            int C_ID = brand_pic.getC_ID();
            String content = Method_Down(brand_url);
            System.out.println(brand_id);
            saveUntil.Method_SaveFile(savePath_first + brand_id + ".txt", content);

//            String brand_url_no_sale = brand_url+"?sortType=4&saleStatus=6";
//
//            String content_2 = Method_Down(brand_url_no_sale);
//
//            saveUntil.Method_SaveFile(savePath_noSale+brand_id+".txt", content_2);

            dao_brand_pic.Method_ChangeState(C_ID);
        }

    }

    public void Method_4_Make_mid_model(String savePath) {
        Dao_brand_pic dao_brand_pic = new Dao_brand_pic(1, 0);
        Dao_model_mid dao_model_mid = new Dao_model_mid(1, 1);
        ArrayList<Object> beanList = dao_brand_pic.MethodFind();

        for (Object bean : beanList) {
            brand_pic brand_pic = (org.example.Entity_pic.brand_pic) bean;
            String brand_id = brand_pic.getC_brand_id();
            String brand_name = brand_pic.getC_brand_name();

            String content = readUntil.Method_ReadFile(savePath + brand_id + ".txt");

            Document document = Jsoup.parse(content);

            Elements Items = document.select(".tuku-tab-container").select(".tuku-tab-menu");

            Elements Item_Sale = Items.select("a");

            for (Element item : Item_Sale) {
                String data_sale = item.attr("data-sale");
                String data_masterid = item.attr("data-masterid");
                String page_url = "https://photo.yiche.com" + item.attr("href");
                String type_page = item.text();
                System.out.println(type_page + "\t" + page_url + "\t" + data_masterid);

                model_pic_mid m = new model_pic_mid();

                m.setC_brand_id(data_masterid);
                m.setC_type(data_sale);
                m.setC_page_type(type_page);
                m.setDownState("否");
                m.setC_brand_url(page_url);
                m.setC_brand_name(brand_name);
                dao_model_mid.MethodInsert(m);
            }
        }
    }

    public void Method_5_Down_mid_page(String savePath_isSale, String savePath_noSale) {
        Dao_model_mid dao_model_mid = new Dao_model_mid(1, 1);
        ArrayList<Object> beanList = dao_model_mid.MethodFind();

        for (Object bean : beanList) {
            model_pic_mid m = (model_pic_mid) bean;
            String url = m.getC_brand_url();
            String DownState = m.getDownState();
            String brand_id = m.getC_brand_id();
            String type = m.getC_type();
            int C_ID = m.getC_ID();
            if (DownState.equals("否")) {
                String content = Method_Down(url);

                if (type.equals("1")) {
                    saveUntil.Method_SaveFile(savePath_isSale + brand_id + ".txt", content);
                }
                if (type.equals("2")) {
                    saveUntil.Method_SaveFile(savePath_noSale + brand_id + ".txt", content);
                }
                dao_model_mid.Method_ChangeState(C_ID);
            }
        }
    }

    public void Method_6_Analysis_Model(String savePath_isSale, String savePath_noSale) {
        Dao_model_mid dao_model_mid = new Dao_model_mid(1, 1);
        ArrayList<Object> beanList = dao_model_mid.MethodFind();
        Dao_model_pic dao_model_pic = new Dao_model_pic(1, 2);

        for (Object bean : beanList) {
            model_pic_mid m = (model_pic_mid) bean;
            String url = m.getC_brand_url();
            String DownState = m.getDownState();
            String brand_id = m.getC_brand_id();
            String type = m.getC_type();
            if (type.equals("1")) {
                String contrnt = readUntil.Method_ReadFile(savePath_isSale + brand_id + ".txt");
                Document document = Jsoup.parse(contrnt);
                Elements Items = document.select(".img-item-content");
                for (Element Item2 : Items) {
                    String model_url = Item2.attr("href");
                    String model_name = Item2.select(".img-item-title").text();
                    String model_pic_num = Item2.select(".img-num").text();
                    String model_id = Item2.attr("data-id");
                    String model_first_pic = Item2.select(".img-item-pic").select(".lazyload").attr("data-original");
                    String model_price = Item2.select(".img-item-price").text();

                    Model_pic model_pic = new Model_pic();
                    model_pic.setC_model_first_pic(model_first_pic);
                    model_pic.setC_model_id(model_id);
                    model_pic.setC_model_pic_num(model_pic_num);
                    model_pic.setC_model_price(model_price);
                    model_pic.setC_model_type("在售");
                    model_pic.setC_model_first_pic(model_first_pic);
                    model_pic.setC_model_name(model_name);
                    model_pic.setC_model_url(model_url);
                    System.out.println(model_name);
                    dao_model_pic.MethodInsert(model_pic);
                }
            }
            if (type.equals("2")) {
                String contrnt = readUntil.Method_ReadFile(savePath_noSale + brand_id + ".txt");
                Document document = Jsoup.parse(contrnt);
                Elements Items = document.select(".img-item-content");
                for (Element Item2 : Items) {
                    String model_url = Item2.attr("href");
                    String model_name = Item2.select(".img-item-title").text();
                    String model_pic_num = Item2.select(".img-num").text();
                    String model_id = Item2.attr("data-id");
                    String model_first_pic = Item2.select(".img-item-pic").select(".lazyload").attr("data-original");
                    String model_price = Item2.select(".img-item-price").text();

                    Model_pic model_pic = new Model_pic();
                    model_pic.setC_model_first_pic(model_first_pic);
                    model_pic.setC_model_id(model_id);
                    model_pic.setC_model_pic_num(model_pic_num);
                    model_pic.setC_model_price(model_price);
                    model_pic.setC_model_type("未售/停售");
                    model_pic.setC_model_first_pic(model_first_pic);
                    model_pic.setC_model_name(model_name);
                    model_pic.setC_model_url(model_url);
                    System.out.println(model_name);
                    dao_model_pic.MethodInsert(model_pic);
                }
            }
        }
    }

    public void Method_7_Down_firstModelPic(String save_firstpic) {
        Dao_model_pic dao_model_pic = new Dao_model_pic(1, 2);

        ArrayList<Object> BeanList = dao_model_pic.MethodFind();
        for (Object bean : BeanList) {
            Model_pic model_pic = (Model_pic) bean;
            String first_url = model_pic.getC_model_first_pic();
            int C_ID = model_pic.getC_ID();
            String model_id = model_pic.getC_model_id();

            saveUntil.Method_SavePic(save_firstpic, model_id + ".png", first_url);
            System.out.println(C_ID);
            dao_model_pic.Method_ChangeState(C_ID);
        }
    }


    public void Method_8_version_hase360(String main_url, String outerSavePAth, String innerSavePath) {
        Dao_model_pic dao_model_pic = new Dao_model_pic(1, 2);
        ArrayList<Object> BeanList = dao_model_pic.MethodFind();
        for (Object bean : BeanList) {
            Model_pic model_pic = (Model_pic) bean;
            int C_ID = model_pic.getC_ID();
            String model_id = model_pic.getC_model_id();

            String connect = Method_RequestConfigAPI(model_id, main_url, "12", 601);
            String status = JSON.parseObject(connect).getString("status");
            if (status.equals("1")) {
                saveUntil.Method_SaveFile(outerSavePAth + model_id + ".txt", connect);

                String connect2 = Method_RequestConfigAPI(model_id, main_url, "11", 508);
                String status2 = JSON.parseObject(connect).getString("status");
                if (status2.equals("1")) {
                    saveUntil.Method_SaveFile(innerSavePath + model_id + ".txt", connect2);
                }
                System.out.println(C_ID+"\t下载完成");
                dao_model_pic.Method_ChangeState(C_ID);
            }
        }
    }


    public void Method_9_Analysis_hase36(String outerSavePath, String innerSavePath){
        Dao_model_pic dao_model_pic = new Dao_model_pic(1, 2);
        ArrayList<Object> BeanList = dao_model_pic.MethodFind();
        for (Object bean : BeanList) {
            Model_pic model_pic = (Model_pic) bean;
            int C_ID = model_pic.getC_ID();
            String model_id = model_pic.getC_model_id();

            String content_out  = readUntil.Method_ReadFile(outerSavePath+model_id+".txt");




        }
    }
    public String Method_RequestConfigAPI(String modelID, String ConfigeUrl, String albumType, int Cid) {

//        https://mhapi.yiche.com/hcar/h_photo/api/v1/vr/get_car_pano_lis}t?cid=508&param={"modelId":2573,"albumType":11
        String resultJson = "Error";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String param = "{\"modelId\":" + modelID + ",\"albumType\":" + albumType + "}";

        String o = "19DDD1FBDFF065D3A4DA777D2D7A81EC";
        String s = "cid=" + Cid + "&param=" + param + o + timestamp;
        String md5_Str = MD5Until.Method_Make_MD5(s);
        //System.out.println(md5_Str);
        String Cookie = "CIGUID=849ec451-0627-4ee7-8139-7d0a7233d10a; auto_id=6618b3b2d19037859fee9321a2165348; UserGuid=849ec451-0627-4ee7-8139-7d0a7233d10a; CIGDCID=1f1c18bfd1a13ef3a7c0b2edee9ef3ca; G_CIGDCID=1f1c18bfd1a13ef3a7c0b2edee9ef3ca; selectcity=110100; selectcityid=201; selectcityName=%E5%8C%97%E4%BA%AC; Hm_lvt_610fee5a506c80c9e1a46aa9a2de2e44=1702474832,1703750079,1704166037; pageCount=2; csids=2573_8014_8450_9933_9934_8491_9235_9035_6484_10649; locatecity=110100; bitauto_ipregion=101.27.232.51%3A%E6%B2%B3%E5%8C%97%E7%9C%81%E4%BF%9D%E5%AE%9A%E5%B8%82%3B201%2C%E5%8C%97%E4%BA%AC%2Cbeijing; isWebP=true; Hm_lpvt_610fee5a506c80c9e1a46aa9a2de2e44=1704867714";
        try {
            String param_url = URLEncoder.encode(param, "UTF-8");
            String main_url = ConfigeUrl + "cid=" + Cid + "&param=" + param_url;
//            System.out.println(main_url);
            Connection.Response res = Jsoup.connect(main_url).method(Connection.Method.GET)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("X-City-Id", "201")
                    .header("X-Ip-Address", "101.27.236.186")
                    .header("X-Platform", "pc")
                    .header("X-Sign", md5_Str)
                    .header("X-User-Guid", "849ec451-0627-4ee7-8139-7d0a7233d10a")
                    .header("Cookie", Cookie)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("Cid", String.valueOf(Cid))
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
            Thread.sleep(1080);
//            System.out.println(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }

}
