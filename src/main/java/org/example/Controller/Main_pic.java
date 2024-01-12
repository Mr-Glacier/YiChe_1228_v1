package org.example.Controller;

import org.example.Until.HelpCreateFile;

public class Main_pic {
    public static void main(String[] args) {
        Controller_picture CP = new Controller_picture();

        String Brand_savePath = "F:\\A_ZKZD_2024\\易车图片\\brand\\";
        String Model_savePath = "F:\\A_ZKZD_2024\\易车图片\\model2\\";

        String Model_savePath_first = Model_savePath+"model_firest\\";
        String Model_savePath_isSale = Model_savePath+"Model_isSale\\";
        String Model_savePath_noSale = Model_savePath+"Model_noSale\\";

        String model_savePath_firstPic = Model_savePath+"Model_firstPic\\";
        HelpCreateFile.Method_Creat_folder(Model_savePath_first);
        HelpCreateFile.Method_Creat_folder(Model_savePath_isSale);
        HelpCreateFile.Method_Creat_folder(Model_savePath_noSale);
        HelpCreateFile.Method_Creat_folder(model_savePath_firstPic);

        String brand_pic_url = "https://photo.yiche.com/";


//        1.下载图片品牌地址
//        CP.Method_1_DownBrand(Brand_savePath,"pic_brand.txt",brand_pic_url);

//        2.解析品牌
//        CP.Method_2_AnalysisBrands(Brand_savePath,"pic_brand.txt");

//        3.下载车型页面
//        CP.Method_3_Down_Model(Model_savePath_first);

//        4.制作中间表
//        CP.Method_4_Make_mid_model(Model_savePath_first);

//        5.下载所有品牌页面
//        CP.Method_5_Down_mid_page(Model_savePath_isSale,Model_savePath_noSale);


//        6.解析车型页面
//        CP.Method_6_Analysis_Model(Model_savePath_isSale,Model_savePath_noSale);

//        7.下载车型的第一张图片
//        CP.Method_7_Down_firstModelPic(model_savePath_firstPic);




        String url_360 = "https://mhapi.yiche.com/hcar/h_photo/api/v1/vr/get_car_pano_list?";

        String version_save_path = "F:\\A_ZKZD_2024\\易车图片\\version\\";
        String in_version_path = version_save_path+"in_version\\";
        String out_version_path = version_save_path+"out_version\\";
        HelpCreateFile.Method_Creat_folder(in_version_path);
        HelpCreateFile.Method_Creat_folder(out_version_path);



//        8.下载具有360图片的版本JSON
//        CP.Method_8_version_hase360(url_360,out_version_path,in_version_path);


//        9.解析具有360图片的版本
//        CP.Method_9_Analysis_hase36(out_version_path,in_version_path);




        String in_version_path_pic_JSON =  version_save_path+"in_version_pic\\";
        String out_version_path_pic_JSON =  version_save_path+"out_version_pic\\";
        HelpCreateFile.Method_Creat_folder(in_version_path_pic_JSON);
        HelpCreateFile.Method_Creat_folder(out_version_path_pic_JSON);

//        10.下载内外360的数据
//        CP.Method_10_Down_version360info(out_version_path_pic_JSON,in_version_path_pic_JSON);

//        11.解析360图片地址
//        CP.Method_11_Analysis_version360info(out_version_path_pic_JSON,in_version_path_pic_JSON);



//        12.下载360 out 图片
        String out_360_picSavePath = "F:\\A_ZKZD_2024\\易车图片\\version_360\\version_out_360\\";
//        CP.Method_12_Down_360outPic(out_360_picSavePath);




//        13.下载车型的版本
        String model_version_Path = "F:\\A_ZKZD_2024\\易车图片\\model_all\\";

//        CP.Method_13_Down_ModelHTML(model_version_Path);


//        14.解析版本
//        CP.Method_14_Analysis_ModelHTML(model_version_Path);

//        15.下载
        String firsthtml = "F:\\A_ZKZD_2024\\易车图片\\Pic_外观_前排_后排\\model_firstHTML\\";
        CP.Method_15_Down_外观_前排_后排(firsthtml);


//        测试图片版本接口
//        https://mhapi.yiche.com/hcar/h_photo/api/v1/vr/get_car_pano_list?
//        String ModelID= "2573";
//        String versionurl ="https://mhapi.yiche.com/hcar/h_photo/api/v1/vr/get_car_pano_list?";
//        int CID = 601;
//        String content = CP.Method_RequestConfigAPI(ModelID,versionurl,"12",CID);
//        System.out.println(content);
    }
}
