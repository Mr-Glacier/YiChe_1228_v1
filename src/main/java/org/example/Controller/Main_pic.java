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


//        8.下载具有360图片的版本JSON
        CP.Method_8_version_hase360(url_360,out_version_path,in_version_path);






//        测试图片版本接口
//        https://mhapi.yiche.com/hcar/h_photo/api/v1/vr/get_car_pano_list?
//        String ModelID= "2573";
//        String versionurl ="https://mhapi.yiche.com/hcar/h_photo/api/v1/vr/get_car_pano_list?";
//        int CID = 601;
//        String content = CP.Method_RequestConfigAPI(ModelID,versionurl,"12",CID);
//        System.out.println(content);

    }

}
