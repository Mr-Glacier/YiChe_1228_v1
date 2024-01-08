package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.example.Controller.Controller_YiChe;
import org.example.Until.HelpCreateFile;
import org.example.Until.ReadUntil;
import org.example.Until.SaveUntil;
import org.example.Until.SentEmail;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        String mainPath_1 = "E:\\ZKZD2024\\易车网\\";

        String mainPath = mainPath_1+"20240104\\";
        String brandPath = mainPath+"Brand\\";

        String saveBrandPicPath = mainPath+"BrandPic\\";
        String saveModelPicPath = mainPath+"ModelPic\\";
        String Brand_FirstHtmlPath = mainPath+"BrandFirstHtml\\";

        String OnSaleModelPath = mainPath+"OnSaleModel\\";
        String NoSaleModelPath = mainPath+"NoSaleModel\\";

        String versionListPath = mainPath+"versionList\\";

        String version_Config_Path = mainPath+"versionConfig\\";


        HelpCreateFile.Method_Creat_folder(mainPath);
        HelpCreateFile.Method_Creat_folder(brandPath);
        HelpCreateFile.Method_Creat_folder(saveBrandPicPath);
        HelpCreateFile.Method_Creat_folder(saveModelPicPath);

        HelpCreateFile.Method_Creat_folder(Brand_FirstHtmlPath);

        HelpCreateFile.Method_Creat_folder(OnSaleModelPath);
        HelpCreateFile.Method_Creat_folder(NoSaleModelPath);

        HelpCreateFile.Method_Creat_folder(versionListPath);
        HelpCreateFile.Method_Creat_folder(version_Config_Path);


        String Brand_url = "https://car.yiche.com/";


        Controller_YiChe CY = new Controller_YiChe();

//        1.下载保存选车页面,准备获取品牌
//        CY.Method_1_RequestYiche_Brand(brandPath,Brand_url,"Brand.txt");

//        2.解析品牌列表页面
//        CY.Method_2_Analysis_Yiche_Brand(brandPath, "Brand.txt");

//        3.下载品牌图标
//        CY.Method_3_Down_Brand_Pic(saveBrandPicPath);

//        4.下载保存分页第一页
//        CY.Method_4_Down_mid_brand_model_html(Brand_FirstHtmlPath);

//        5.解析入中间表
//        CY.Method_5_GetBrandEnglish(Brand_FirstHtmlPath);





//        6.下载中间表设计
//        CY.Method_6_Down_ModelPage(OnSaleModelPath,NoSaleModelPath);

//        7.解析车型入库
//        CY.Method_7_Analysis_Model(OnSaleModelPath,NoSaleModelPath);

//        13.下载车型图片
//        CY.Method_13_Down_ModelPic(saveModelPicPath);

//        8.下载车型页面
//        CY.Method_8_Down_versionList(versionListPath);

//        9.解析版本
//        CY.Method_9_Analysis_versionList(versionListPath);


        String ConfigAPI = "https://mhapi.yiche.com/hcar/h_car/api/v1/param/get_param_details?";

//        10.下载配置信息
//        CY.Method_10_1_Down_Config_fenzu();
        CY.Method_10_Down_Config_ReadSQL(version_Config_Path,ConfigAPI);
        int GroupNumber = 4963;
//        CY.Method_10_Down_Config_oneGroup(GroupNumber,version_Config_Path,ConfigAPI);


//        11.解析配置信息
//        CY.Method_12_Analysis_config_1_GetCoumens(version_Config_Path);

//        ReadUntil readUntil = new ReadUntil();
//        String conten = readUntil.Method_ReadFile(version_Config_Path+"4963_Config.txt");
//
//        CY.Method_12_Analysis_Config_2_GetCoumns(conten);





//        1229_versionConfigBuChong

        ReadUntil readUntil = new ReadUntil();
        SaveUntil saveUntil = new SaveUntil();
        ArrayList<String> VersionsList = readUntil.Method_ReadbyLine("F:\\ZKZD\\Java项目\\YiChe_1228_v1\\src\\main\\java\\org\\example\\11.txt");

        for (int i = 0; i < VersionsList.size(); i++) {

//            if(i == 35){
                String version_str = VersionsList.get(i);
                String content = CY.Method_11_RequestConfigAPI(version_str,ConfigAPI);

                if (!content.equals("Error")) {
                    JSONObject jsonObject = JSON.parseObject(content);
                    String State = jsonObject.getString("status");
                    if (State.equals("11006")) {
                        do {
                            content = CY.Method_11_RequestConfigAPI(version_str, ConfigAPI);
                            JSONObject jsonObject2 = JSON.parseObject(content);
                            State = jsonObject2.getString("status");
                            Thread.sleep(2000);
                            System.out.println("进入循环");
                        } while (State.equals("11006"));
                    }
                    saveUntil.Method_SaveFile("E:\\ZKZD2024\\易车网\\20240104\\versionConfig3\\"+version_str+"_buchong2.txt",content);
                    System.out.println(i);
                }
            }

//        }
        SentEmail sentEmail = new SentEmail();
        sentEmail.Method_SentEmail(0,"程序完成!");
    }
}