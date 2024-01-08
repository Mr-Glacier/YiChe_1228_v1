package org.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;
import org.example.Dao.*;
import org.example.Entity.*;
import org.example.Until.ReadUntil;
import org.example.Until.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller_YiChe {

    private SaveUntil saveUntil = new SaveUntil();
    private ReadUntil readUntil = new ReadUntil();

    //    公用方法,发送请求,获取页面数据.
    public String Method_JsoupRequest(String url) {
        try {
            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            Thread.sleep(550);
            return document.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public void Method_1_RequestYiche_Brand(String savePath, String main_url, String fileName) {
        try {
            Document document = Jsoup.parse(new URL(main_url).openStream(), "UTF-8", main_url);
//            System.out.println(document);
            saveUntil.Method_SaveFile(savePath + fileName, document.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Method_2_Analysis_Yiche_Brand(String path, String fileName) {
        Dao_brand dao_brand = new Dao_brand(0, 0);

        String content = readUntil.Method_ReadFile(path + fileName);
//        System.out.println(content);

        Document document = Jsoup.parse(content);

        Elements Item = document.select(".brand-list");

        Elements Item_zimu = Item.select(".brand-list-item");

        for (int i = 0; i < Item_zimu.size(); i++) {
            Element Item_letter = Item_zimu.get(i).selectFirst(".item-letter");
            String Brand_letter = Item_letter.text();

            Elements Item_Brands = Item_zimu.get(i).select(".item-brand");
            for (int j = 0; j < Item_Brands.size(); j++) {
                Element Item_One_Brand = Item_Brands.get(j);

                String C_brand_id = Item_One_Brand.attr("data-id");
                String C_brand_name = Item_One_Brand.attr("data-name");
                String brand_url = "https://car.yiche.com" + Item_One_Brand.select("a").attr("href");
                String brand_pictureurl = "https:" + Item_One_Brand.select(".brand-icon.lazyload").attr("data-original");
                Bean_brand bean_brand = new Bean_brand();
                bean_brand.setC_brand_id(C_brand_id);
                bean_brand.setC_brand_name(C_brand_name);
                bean_brand.setC_brand_url(brand_url);
                bean_brand.setC_brand_picurl(brand_pictureurl);
                bean_brand.setC_letter(Brand_letter);
                bean_brand.setC_DownState("否");
                bean_brand.setC_DownTime("--");
                dao_brand.MethodInsert(bean_brand);

            }
        }
    }

    public void Method_3_Down_Brand_Pic(String savePath) {
        Dao_brand dao_brand = new Dao_brand(0, 0);
        ArrayList<Object> beanList = dao_brand.MethodFind();

        for (Object bean : beanList) {
            Bean_brand bean_brand = (Bean_brand) bean;
            String picurl = bean_brand.getC_brand_picurl();
            String brand_id = bean_brand.getC_brand_id();
            saveUntil.Method_SavePic(savePath, brand_id + ".png", picurl);
        }
    }

    public void Method_4_Down_mid_brand_model_html(String savepath) {
        Dao_brand dao_brand = new Dao_brand(0, 0);
        ArrayList<Object> beanList = dao_brand.MethodFind();

        for (Object bean : beanList) {
            Bean_brand bean_brand = (Bean_brand) bean;
            String url = bean_brand.getC_brand_url();
            String brand_id = bean_brand.getC_brand_id();
            String Brand_html = Method_JsoupRequest(url);
            String Brand_name = bean_brand.getC_brand_name();

            int C_ID = bean_brand.getC_ID();

            if (!Brand_html.equals("Error")) {
                System.out.println(Brand_name);
                saveUntil.Method_SaveFile(savepath + brand_id + "_html.txt", Brand_html);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                String time = df.format(new Date());
                dao_brand.Method_1_ChangeDownState(C_ID, time);
            }
        }
    }

    public void Method_5_GetBrandEnglish(String path) {

        Dao_brand dao_brand = new Dao_brand(0, 0);
        Dao_brand dao_brand2 = new Dao_brand(0, 1);

        ArrayList<Object> BrandList = dao_brand.MethodFind();
        for (int i = 0; i < BrandList.size(); i++) {
            int ID = ((Bean_brand) BrandList.get(i)).getC_ID();
            String BrandID = ((Bean_brand) BrandList.get(i)).getC_brand_id();
            String BrandName = ((Bean_brand) BrandList.get(i)).getC_brand_name();

            System.out.println(BrandName);
            String content = readUntil.Method_ReadFile(path + BrandID + "_html.txt");

            Document document = Jsoup.parse(content);
            Elements Items1 = document.select(".search-result-list");
            //System.out.println(Items1.size());
            Elements Items2 = Items1.select(".search-result-list-item");
            System.out.println(Items2.size());
            Bean_mid_brand brand_2 = new Bean_mid_brand();
            if (Items2.size() == 0) {
                System.out.println("无数据");
                brand_2.setC_BrandID(BrandID);
                brand_2.setC_BrandName(BrandName);
                brand_2.setC_HavingModel(0);
                brand_2.setC_BrandEnglish("无");
                brand_2.setC_OnSalePageUrl("无");
                dao_brand2.MethodInsert(brand_2);
            } else {
                Element Items3 = Items2.get(0).select("a").get(0);
                String Url = "https://car.yiche.com" + Items3.attr("href");
                try {
                    Document document1 = Jsoup.parse(new URL(Url).openStream(), "UTF-8", Url);
                    Element ItemsB1 = document1.select(".yiche-breadcrumb_item-link").get(2);
                    String saleUrl = "https://car.yiche.com" + ItemsB1.attr("href");
                    String English = ItemsB1.attr("href").replace("/", "");
                    brand_2.setC_BrandID(BrandID);
                    brand_2.setC_BrandName(BrandName);
                    brand_2.setC_HavingModel(1);
                    brand_2.setC_OnSalePageUrl(saleUrl);
                    brand_2.setC_NotSalePageUrl(saleUrl + "?sale=1");
                    brand_2.setC_BrandEnglish(English);
                    dao_brand2.MethodInsert(brand_2);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }

    public void Method_6_Down_ModelPage(String yesSalePath, String noSalePath) {
        Dao_mid_brand dao_mid_brand = new Dao_mid_brand(0, 1);

        ArrayList<Object> BeanList = dao_mid_brand.MethodFind_midBrand();
        for (Object Bean : BeanList) {
            Bean_mid_brand bean_mid_brand = (Bean_mid_brand) Bean;

            String BrandID = bean_mid_brand.getC_BrandID();
            int C_ID = bean_mid_brand.getC_ID();
            String onSale_url = bean_mid_brand.getC_OnSalePageUrl();
            String NoSale_url = bean_mid_brand.getC_NotSalePageUrl();

            String yes_DownState = bean_mid_brand.getC_DownState();

            String No_DownState = bean_mid_brand.getC_DownStateNoSale();

            if (yes_DownState.equals("-")) {
                String sale_Content = Method_JsoupRequest(onSale_url);
                if (!sale_Content.equals("Error")) {
                    saveUntil.Method_SaveFile(yesSalePath + BrandID + "_modelOnSale.txt", sale_Content);
                    dao_mid_brand.Method_ChangState(C_ID);
                }
            }


            if (No_DownState.equals("-")) {
                String noSale_Content = Method_JsoupRequest(NoSale_url);
                if (!noSale_Content.equals("Error")) {
                    saveUntil.Method_SaveFile(noSalePath + BrandID + "_modelNoSale.txt", noSale_Content);
                    dao_mid_brand.Method_ChangState2(C_ID);
                }
            }

            System.out.println(C_ID);
        }
    }

    public void Method_7_Analysis_Model(String yesSalePath, String noSalePath) {

        Dao_mid_brand dao_mid_brand = new Dao_mid_brand(0, 1);
        Dao_model dao_model = new Dao_model(0, 2);

        ArrayList<Object> BeanList = dao_mid_brand.MethodFind_midBrand();
        for (Object bean : BeanList) {
            Bean_mid_brand bean_mid_brand = (Bean_mid_brand) bean;

            String brand_id = bean_mid_brand.getC_BrandID();
            String brand_name = bean_mid_brand.getC_BrandName();


            String Salecontent = readUntil.Method_ReadFile(yesSalePath + brand_id + "_modelOnSale.txt");
            Document Saledocument = Jsoup.parse(Salecontent);
            Elements Items1 = Saledocument.select(".img-info-layout-vertical.img-info-layout-vertical-center.img-info-layout-vertical-180120");
            if (Items1.size() > 0) {
                for (Element element : Items1) {
                    String ModelID = element.attr("data-id");
                    String ModelName = element.select(".lazyload").attr("alt");
                    String ModelUrl = "https://car.yiche.com" + element.select("a").attr("href");
                    String ModelPicUrl = element.select(".lazyload").attr("data-original");

                    Elements Items2 = element.select("span");
                    String tag = "";
                    if (Items2.size() > 0) {
                        tag = Items2.text();
                        System.out.println(tag);
                    } else {
                        tag = "无";
                    }
                    Bean_model model = new Bean_model();
                    model.setC_ModelID(ModelID);
                    model.setC_ModelName(ModelName);
                    model.setC_ModelUrl(ModelUrl);
                    model.setC_ModelPicUrl(ModelPicUrl);
                    model.setC_BrandID(brand_id);
                    model.setC_BrandName(brand_name);
                    model.setC_DownState("否");
                    model.setC_Tag(tag);
                    model.setC_isSale(1);
                    dao_model.MethodInsert(model);
                }
            } else {
                System.out.println(brand_name + "无车型");
            }


            String NOSalContent = readUntil.Method_ReadFile(noSalePath + brand_id + "_modelNoSale.txt");
            Document NoSaledocument = Jsoup.parse(NOSalContent);

            Elements ItemsB1 = NoSaledocument.select(".img-info-layout-vertical.img-info-layout-vertical-center.img-info-layout-vertical-180120");
            if (ItemsB1.size() > 0) {
                for (Element element : ItemsB1) {
                    String ModelID = element.attr("data-id");
                    String ModelName = element.select(".lazyload").attr("alt");
                    String ModelUrl = "https://car.yiche.com" + element.select("a").attr("href");
                    String ModelPicUrl = element.select(".lazyload").attr("data-original");
                    Elements Items2 = element.select("span");
                    String tag = "";
                    if (Items2.size() > 0) {
                        tag = Items2.text();
                        System.out.println(tag);
                    } else {
                        tag = "无";
                    }
                    Bean_model model = new Bean_model();
                    model.setC_ModelID(ModelID);
                    model.setC_ModelName(ModelName);
                    model.setC_ModelUrl(ModelUrl);
                    model.setC_ModelPicUrl(ModelPicUrl);
                    model.setC_BrandID(brand_id);
                    model.setC_BrandName(brand_name);
                    model.setC_DownState("否");
                    model.setC_Tag(tag);
                    model.setC_isSale(0);
                    dao_model.MethodInsert(model);

                }
            }
        }
    }

    public void Method_8_Down_versionList(String versionPath_onSale) {
        try {
            Dao_model dao_model = new Dao_model(0, 2);
            ArrayList<Object> BeanList = dao_model.MethodFind();
            for (Object bean : BeanList) {
                Bean_model bean_model = (Bean_model) bean;
                String model_id = bean_model.getC_ModelID();
                String brand_id = bean_model.getC_BrandID();
                System.out.println(model_id);
                int C_ID = bean_model.getC_ID();
                String model_url = bean_model.getC_ModelUrl();
                String DownState = bean_model.getC_DownState();
                int C_isSale = bean_model.getC_isSale();

                if (C_isSale == 1) {
                    String content = Method_JsoupRequest(model_url);
                    if (!content.equals("Error")) {
                        saveUntil.Method_SaveFile(versionPath_onSale + model_id + "_version_isSale.txt", content);
                        dao_model.Method_ChangeDownState(C_ID);
                    }
                }

                if (C_isSale == 0) {
                    String content = Method_JsoupRequest(model_url);
                    if (!content.equals("Error")) {
                        saveUntil.Method_SaveFile(versionPath_onSale + model_id + "_version_NoSale.txt", content);
                        dao_model.Method_ChangeDownState(C_ID);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Method_9_Analysis_versionList(String versionPath) {
        try {
            Dao_model dao_model = new Dao_model(0, 2);
            Dao_version dao_version = new Dao_version(0, 3);
            ArrayList<Object> BeanList = dao_model.MethodFind();
            for (Object bean : BeanList) {
                Bean_model bean_model = (Bean_model) bean;
                String model_id = bean_model.getC_ModelID();
                String brand_id = bean_model.getC_BrandID();
                int C_isSale = bean_model.getC_isSale();
                String model_name = bean_model.getC_ModelName();
                System.out.println(model_id + "\t" + model_name);
                if (C_isSale == 1) {
                    String content = readUntil.Method_ReadFile(versionPath + model_id + "_version_isSale.txt");
                    Elements Item1 = Jsoup.parse(content).select("script[type=text/javascript]");

                    String Text_Content = URLDecoder.decode(String.valueOf(Item1.get(4)), "utf-8");
                    int ju = Text_Content.indexOf("{\"conditionList\"");
                    if (ju > 0) {
                        String mainJSON = Text_Content.substring(Text_Content.indexOf("{\"conditionList\""), Text_Content.indexOf("\",serialSalesListData:\""));
                        JSONObject jsonObject = JSON.parseObject(mainJSON);
                        JSONArray notSaleCarList = jsonObject.getJSONArray("notSaleCarList");
                        if (notSaleCarList.size() != 0) {
                            for (int i = 0; i < notSaleCarList.size(); i++) {
                                JSONObject Item1_notSale = notSaleCarList.getJSONObject(i);
                                String year_carList = Item1_notSale.getString("year");
                                String saleStatusList = Item1_notSale.getString("saleStatusList");

                                JSONArray powerList_notSale = Item1_notSale.getJSONArray("powerList");
                                if (powerList_notSale.size() != 0) {
                                    for (int j = 0; j < powerList_notSale.size(); j++) {
                                        JSONObject powerList_notSale_item = powerList_notSale.getJSONObject(j);
                                        String powerName = powerList_notSale_item.getString("powerName");
                                        JSONArray carList = powerList_notSale_item.getJSONArray("carList");
                                        for (int k = 0; k < carList.size(); k++) {
                                            JSONObject carList_item = carList.getJSONObject(k);
                                            String C_id = carList_item.getString("id");
                                            String C_name = carList_item.getString("name");
                                            String C_year = carList_item.getString("year");
                                            String C_saleStatus = carList_item.getString("saleStatus");
                                            String C_localDealPrice = carList_item.getString("localDealPrice");
                                            String C_locaLatestDealPrice = carList_item.getString("locaLatestDealPrice");
                                            String C_imageUrl = carList_item.getString("imageUrl");
                                            String C_entireDealPrice = carList_item.getString("entireDealPrice");
                                            String C_dealFinalPriceInfo = carList_item.getString("dealFinalPriceInfo");
                                            String C_serialId = carList_item.getString("serialId");
                                            String C_serialName = carList_item.getString("serialName");
                                            String C_serialSecondLevel = carList_item.getString("serialSecondLevel");
                                            String C_usedCarPrice = carList_item.getString("usedCarPrice");
                                            String C_dropPrice = carList_item.getString("dropPrice");
                                            String C_dealerDropAfterPrice = carList_item.getString("dealerDropAfterPrice");
                                            String C_maintainValue = carList_item.getString("maintainValue");
                                            String C_cityReferencePrice = carList_item.getString("cityReferencePrice");
                                            String C_subscribeStatus = carList_item.getString("subscribeStatus");
                                            String C_referPrice = carList_item.getString("referPrice");
                                            String C_discountPrice = carList_item.getString("discountPrice");
                                            String C_subsidizedReferPrice = carList_item.getString("subsidizedReferPrice");
                                            String C_greenStandards = carList_item.getString("greenStandards");
                                            String C_haveParam = carList_item.getString("haveParam");
                                            String C_pvPercent = carList_item.getString("pvPercent");
                                            String C_hasImageFlag = carList_item.getString("hasImageFlag");
                                            String C_marketDate = carList_item.getString("marketDate");
                                            String C_params = carList_item.getString("params");
                                            String C_photoInfo = carList_item.getString("photoInfo");
                                            String C_yiCheHuiTag = carList_item.getString("yiCheHuiTag");
                                            String C_loanVo = carList_item.getString("loanVo");
                                            String C_businessCardList = carList_item.getString("businessCardList");
                                            String C_oilFuelTypeInt = carList_item.getString("oilFuelTypeInt");
                                            String C_fuelUnitType = carList_item.getString("fuelUnitType");
                                            String C_electricRechargeMileage = carList_item.getString("electricRechargeMileage");
                                            String C_oilWear = carList_item.getString("oilWear");
                                            String C_masterId = carList_item.getString("masterId");
                                            String C_masterName = carList_item.getString("masterName");
                                            String C_logoUrl = carList_item.getString("logoUrl");
                                            String C_logoUrlWp = carList_item.getString("logoUrlWp");
                                            String C_masterAllSpell = carList_item.getString("masterAllSpell");
                                            String C_minDealPrice = carList_item.getString("minDealPrice");
                                            String C_invoiceCount = carList_item.getString("invoiceCount");
                                            String C_brandId = carList_item.getString("brandId");
                                            String C_brandName = carList_item.getString("brandName");
                                            String C_imageUrlWp = carList_item.getString("imageUrlWp");
                                            String C_tranAndGearNum = carList_item.getString("tranAndGearNum");


                                            JSONArray tagList = carList_item.getJSONArray("tagList");

                                            String C_styleType = "无";
                                            String C_styleType_value = "无";
                                            if (tagList.size() != 0) {
                                                C_styleType = tagList.getJSONObject(0).getString("styleType");
                                                C_styleType_value = tagList.getJSONObject(0).getString("value");
                                            }


                                            Bean_version bean_version = new Bean_version();

                                            bean_version.setC_model_id(model_id);
                                            bean_version.setC_sourceList("notSaleCarList");
                                            bean_version.setC_year_carList(year_carList);
                                            bean_version.setC_saleStatusList(saleStatusList);
                                            bean_version.setC_powerName(powerName);

                                            bean_version.setC_version_id(C_id);
                                            bean_version.setC_name(C_name);
                                            bean_version.setC_year(C_year);
                                            bean_version.setC_saleStatus(C_saleStatus);
                                            bean_version.setC_localDealPrice(C_localDealPrice);
                                            bean_version.setC_locaLatestDealPrice(C_locaLatestDealPrice);
                                            bean_version.setC_imageUrl(C_imageUrl);
                                            bean_version.setC_entireDealPrice(C_entireDealPrice);
                                            bean_version.setC_dealFinalPriceInfo(C_dealFinalPriceInfo);
                                            bean_version.setC_serialId(C_serialId);
                                            bean_version.setC_serialName(C_serialName);
                                            bean_version.setC_serialSecondLevel(C_serialSecondLevel);
                                            bean_version.setC_usedCarPrice(C_usedCarPrice);
                                            bean_version.setC_dropPrice(C_dropPrice);
                                            bean_version.setC_dealerDropAfterPrice(C_dealerDropAfterPrice);
                                            bean_version.setC_maintainValue(C_maintainValue);
                                            bean_version.setC_cityReferencePrice(C_cityReferencePrice);
                                            bean_version.setC_subscribeStatus(C_subscribeStatus);
                                            bean_version.setC_referPrice(C_referPrice);
                                            bean_version.setC_discountPrice(C_discountPrice);
                                            bean_version.setC_subsidizedReferPrice(C_subsidizedReferPrice);
                                            bean_version.setC_greenStandards(C_greenStandards);
                                            bean_version.setC_haveParam(C_haveParam);
                                            bean_version.setC_pvPercent(C_pvPercent);
                                            bean_version.setC_hasImageFlag(C_hasImageFlag);
                                            bean_version.setC_marketDate(C_marketDate);
                                            bean_version.setC_params(C_params);
                                            bean_version.setC_photoInfo(C_photoInfo);
                                            bean_version.setC_yiCheHuiTag(C_yiCheHuiTag);
                                            bean_version.setC_loanVo(C_loanVo);
                                            bean_version.setC_businessCardList(C_businessCardList);
                                            bean_version.setC_oilFuelTypeInt(C_oilFuelTypeInt);
                                            bean_version.setC_fuelUnitType(C_fuelUnitType);
                                            bean_version.setC_electricRechargeMileage(C_electricRechargeMileage);
                                            bean_version.setC_oilWear(C_oilWear);
                                            bean_version.setC_masterId(C_masterId);
                                            bean_version.setC_masterName(C_masterName);
                                            bean_version.setC_logoUrl(C_logoUrl);
                                            bean_version.setC_logoUrlWp(C_logoUrlWp);
                                            bean_version.setC_masterAllSpell(C_masterAllSpell);
                                            bean_version.setC_minDealPrice(C_minDealPrice);
                                            bean_version.setC_invoiceCount(C_invoiceCount);
                                            bean_version.setC_brandId(C_brandId);
                                            bean_version.setC_brandName(C_brandName);
                                            bean_version.setC_imageUrlWp(C_imageUrlWp);
                                            bean_version.setC_tranAndGearNum(C_tranAndGearNum);
                                            bean_version.setC_styleType(C_styleType);
                                            bean_version.setC_styleType_value(C_styleType_value);
                                            dao_version.MethodInsert(bean_version);
                                        }
                                    }
                                }
                            }
                        }
                        JSONArray waitSaleCarList = jsonObject.getJSONArray("waitSaleCarList");
                        if (waitSaleCarList.size() != 0) {
                            for (int i = 0; i < waitSaleCarList.size(); i++) {
                                JSONObject Item1_notSale = waitSaleCarList.getJSONObject(i);
                                String year_carList = Item1_notSale.getString("year");
                                String saleStatusList = Item1_notSale.getString("saleStatusList");

                                JSONArray powerList_notSale = Item1_notSale.getJSONArray("powerList");
                                if (powerList_notSale.size() != 0) {
                                    for (int j = 0; j < powerList_notSale.size(); j++) {
                                        JSONObject powerList_notSale_item = powerList_notSale.getJSONObject(j);
                                        String powerName = powerList_notSale_item.getString("powerName");
                                        JSONArray carList = powerList_notSale_item.getJSONArray("carList");
                                        for (int k = 0; k < carList.size(); k++) {
                                            JSONObject carList_item = carList.getJSONObject(k);
                                            String C_id = carList_item.getString("id");
                                            String C_name = carList_item.getString("name");
                                            String C_year = carList_item.getString("year");
                                            String C_saleStatus = carList_item.getString("saleStatus");
                                            String C_localDealPrice = carList_item.getString("localDealPrice");
                                            String C_locaLatestDealPrice = carList_item.getString("locaLatestDealPrice");
                                            String C_imageUrl = carList_item.getString("imageUrl");
                                            String C_entireDealPrice = carList_item.getString("entireDealPrice");
                                            String C_dealFinalPriceInfo = carList_item.getString("dealFinalPriceInfo");
                                            String C_serialId = carList_item.getString("serialId");
                                            String C_serialName = carList_item.getString("serialName");
                                            String C_serialSecondLevel = carList_item.getString("serialSecondLevel");
                                            String C_usedCarPrice = carList_item.getString("usedCarPrice");
                                            String C_dropPrice = carList_item.getString("dropPrice");
                                            String C_dealerDropAfterPrice = carList_item.getString("dealerDropAfterPrice");
                                            String C_maintainValue = carList_item.getString("maintainValue");
                                            String C_cityReferencePrice = carList_item.getString("cityReferencePrice");
                                            String C_subscribeStatus = carList_item.getString("subscribeStatus");
                                            String C_referPrice = carList_item.getString("referPrice");
                                            String C_discountPrice = carList_item.getString("discountPrice");
                                            String C_subsidizedReferPrice = carList_item.getString("subsidizedReferPrice");
                                            String C_greenStandards = carList_item.getString("greenStandards");
                                            String C_haveParam = carList_item.getString("haveParam");
                                            String C_pvPercent = carList_item.getString("pvPercent");
                                            String C_hasImageFlag = carList_item.getString("hasImageFlag");
                                            String C_marketDate = carList_item.getString("marketDate");
                                            String C_params = carList_item.getString("params");
                                            String C_photoInfo = carList_item.getString("photoInfo");
                                            String C_yiCheHuiTag = carList_item.getString("yiCheHuiTag");
                                            String C_loanVo = carList_item.getString("loanVo");
                                            String C_businessCardList = carList_item.getString("businessCardList");
                                            String C_oilFuelTypeInt = carList_item.getString("oilFuelTypeInt");
                                            String C_fuelUnitType = carList_item.getString("fuelUnitType");
                                            String C_electricRechargeMileage = carList_item.getString("electricRechargeMileage");
                                            String C_oilWear = carList_item.getString("oilWear");
                                            String C_masterId = carList_item.getString("masterId");
                                            String C_masterName = carList_item.getString("masterName");
                                            String C_logoUrl = carList_item.getString("logoUrl");
                                            String C_logoUrlWp = carList_item.getString("logoUrlWp");
                                            String C_masterAllSpell = carList_item.getString("masterAllSpell");
                                            String C_minDealPrice = carList_item.getString("minDealPrice");
                                            String C_invoiceCount = carList_item.getString("invoiceCount");
                                            String C_brandId = carList_item.getString("brandId");
                                            String C_brandName = carList_item.getString("brandName");
                                            String C_imageUrlWp = carList_item.getString("imageUrlWp");
                                            String C_tranAndGearNum = carList_item.getString("tranAndGearNum");


                                            JSONArray tagList = carList_item.getJSONArray("tagList");

                                            String C_styleType = "无";
                                            String C_styleType_value = "无";
                                            if (tagList.size() != 0) {
                                                C_styleType = tagList.getJSONObject(0).getString("styleType");
                                                C_styleType_value = tagList.getJSONObject(0).getString("value");
                                            }


                                            Bean_version bean_version = new Bean_version();

                                            bean_version.setC_model_id(model_id);
                                            bean_version.setC_sourceList("waitSaleCarList");
                                            bean_version.setC_year_carList(year_carList);
                                            bean_version.setC_saleStatusList(saleStatusList);
                                            bean_version.setC_powerName(powerName);

                                            bean_version.setC_version_id(C_id);
                                            bean_version.setC_name(C_name);
                                            bean_version.setC_year(C_year);
                                            bean_version.setC_saleStatus(C_saleStatus);
                                            bean_version.setC_localDealPrice(C_localDealPrice);
                                            bean_version.setC_locaLatestDealPrice(C_locaLatestDealPrice);
                                            bean_version.setC_imageUrl(C_imageUrl);
                                            bean_version.setC_entireDealPrice(C_entireDealPrice);
                                            bean_version.setC_dealFinalPriceInfo(C_dealFinalPriceInfo);
                                            bean_version.setC_serialId(C_serialId);
                                            bean_version.setC_serialName(C_serialName);
                                            bean_version.setC_serialSecondLevel(C_serialSecondLevel);
                                            bean_version.setC_usedCarPrice(C_usedCarPrice);
                                            bean_version.setC_dropPrice(C_dropPrice);
                                            bean_version.setC_dealerDropAfterPrice(C_dealerDropAfterPrice);
                                            bean_version.setC_maintainValue(C_maintainValue);
                                            bean_version.setC_cityReferencePrice(C_cityReferencePrice);
                                            bean_version.setC_subscribeStatus(C_subscribeStatus);
                                            bean_version.setC_referPrice(C_referPrice);
                                            bean_version.setC_discountPrice(C_discountPrice);
                                            bean_version.setC_subsidizedReferPrice(C_subsidizedReferPrice);
                                            bean_version.setC_greenStandards(C_greenStandards);
                                            bean_version.setC_haveParam(C_haveParam);
                                            bean_version.setC_pvPercent(C_pvPercent);
                                            bean_version.setC_hasImageFlag(C_hasImageFlag);
                                            bean_version.setC_marketDate(C_marketDate);
                                            bean_version.setC_params(C_params);
                                            bean_version.setC_photoInfo(C_photoInfo);
                                            bean_version.setC_yiCheHuiTag(C_yiCheHuiTag);
                                            bean_version.setC_loanVo(C_loanVo);
                                            bean_version.setC_businessCardList(C_businessCardList);
                                            bean_version.setC_oilFuelTypeInt(C_oilFuelTypeInt);
                                            bean_version.setC_fuelUnitType(C_fuelUnitType);
                                            bean_version.setC_electricRechargeMileage(C_electricRechargeMileage);
                                            bean_version.setC_oilWear(C_oilWear);
                                            bean_version.setC_masterId(C_masterId);
                                            bean_version.setC_masterName(C_masterName);
                                            bean_version.setC_logoUrl(C_logoUrl);
                                            bean_version.setC_logoUrlWp(C_logoUrlWp);
                                            bean_version.setC_masterAllSpell(C_masterAllSpell);
                                            bean_version.setC_minDealPrice(C_minDealPrice);
                                            bean_version.setC_invoiceCount(C_invoiceCount);
                                            bean_version.setC_brandId(C_brandId);
                                            bean_version.setC_brandName(C_brandName);
                                            bean_version.setC_imageUrlWp(C_imageUrlWp);
                                            bean_version.setC_tranAndGearNum(C_tranAndGearNum);
                                            bean_version.setC_styleType(C_styleType);
                                            bean_version.setC_styleType_value(C_styleType_value);

                                            dao_version.MethodInsert(bean_version);
                                        }
                                    }
                                }
                            }
                        }
                        JSONArray onSaleCarList = jsonObject.getJSONArray("onSaleCarList");
                        if (onSaleCarList.size() != 0) {
                            for (int i = 0; i < onSaleCarList.size(); i++) {
//                            System.out.println(onSaleCarList);
                                JSONObject Item1_notSale = onSaleCarList.getJSONObject(i);
                                String year_carList = Item1_notSale.getString("year");
                                String saleStatusList = Item1_notSale.getString("saleStatusList");

                                JSONArray powerList_notSale = Item1_notSale.getJSONArray("powerList");
                                if (powerList_notSale.size() != 0) {
                                    for (int j = 0; j < powerList_notSale.size(); j++) {
                                        JSONObject powerList_notSale_item = powerList_notSale.getJSONObject(j);
                                        String powerName = powerList_notSale_item.getString("powerName");
                                        JSONArray carList = powerList_notSale_item.getJSONArray("carList");
                                        for (int k = 0; k < carList.size(); k++) {
                                            JSONObject carList_item = carList.getJSONObject(k);
                                            String C_id = carList_item.getString("id");
                                            String C_name = carList_item.getString("name");
                                            String C_year = carList_item.getString("year");
                                            String C_saleStatus = carList_item.getString("saleStatus");
                                            String C_localDealPrice = carList_item.getString("localDealPrice");
                                            String C_locaLatestDealPrice = carList_item.getString("locaLatestDealPrice");
                                            String C_imageUrl = carList_item.getString("imageUrl");
                                            String C_entireDealPrice = carList_item.getString("entireDealPrice");
                                            String C_dealFinalPriceInfo = carList_item.getString("dealFinalPriceInfo");
                                            String C_serialId = carList_item.getString("serialId");
                                            String C_serialName = carList_item.getString("serialName");
                                            String C_serialSecondLevel = carList_item.getString("serialSecondLevel");
                                            String C_usedCarPrice = carList_item.getString("usedCarPrice");
                                            String C_dropPrice = carList_item.getString("dropPrice");
                                            String C_dealerDropAfterPrice = carList_item.getString("dealerDropAfterPrice");
                                            String C_maintainValue = carList_item.getString("maintainValue");
                                            String C_cityReferencePrice = carList_item.getString("cityReferencePrice");
                                            String C_subscribeStatus = carList_item.getString("subscribeStatus");
                                            String C_referPrice = carList_item.getString("referPrice");
                                            String C_discountPrice = carList_item.getString("discountPrice");
                                            String C_subsidizedReferPrice = carList_item.getString("subsidizedReferPrice");
                                            String C_greenStandards = carList_item.getString("greenStandards");
                                            String C_haveParam = carList_item.getString("haveParam");
                                            String C_pvPercent = carList_item.getString("pvPercent");
                                            String C_hasImageFlag = carList_item.getString("hasImageFlag");
                                            String C_marketDate = carList_item.getString("marketDate");
                                            String C_params = carList_item.getString("params");
                                            String C_photoInfo = carList_item.getString("photoInfo");
                                            String C_yiCheHuiTag = carList_item.getString("yiCheHuiTag");
                                            String C_loanVo = carList_item.getString("loanVo");
                                            String C_businessCardList = carList_item.getString("businessCardList");
                                            String C_oilFuelTypeInt = carList_item.getString("oilFuelTypeInt");
                                            String C_fuelUnitType = carList_item.getString("fuelUnitType");
                                            String C_electricRechargeMileage = carList_item.getString("electricRechargeMileage");
                                            String C_oilWear = carList_item.getString("oilWear");
                                            String C_masterId = carList_item.getString("masterId");
                                            String C_masterName = carList_item.getString("masterName");
                                            String C_logoUrl = carList_item.getString("logoUrl");
                                            String C_logoUrlWp = carList_item.getString("logoUrlWp");
                                            String C_masterAllSpell = carList_item.getString("masterAllSpell");
                                            String C_minDealPrice = carList_item.getString("minDealPrice");
                                            String C_invoiceCount = carList_item.getString("invoiceCount");
                                            String C_brandId = carList_item.getString("brandId");
                                            String C_brandName = carList_item.getString("brandName");
                                            String C_imageUrlWp = carList_item.getString("imageUrlWp");
                                            String C_tranAndGearNum = carList_item.getString("tranAndGearNum");


                                            JSONArray tagList = carList_item.getJSONArray("tagList");

                                            String C_styleType = "无";
                                            String C_styleType_value = "无";
                                            if (tagList.size() != 0) {
                                                C_styleType = tagList.getJSONObject(0).getString("styleType");
                                                C_styleType_value = tagList.getJSONObject(0).getString("value");
                                            }

                                            Bean_version bean_version = new Bean_version();

                                            bean_version.setC_model_id(model_id);
                                            bean_version.setC_sourceList("onSaleCarList");
                                            bean_version.setC_year_carList(year_carList);
                                            bean_version.setC_saleStatusList(saleStatusList);
                                            bean_version.setC_powerName(powerName);

                                            bean_version.setC_version_id(C_id);
                                            bean_version.setC_name(C_name);
                                            bean_version.setC_year(C_year);
                                            bean_version.setC_saleStatus(C_saleStatus);
                                            bean_version.setC_localDealPrice(C_localDealPrice);
                                            bean_version.setC_locaLatestDealPrice(C_locaLatestDealPrice);
                                            bean_version.setC_imageUrl(C_imageUrl);
                                            bean_version.setC_entireDealPrice(C_entireDealPrice);
                                            bean_version.setC_dealFinalPriceInfo(C_dealFinalPriceInfo);
                                            bean_version.setC_serialId(C_serialId);
                                            bean_version.setC_serialName(C_serialName);
                                            bean_version.setC_serialSecondLevel(C_serialSecondLevel);
                                            bean_version.setC_usedCarPrice(C_usedCarPrice);
                                            bean_version.setC_dropPrice(C_dropPrice);
                                            bean_version.setC_dealerDropAfterPrice(C_dealerDropAfterPrice);
                                            bean_version.setC_maintainValue(C_maintainValue);
                                            bean_version.setC_cityReferencePrice(C_cityReferencePrice);
                                            bean_version.setC_subscribeStatus(C_subscribeStatus);
                                            bean_version.setC_referPrice(C_referPrice);
                                            bean_version.setC_discountPrice(C_discountPrice);
                                            bean_version.setC_subsidizedReferPrice(C_subsidizedReferPrice);
                                            bean_version.setC_greenStandards(C_greenStandards);
                                            bean_version.setC_haveParam(C_haveParam);
                                            bean_version.setC_pvPercent(C_pvPercent);
                                            bean_version.setC_hasImageFlag(C_hasImageFlag);
                                            bean_version.setC_marketDate(C_marketDate);
                                            bean_version.setC_params(C_params);
                                            bean_version.setC_photoInfo(C_photoInfo);
                                            bean_version.setC_yiCheHuiTag(C_yiCheHuiTag);
                                            bean_version.setC_loanVo(C_loanVo);
                                            bean_version.setC_businessCardList(C_businessCardList);
                                            bean_version.setC_oilFuelTypeInt(C_oilFuelTypeInt);
                                            bean_version.setC_fuelUnitType(C_fuelUnitType);
                                            bean_version.setC_electricRechargeMileage(C_electricRechargeMileage);
                                            bean_version.setC_oilWear(C_oilWear);
                                            bean_version.setC_masterId(C_masterId);
                                            bean_version.setC_masterName(C_masterName);
                                            bean_version.setC_logoUrl(C_logoUrl);
                                            bean_version.setC_logoUrlWp(C_logoUrlWp);
                                            bean_version.setC_masterAllSpell(C_masterAllSpell);
                                            bean_version.setC_minDealPrice(C_minDealPrice);
                                            bean_version.setC_invoiceCount(C_invoiceCount);
                                            bean_version.setC_brandId(C_brandId);
                                            bean_version.setC_brandName(C_brandName);
                                            bean_version.setC_imageUrlWp(C_imageUrlWp);
                                            bean_version.setC_tranAndGearNum(C_tranAndGearNum);
                                            bean_version.setC_styleType(C_styleType);
                                            bean_version.setC_styleType_value(C_styleType_value);
                                            dao_version.MethodInsert(bean_version);
                                        }
                                    }
                                }
                            }
                        }
                        JSONArray stopSaleCarList = jsonObject.getJSONArray("stopSaleCarList");
                        if (stopSaleCarList.size() != 0) {
                            for (int i = 0; i < stopSaleCarList.size(); i++) {
                                JSONObject Item1_notSale = stopSaleCarList.getJSONObject(i);
                                String year_carList = Item1_notSale.getString("year");
                                String saleStatusList = Item1_notSale.getString("saleStatusList");

                                JSONArray powerList_notSale = Item1_notSale.getJSONArray("powerList");
                                if (powerList_notSale.size() != 0) {
                                    for (int j = 0; j < powerList_notSale.size(); j++) {
                                        JSONObject powerList_notSale_item = powerList_notSale.getJSONObject(j);
                                        String powerName = powerList_notSale_item.getString("powerName");
                                        JSONArray carList = powerList_notSale_item.getJSONArray("carList");
                                        for (int k = 0; k < carList.size(); k++) {
                                            JSONObject carList_item = carList.getJSONObject(k);
                                            String C_id = carList_item.getString("id");
                                            String C_name = carList_item.getString("name");
                                            String C_year = carList_item.getString("year");
                                            String C_saleStatus = carList_item.getString("saleStatus");
                                            String C_localDealPrice = carList_item.getString("localDealPrice");
                                            String C_locaLatestDealPrice = carList_item.getString("locaLatestDealPrice");
                                            String C_imageUrl = carList_item.getString("imageUrl");
                                            String C_entireDealPrice = carList_item.getString("entireDealPrice");
                                            String C_dealFinalPriceInfo = carList_item.getString("dealFinalPriceInfo");
                                            String C_serialId = carList_item.getString("serialId");
                                            String C_serialName = carList_item.getString("serialName");
                                            String C_serialSecondLevel = carList_item.getString("serialSecondLevel");
                                            String C_usedCarPrice = carList_item.getString("usedCarPrice");
                                            String C_dropPrice = carList_item.getString("dropPrice");
                                            String C_dealerDropAfterPrice = carList_item.getString("dealerDropAfterPrice");
                                            String C_maintainValue = carList_item.getString("maintainValue");
                                            String C_cityReferencePrice = carList_item.getString("cityReferencePrice");
                                            String C_subscribeStatus = carList_item.getString("subscribeStatus");
                                            String C_referPrice = carList_item.getString("referPrice");
                                            String C_discountPrice = carList_item.getString("discountPrice");
                                            String C_subsidizedReferPrice = carList_item.getString("subsidizedReferPrice");
                                            String C_greenStandards = carList_item.getString("greenStandards");
                                            String C_haveParam = carList_item.getString("haveParam");
                                            String C_pvPercent = carList_item.getString("pvPercent");
                                            String C_hasImageFlag = carList_item.getString("hasImageFlag");
                                            String C_marketDate = carList_item.getString("marketDate");
                                            String C_params = carList_item.getString("params");
                                            String C_photoInfo = carList_item.getString("photoInfo");
                                            String C_yiCheHuiTag = carList_item.getString("yiCheHuiTag");
                                            String C_loanVo = carList_item.getString("loanVo");
                                            String C_businessCardList = carList_item.getString("businessCardList");
                                            String C_oilFuelTypeInt = carList_item.getString("oilFuelTypeInt");
                                            String C_fuelUnitType = carList_item.getString("fuelUnitType");
                                            String C_electricRechargeMileage = carList_item.getString("electricRechargeMileage");
                                            String C_oilWear = carList_item.getString("oilWear");
                                            String C_masterId = carList_item.getString("masterId");
                                            String C_masterName = carList_item.getString("masterName");
                                            String C_logoUrl = carList_item.getString("logoUrl");
                                            String C_logoUrlWp = carList_item.getString("logoUrlWp");
                                            String C_masterAllSpell = carList_item.getString("masterAllSpell");
                                            String C_minDealPrice = carList_item.getString("minDealPrice");
                                            String C_invoiceCount = carList_item.getString("invoiceCount");
                                            String C_brandId = carList_item.getString("brandId");
                                            String C_brandName = carList_item.getString("brandName");
                                            String C_imageUrlWp = carList_item.getString("imageUrlWp");
                                            String C_tranAndGearNum = carList_item.getString("tranAndGearNum");


                                            JSONArray tagList = carList_item.getJSONArray("tagList");
                                            String C_styleType = "无";
                                            String C_styleType_value = "无";
                                            if (tagList.size() != 0) {
                                                C_styleType = tagList.getJSONObject(0).getString("styleType");
                                                C_styleType_value = tagList.getJSONObject(0).getString("value");
                                            }


                                            Bean_version bean_version = new Bean_version();

                                            bean_version.setC_model_id(model_id);
                                            bean_version.setC_sourceList("stopSaleCarList");
                                            bean_version.setC_year_carList(year_carList);
                                            bean_version.setC_saleStatusList(saleStatusList);
                                            bean_version.setC_powerName(powerName);

                                            bean_version.setC_version_id(C_id);
                                            bean_version.setC_name(C_name);
                                            bean_version.setC_year(C_year);
                                            bean_version.setC_saleStatus(C_saleStatus);
                                            bean_version.setC_localDealPrice(C_localDealPrice);
                                            bean_version.setC_locaLatestDealPrice(C_locaLatestDealPrice);
                                            bean_version.setC_imageUrl(C_imageUrl);
                                            bean_version.setC_entireDealPrice(C_entireDealPrice);
                                            bean_version.setC_dealFinalPriceInfo(C_dealFinalPriceInfo);
                                            bean_version.setC_serialId(C_serialId);
                                            bean_version.setC_serialName(C_serialName);
                                            bean_version.setC_serialSecondLevel(C_serialSecondLevel);
                                            bean_version.setC_usedCarPrice(C_usedCarPrice);
                                            bean_version.setC_dropPrice(C_dropPrice);
                                            bean_version.setC_dealerDropAfterPrice(C_dealerDropAfterPrice);
                                            bean_version.setC_maintainValue(C_maintainValue);
                                            bean_version.setC_cityReferencePrice(C_cityReferencePrice);
                                            bean_version.setC_subscribeStatus(C_subscribeStatus);
                                            bean_version.setC_referPrice(C_referPrice);
                                            bean_version.setC_discountPrice(C_discountPrice);
                                            bean_version.setC_subsidizedReferPrice(C_subsidizedReferPrice);
                                            bean_version.setC_greenStandards(C_greenStandards);
                                            bean_version.setC_haveParam(C_haveParam);
                                            bean_version.setC_pvPercent(C_pvPercent);
                                            bean_version.setC_hasImageFlag(C_hasImageFlag);
                                            bean_version.setC_marketDate(C_marketDate);
                                            bean_version.setC_params(C_params);
                                            bean_version.setC_photoInfo(C_photoInfo);
                                            bean_version.setC_yiCheHuiTag(C_yiCheHuiTag);
                                            bean_version.setC_loanVo(C_loanVo);
                                            bean_version.setC_businessCardList(C_businessCardList);
                                            bean_version.setC_oilFuelTypeInt(C_oilFuelTypeInt);
                                            bean_version.setC_fuelUnitType(C_fuelUnitType);
                                            bean_version.setC_electricRechargeMileage(C_electricRechargeMileage);
                                            bean_version.setC_oilWear(C_oilWear);
                                            bean_version.setC_masterId(C_masterId);
                                            bean_version.setC_masterName(C_masterName);
                                            bean_version.setC_logoUrl(C_logoUrl);
                                            bean_version.setC_logoUrlWp(C_logoUrlWp);
                                            bean_version.setC_masterAllSpell(C_masterAllSpell);
                                            bean_version.setC_minDealPrice(C_minDealPrice);
                                            bean_version.setC_invoiceCount(C_invoiceCount);
                                            bean_version.setC_brandId(C_brandId);
                                            bean_version.setC_brandName(C_brandName);
                                            bean_version.setC_imageUrlWp(C_imageUrlWp);
                                            bean_version.setC_tranAndGearNum(C_tranAndGearNum);
                                            bean_version.setC_styleType(C_styleType);
                                            bean_version.setC_styleType_value(C_styleType_value);

                                            dao_version.MethodInsert(bean_version);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (C_isSale == 0) {
                    String content = readUntil.Method_ReadFile(versionPath + model_id + "_version_NoSale.txt");
                    Elements Item1 = Jsoup.parse(content).select("script[type=text/javascript]");

                    String Text_Content = URLDecoder.decode(String.valueOf(Item1.get(4)), "utf-8");
                    int ju = Text_Content.indexOf("{\"conditionList\"");
                    if (ju > 0) {

                        String mainJSON = Text_Content.substring(Text_Content.indexOf("{\"conditionList\""), Text_Content.indexOf("\",serialSalesListData:\""));
                        JSONObject jsonObject = JSON.parseObject(mainJSON);
                        JSONArray notSaleCarList = jsonObject.getJSONArray("notSaleCarList");
                        if (notSaleCarList.size() != 0) {
                            for (int i = 0; i < notSaleCarList.size(); i++) {
                                JSONObject Item1_notSale = notSaleCarList.getJSONObject(i);
                                String year_carList = Item1_notSale.getString("year");
                                String saleStatusList = Item1_notSale.getString("saleStatusList");

                                JSONArray powerList_notSale = Item1_notSale.getJSONArray("powerList");
                                if (powerList_notSale.size() != 0) {
                                    for (int j = 0; j < powerList_notSale.size(); j++) {
                                        JSONObject powerList_notSale_item = powerList_notSale.getJSONObject(j);
                                        String powerName = powerList_notSale_item.getString("powerName");
                                        JSONArray carList = powerList_notSale_item.getJSONArray("carList");
                                        for (int k = 0; k < carList.size(); k++) {
                                            JSONObject carList_item = carList.getJSONObject(k);
                                            String C_id = carList_item.getString("id");
                                            String C_name = carList_item.getString("name");
                                            String C_year = carList_item.getString("year");
                                            String C_saleStatus = carList_item.getString("saleStatus");
                                            String C_localDealPrice = carList_item.getString("localDealPrice");
                                            String C_locaLatestDealPrice = carList_item.getString("locaLatestDealPrice");
                                            String C_imageUrl = carList_item.getString("imageUrl");
                                            String C_entireDealPrice = carList_item.getString("entireDealPrice");
                                            String C_dealFinalPriceInfo = carList_item.getString("dealFinalPriceInfo");
                                            String C_serialId = carList_item.getString("serialId");
                                            String C_serialName = carList_item.getString("serialName");
                                            String C_serialSecondLevel = carList_item.getString("serialSecondLevel");
                                            String C_usedCarPrice = carList_item.getString("usedCarPrice");
                                            String C_dropPrice = carList_item.getString("dropPrice");
                                            String C_dealerDropAfterPrice = carList_item.getString("dealerDropAfterPrice");
                                            String C_maintainValue = carList_item.getString("maintainValue");
                                            String C_cityReferencePrice = carList_item.getString("cityReferencePrice");
                                            String C_subscribeStatus = carList_item.getString("subscribeStatus");
                                            String C_referPrice = carList_item.getString("referPrice");
                                            String C_discountPrice = carList_item.getString("discountPrice");
                                            String C_subsidizedReferPrice = carList_item.getString("subsidizedReferPrice");
                                            String C_greenStandards = carList_item.getString("greenStandards");
                                            String C_haveParam = carList_item.getString("haveParam");
                                            String C_pvPercent = carList_item.getString("pvPercent");
                                            String C_hasImageFlag = carList_item.getString("hasImageFlag");
                                            String C_marketDate = carList_item.getString("marketDate");
                                            String C_params = carList_item.getString("params");
                                            String C_photoInfo = carList_item.getString("photoInfo");
                                            String C_yiCheHuiTag = carList_item.getString("yiCheHuiTag");
                                            String C_loanVo = carList_item.getString("loanVo");
                                            String C_businessCardList = carList_item.getString("businessCardList");
                                            String C_oilFuelTypeInt = carList_item.getString("oilFuelTypeInt");
                                            String C_fuelUnitType = carList_item.getString("fuelUnitType");
                                            String C_electricRechargeMileage = carList_item.getString("electricRechargeMileage");
                                            String C_oilWear = carList_item.getString("oilWear");
                                            String C_masterId = carList_item.getString("masterId");
                                            String C_masterName = carList_item.getString("masterName");
                                            String C_logoUrl = carList_item.getString("logoUrl");
                                            String C_logoUrlWp = carList_item.getString("logoUrlWp");
                                            String C_masterAllSpell = carList_item.getString("masterAllSpell");
                                            String C_minDealPrice = carList_item.getString("minDealPrice");
                                            String C_invoiceCount = carList_item.getString("invoiceCount");
                                            String C_brandId = carList_item.getString("brandId");
                                            String C_brandName = carList_item.getString("brandName");
                                            String C_imageUrlWp = carList_item.getString("imageUrlWp");
                                            String C_tranAndGearNum = carList_item.getString("tranAndGearNum");


                                            JSONArray tagList = carList_item.getJSONArray("tagList");

                                            String C_styleType = "无";
                                            String C_styleType_value = "无";
                                            if (tagList.size() != 0) {
                                                C_styleType = tagList.getJSONObject(0).getString("styleType");
                                                C_styleType_value = tagList.getJSONObject(0).getString("value");
                                            }


                                            Bean_version bean_version = new Bean_version();

                                            bean_version.setC_model_id(model_id);
                                            bean_version.setC_sourceList("notSaleCarList");
                                            bean_version.setC_year_carList(year_carList);
                                            bean_version.setC_saleStatusList(saleStatusList);
                                            bean_version.setC_powerName(powerName);

                                            bean_version.setC_version_id(C_id);
                                            bean_version.setC_name(C_name);
                                            bean_version.setC_year(C_year);
                                            bean_version.setC_saleStatus(C_saleStatus);
                                            bean_version.setC_localDealPrice(C_localDealPrice);
                                            bean_version.setC_locaLatestDealPrice(C_locaLatestDealPrice);
                                            bean_version.setC_imageUrl(C_imageUrl);
                                            bean_version.setC_entireDealPrice(C_entireDealPrice);
                                            bean_version.setC_dealFinalPriceInfo(C_dealFinalPriceInfo);
                                            bean_version.setC_serialId(C_serialId);
                                            bean_version.setC_serialName(C_serialName);
                                            bean_version.setC_serialSecondLevel(C_serialSecondLevel);
                                            bean_version.setC_usedCarPrice(C_usedCarPrice);
                                            bean_version.setC_dropPrice(C_dropPrice);
                                            bean_version.setC_dealerDropAfterPrice(C_dealerDropAfterPrice);
                                            bean_version.setC_maintainValue(C_maintainValue);
                                            bean_version.setC_cityReferencePrice(C_cityReferencePrice);
                                            bean_version.setC_subscribeStatus(C_subscribeStatus);
                                            bean_version.setC_referPrice(C_referPrice);
                                            bean_version.setC_discountPrice(C_discountPrice);
                                            bean_version.setC_subsidizedReferPrice(C_subsidizedReferPrice);
                                            bean_version.setC_greenStandards(C_greenStandards);
                                            bean_version.setC_haveParam(C_haveParam);
                                            bean_version.setC_pvPercent(C_pvPercent);
                                            bean_version.setC_hasImageFlag(C_hasImageFlag);
                                            bean_version.setC_marketDate(C_marketDate);
                                            bean_version.setC_params(C_params);
                                            bean_version.setC_photoInfo(C_photoInfo);
                                            bean_version.setC_yiCheHuiTag(C_yiCheHuiTag);
                                            bean_version.setC_loanVo(C_loanVo);
                                            bean_version.setC_businessCardList(C_businessCardList);
                                            bean_version.setC_oilFuelTypeInt(C_oilFuelTypeInt);
                                            bean_version.setC_fuelUnitType(C_fuelUnitType);
                                            bean_version.setC_electricRechargeMileage(C_electricRechargeMileage);
                                            bean_version.setC_oilWear(C_oilWear);
                                            bean_version.setC_masterId(C_masterId);
                                            bean_version.setC_masterName(C_masterName);
                                            bean_version.setC_logoUrl(C_logoUrl);
                                            bean_version.setC_logoUrlWp(C_logoUrlWp);
                                            bean_version.setC_masterAllSpell(C_masterAllSpell);
                                            bean_version.setC_minDealPrice(C_minDealPrice);
                                            bean_version.setC_invoiceCount(C_invoiceCount);
                                            bean_version.setC_brandId(C_brandId);
                                            bean_version.setC_brandName(C_brandName);
                                            bean_version.setC_imageUrlWp(C_imageUrlWp);
                                            bean_version.setC_tranAndGearNum(C_tranAndGearNum);
                                            bean_version.setC_styleType(C_styleType);
                                            bean_version.setC_styleType_value(C_styleType_value);

                                            dao_version.MethodInsert(bean_version);
                                        }
                                    }
                                }
                            }
                        }
                        JSONArray waitSaleCarList = jsonObject.getJSONArray("waitSaleCarList");
                        if (waitSaleCarList.size() != 0) {
                            for (int i = 0; i < waitSaleCarList.size(); i++) {
                                JSONObject Item1_notSale = waitSaleCarList.getJSONObject(i);
                                String year_carList = Item1_notSale.getString("year");
                                String saleStatusList = Item1_notSale.getString("saleStatusList");

                                JSONArray powerList_notSale = Item1_notSale.getJSONArray("powerList");
                                if (powerList_notSale.size() != 0) {
                                    for (int j = 0; j < powerList_notSale.size(); j++) {
                                        JSONObject powerList_notSale_item = powerList_notSale.getJSONObject(j);
                                        String powerName = powerList_notSale_item.getString("powerName");
                                        JSONArray carList = powerList_notSale_item.getJSONArray("carList");
                                        for (int k = 0; k < carList.size(); k++) {
                                            JSONObject carList_item = carList.getJSONObject(k);
                                            String C_id = carList_item.getString("id");
                                            String C_name = carList_item.getString("name");
                                            String C_year = carList_item.getString("year");
                                            String C_saleStatus = carList_item.getString("saleStatus");
                                            String C_localDealPrice = carList_item.getString("localDealPrice");
                                            String C_locaLatestDealPrice = carList_item.getString("locaLatestDealPrice");
                                            String C_imageUrl = carList_item.getString("imageUrl");
                                            String C_entireDealPrice = carList_item.getString("entireDealPrice");
                                            String C_dealFinalPriceInfo = carList_item.getString("dealFinalPriceInfo");
                                            String C_serialId = carList_item.getString("serialId");
                                            String C_serialName = carList_item.getString("serialName");
                                            String C_serialSecondLevel = carList_item.getString("serialSecondLevel");
                                            String C_usedCarPrice = carList_item.getString("usedCarPrice");
                                            String C_dropPrice = carList_item.getString("dropPrice");
                                            String C_dealerDropAfterPrice = carList_item.getString("dealerDropAfterPrice");
                                            String C_maintainValue = carList_item.getString("maintainValue");
                                            String C_cityReferencePrice = carList_item.getString("cityReferencePrice");
                                            String C_subscribeStatus = carList_item.getString("subscribeStatus");
                                            String C_referPrice = carList_item.getString("referPrice");
                                            String C_discountPrice = carList_item.getString("discountPrice");
                                            String C_subsidizedReferPrice = carList_item.getString("subsidizedReferPrice");
                                            String C_greenStandards = carList_item.getString("greenStandards");
                                            String C_haveParam = carList_item.getString("haveParam");
                                            String C_pvPercent = carList_item.getString("pvPercent");
                                            String C_hasImageFlag = carList_item.getString("hasImageFlag");
                                            String C_marketDate = carList_item.getString("marketDate");
                                            String C_params = carList_item.getString("params");
                                            String C_photoInfo = carList_item.getString("photoInfo");
                                            String C_yiCheHuiTag = carList_item.getString("yiCheHuiTag");
                                            String C_loanVo = carList_item.getString("loanVo");
                                            String C_businessCardList = carList_item.getString("businessCardList");
                                            String C_oilFuelTypeInt = carList_item.getString("oilFuelTypeInt");
                                            String C_fuelUnitType = carList_item.getString("fuelUnitType");
                                            String C_electricRechargeMileage = carList_item.getString("electricRechargeMileage");
                                            String C_oilWear = carList_item.getString("oilWear");
                                            String C_masterId = carList_item.getString("masterId");
                                            String C_masterName = carList_item.getString("masterName");
                                            String C_logoUrl = carList_item.getString("logoUrl");
                                            String C_logoUrlWp = carList_item.getString("logoUrlWp");
                                            String C_masterAllSpell = carList_item.getString("masterAllSpell");
                                            String C_minDealPrice = carList_item.getString("minDealPrice");
                                            String C_invoiceCount = carList_item.getString("invoiceCount");
                                            String C_brandId = carList_item.getString("brandId");
                                            String C_brandName = carList_item.getString("brandName");
                                            String C_imageUrlWp = carList_item.getString("imageUrlWp");
                                            String C_tranAndGearNum = carList_item.getString("tranAndGearNum");


                                            JSONArray tagList = carList_item.getJSONArray("tagList");

                                            String C_styleType = "无";
                                            String C_styleType_value = "无";
                                            if (tagList.size() != 0) {
                                                C_styleType = tagList.getJSONObject(0).getString("styleType");
                                                C_styleType_value = tagList.getJSONObject(0).getString("value");
                                            }


                                            Bean_version bean_version = new Bean_version();

                                            bean_version.setC_model_id(model_id);
                                            bean_version.setC_sourceList("waitSaleCarList");
                                            bean_version.setC_year_carList(year_carList);
                                            bean_version.setC_saleStatusList(saleStatusList);
                                            bean_version.setC_powerName(powerName);

                                            bean_version.setC_version_id(C_id);
                                            bean_version.setC_name(C_name);
                                            bean_version.setC_year(C_year);
                                            bean_version.setC_saleStatus(C_saleStatus);
                                            bean_version.setC_localDealPrice(C_localDealPrice);
                                            bean_version.setC_locaLatestDealPrice(C_locaLatestDealPrice);
                                            bean_version.setC_imageUrl(C_imageUrl);
                                            bean_version.setC_entireDealPrice(C_entireDealPrice);
                                            bean_version.setC_dealFinalPriceInfo(C_dealFinalPriceInfo);
                                            bean_version.setC_serialId(C_serialId);
                                            bean_version.setC_serialName(C_serialName);
                                            bean_version.setC_serialSecondLevel(C_serialSecondLevel);
                                            bean_version.setC_usedCarPrice(C_usedCarPrice);
                                            bean_version.setC_dropPrice(C_dropPrice);
                                            bean_version.setC_dealerDropAfterPrice(C_dealerDropAfterPrice);
                                            bean_version.setC_maintainValue(C_maintainValue);
                                            bean_version.setC_cityReferencePrice(C_cityReferencePrice);
                                            bean_version.setC_subscribeStatus(C_subscribeStatus);
                                            bean_version.setC_referPrice(C_referPrice);
                                            bean_version.setC_discountPrice(C_discountPrice);
                                            bean_version.setC_subsidizedReferPrice(C_subsidizedReferPrice);
                                            bean_version.setC_greenStandards(C_greenStandards);
                                            bean_version.setC_haveParam(C_haveParam);
                                            bean_version.setC_pvPercent(C_pvPercent);
                                            bean_version.setC_hasImageFlag(C_hasImageFlag);
                                            bean_version.setC_marketDate(C_marketDate);
                                            bean_version.setC_params(C_params);
                                            bean_version.setC_photoInfo(C_photoInfo);
                                            bean_version.setC_yiCheHuiTag(C_yiCheHuiTag);
                                            bean_version.setC_loanVo(C_loanVo);
                                            bean_version.setC_businessCardList(C_businessCardList);
                                            bean_version.setC_oilFuelTypeInt(C_oilFuelTypeInt);
                                            bean_version.setC_fuelUnitType(C_fuelUnitType);
                                            bean_version.setC_electricRechargeMileage(C_electricRechargeMileage);
                                            bean_version.setC_oilWear(C_oilWear);
                                            bean_version.setC_masterId(C_masterId);
                                            bean_version.setC_masterName(C_masterName);
                                            bean_version.setC_logoUrl(C_logoUrl);
                                            bean_version.setC_logoUrlWp(C_logoUrlWp);
                                            bean_version.setC_masterAllSpell(C_masterAllSpell);
                                            bean_version.setC_minDealPrice(C_minDealPrice);
                                            bean_version.setC_invoiceCount(C_invoiceCount);
                                            bean_version.setC_brandId(C_brandId);
                                            bean_version.setC_brandName(C_brandName);
                                            bean_version.setC_imageUrlWp(C_imageUrlWp);
                                            bean_version.setC_tranAndGearNum(C_tranAndGearNum);
                                            bean_version.setC_styleType(C_styleType);
                                            bean_version.setC_styleType_value(C_styleType_value);

                                            dao_version.MethodInsert(bean_version);

                                        }
                                    }
                                }
                            }
                        }
                        JSONArray onSaleCarList = jsonObject.getJSONArray("onSaleCarList");
                        if (onSaleCarList.size() != 0) {
                            for (int i = 0; i < onSaleCarList.size(); i++) {
                                JSONObject Item1_notSale = onSaleCarList.getJSONObject(i);
                                String year_carList = Item1_notSale.getString("year");
                                String saleStatusList = Item1_notSale.getString("saleStatusList");

                                JSONArray powerList_notSale = Item1_notSale.getJSONArray("powerList");
                                if (powerList_notSale.size() != 0) {
                                    for (int j = 0; j < powerList_notSale.size(); j++) {
                                        JSONObject powerList_notSale_item = powerList_notSale.getJSONObject(j);
                                        String powerName = powerList_notSale_item.getString("powerName");
                                        JSONArray carList = powerList_notSale_item.getJSONArray("carList");
                                        for (int k = 0; k < carList.size(); k++) {
                                            JSONObject carList_item = carList.getJSONObject(k);
                                            String C_id = carList_item.getString("id");
                                            String C_name = carList_item.getString("name");
                                            String C_year = carList_item.getString("year");
                                            String C_saleStatus = carList_item.getString("saleStatus");
                                            String C_localDealPrice = carList_item.getString("localDealPrice");
                                            String C_locaLatestDealPrice = carList_item.getString("locaLatestDealPrice");
                                            String C_imageUrl = carList_item.getString("imageUrl");
                                            String C_entireDealPrice = carList_item.getString("entireDealPrice");
                                            String C_dealFinalPriceInfo = carList_item.getString("dealFinalPriceInfo");
                                            String C_serialId = carList_item.getString("serialId");
                                            String C_serialName = carList_item.getString("serialName");
                                            String C_serialSecondLevel = carList_item.getString("serialSecondLevel");
                                            String C_usedCarPrice = carList_item.getString("usedCarPrice");
                                            String C_dropPrice = carList_item.getString("dropPrice");
                                            String C_dealerDropAfterPrice = carList_item.getString("dealerDropAfterPrice");
                                            String C_maintainValue = carList_item.getString("maintainValue");
                                            String C_cityReferencePrice = carList_item.getString("cityReferencePrice");
                                            String C_subscribeStatus = carList_item.getString("subscribeStatus");
                                            String C_referPrice = carList_item.getString("referPrice");
                                            String C_discountPrice = carList_item.getString("discountPrice");
                                            String C_subsidizedReferPrice = carList_item.getString("subsidizedReferPrice");
                                            String C_greenStandards = carList_item.getString("greenStandards");
                                            String C_haveParam = carList_item.getString("haveParam");
                                            String C_pvPercent = carList_item.getString("pvPercent");
                                            String C_hasImageFlag = carList_item.getString("hasImageFlag");
                                            String C_marketDate = carList_item.getString("marketDate");
                                            String C_params = carList_item.getString("params");
                                            String C_photoInfo = carList_item.getString("photoInfo");
                                            String C_yiCheHuiTag = carList_item.getString("yiCheHuiTag");
                                            String C_loanVo = carList_item.getString("loanVo");
                                            String C_businessCardList = carList_item.getString("businessCardList");
                                            String C_oilFuelTypeInt = carList_item.getString("oilFuelTypeInt");
                                            String C_fuelUnitType = carList_item.getString("fuelUnitType");
                                            String C_electricRechargeMileage = carList_item.getString("electricRechargeMileage");
                                            String C_oilWear = carList_item.getString("oilWear");
                                            String C_masterId = carList_item.getString("masterId");
                                            String C_masterName = carList_item.getString("masterName");
                                            String C_logoUrl = carList_item.getString("logoUrl");
                                            String C_logoUrlWp = carList_item.getString("logoUrlWp");
                                            String C_masterAllSpell = carList_item.getString("masterAllSpell");
                                            String C_minDealPrice = carList_item.getString("minDealPrice");
                                            String C_invoiceCount = carList_item.getString("invoiceCount");
                                            String C_brandId = carList_item.getString("brandId");
                                            String C_brandName = carList_item.getString("brandName");
                                            String C_imageUrlWp = carList_item.getString("imageUrlWp");
                                            String C_tranAndGearNum = carList_item.getString("tranAndGearNum");


                                            JSONArray tagList = carList_item.getJSONArray("tagList");

                                            String C_styleType = "无";
                                            String C_styleType_value = "无";
                                            if (tagList.size() != 0) {
                                                C_styleType = tagList.getJSONObject(0).getString("styleType");
                                                C_styleType_value = tagList.getJSONObject(0).getString("value");
                                            }


                                            Bean_version bean_version = new Bean_version();

                                            bean_version.setC_model_id(model_id);
                                            bean_version.setC_sourceList("onSaleCarList");
                                            bean_version.setC_year_carList(year_carList);
                                            bean_version.setC_saleStatusList(saleStatusList);
                                            bean_version.setC_powerName(powerName);

                                            bean_version.setC_version_id(C_id);
                                            bean_version.setC_name(C_name);
                                            bean_version.setC_year(C_year);
                                            bean_version.setC_saleStatus(C_saleStatus);
                                            bean_version.setC_localDealPrice(C_localDealPrice);
                                            bean_version.setC_locaLatestDealPrice(C_locaLatestDealPrice);
                                            bean_version.setC_imageUrl(C_imageUrl);
                                            bean_version.setC_entireDealPrice(C_entireDealPrice);
                                            bean_version.setC_dealFinalPriceInfo(C_dealFinalPriceInfo);
                                            bean_version.setC_serialId(C_serialId);
                                            bean_version.setC_serialName(C_serialName);
                                            bean_version.setC_serialSecondLevel(C_serialSecondLevel);
                                            bean_version.setC_usedCarPrice(C_usedCarPrice);
                                            bean_version.setC_dropPrice(C_dropPrice);
                                            bean_version.setC_dealerDropAfterPrice(C_dealerDropAfterPrice);
                                            bean_version.setC_maintainValue(C_maintainValue);
                                            bean_version.setC_cityReferencePrice(C_cityReferencePrice);
                                            bean_version.setC_subscribeStatus(C_subscribeStatus);
                                            bean_version.setC_referPrice(C_referPrice);
                                            bean_version.setC_discountPrice(C_discountPrice);
                                            bean_version.setC_subsidizedReferPrice(C_subsidizedReferPrice);
                                            bean_version.setC_greenStandards(C_greenStandards);
                                            bean_version.setC_haveParam(C_haveParam);
                                            bean_version.setC_pvPercent(C_pvPercent);
                                            bean_version.setC_hasImageFlag(C_hasImageFlag);
                                            bean_version.setC_marketDate(C_marketDate);
                                            bean_version.setC_params(C_params);
                                            bean_version.setC_photoInfo(C_photoInfo);
                                            bean_version.setC_yiCheHuiTag(C_yiCheHuiTag);
                                            bean_version.setC_loanVo(C_loanVo);
                                            bean_version.setC_businessCardList(C_businessCardList);
                                            bean_version.setC_oilFuelTypeInt(C_oilFuelTypeInt);
                                            bean_version.setC_fuelUnitType(C_fuelUnitType);
                                            bean_version.setC_electricRechargeMileage(C_electricRechargeMileage);
                                            bean_version.setC_oilWear(C_oilWear);
                                            bean_version.setC_masterId(C_masterId);
                                            bean_version.setC_masterName(C_masterName);
                                            bean_version.setC_logoUrl(C_logoUrl);
                                            bean_version.setC_logoUrlWp(C_logoUrlWp);
                                            bean_version.setC_masterAllSpell(C_masterAllSpell);
                                            bean_version.setC_minDealPrice(C_minDealPrice);
                                            bean_version.setC_invoiceCount(C_invoiceCount);
                                            bean_version.setC_brandId(C_brandId);
                                            bean_version.setC_brandName(C_brandName);
                                            bean_version.setC_imageUrlWp(C_imageUrlWp);
                                            bean_version.setC_tranAndGearNum(C_tranAndGearNum);
                                            bean_version.setC_styleType(C_styleType);
                                            bean_version.setC_styleType_value(C_styleType_value);

                                            dao_version.MethodInsert(bean_version);
                                        }
                                    }
                                }
                            }
                        }
                        JSONArray stopSaleCarList = jsonObject.getJSONArray("stopSaleCarList");
                        if (stopSaleCarList.size() != 0) {
                            for (int i = 0; i < stopSaleCarList.size(); i++) {
                                JSONObject Item1_notSale = stopSaleCarList.getJSONObject(i);
                                String year_carList = Item1_notSale.getString("year");
                                String saleStatusList = Item1_notSale.getString("saleStatusList");

                                JSONArray powerList_notSale = Item1_notSale.getJSONArray("powerList");
                                if (powerList_notSale.size() != 0) {
                                    for (int j = 0; j < powerList_notSale.size(); j++) {
                                        JSONObject powerList_notSale_item = powerList_notSale.getJSONObject(j);
                                        String powerName = powerList_notSale_item.getString("powerName");
                                        JSONArray carList = powerList_notSale_item.getJSONArray("carList");
                                        for (int k = 0; k < carList.size(); k++) {
                                            JSONObject carList_item = carList.getJSONObject(k);
                                            String C_id = carList_item.getString("id");
                                            String C_name = carList_item.getString("name");
                                            String C_year = carList_item.getString("year");
                                            String C_saleStatus = carList_item.getString("saleStatus");
                                            String C_localDealPrice = carList_item.getString("localDealPrice");
                                            String C_locaLatestDealPrice = carList_item.getString("locaLatestDealPrice");
                                            String C_imageUrl = carList_item.getString("imageUrl");
                                            String C_entireDealPrice = carList_item.getString("entireDealPrice");
                                            String C_dealFinalPriceInfo = carList_item.getString("dealFinalPriceInfo");
                                            String C_serialId = carList_item.getString("serialId");
                                            String C_serialName = carList_item.getString("serialName");
                                            String C_serialSecondLevel = carList_item.getString("serialSecondLevel");
                                            String C_usedCarPrice = carList_item.getString("usedCarPrice");
                                            String C_dropPrice = carList_item.getString("dropPrice");
                                            String C_dealerDropAfterPrice = carList_item.getString("dealerDropAfterPrice");
                                            String C_maintainValue = carList_item.getString("maintainValue");
                                            String C_cityReferencePrice = carList_item.getString("cityReferencePrice");
                                            String C_subscribeStatus = carList_item.getString("subscribeStatus");
                                            String C_referPrice = carList_item.getString("referPrice");
                                            String C_discountPrice = carList_item.getString("discountPrice");
                                            String C_subsidizedReferPrice = carList_item.getString("subsidizedReferPrice");
                                            String C_greenStandards = carList_item.getString("greenStandards");
                                            String C_haveParam = carList_item.getString("haveParam");
                                            String C_pvPercent = carList_item.getString("pvPercent");
                                            String C_hasImageFlag = carList_item.getString("hasImageFlag");
                                            String C_marketDate = carList_item.getString("marketDate");
                                            String C_params = carList_item.getString("params");
                                            String C_photoInfo = carList_item.getString("photoInfo");
                                            String C_yiCheHuiTag = carList_item.getString("yiCheHuiTag");
                                            String C_loanVo = carList_item.getString("loanVo");
                                            String C_businessCardList = carList_item.getString("businessCardList");
                                            String C_oilFuelTypeInt = carList_item.getString("oilFuelTypeInt");
                                            String C_fuelUnitType = carList_item.getString("fuelUnitType");
                                            String C_electricRechargeMileage = carList_item.getString("electricRechargeMileage");
                                            String C_oilWear = carList_item.getString("oilWear");
                                            String C_masterId = carList_item.getString("masterId");
                                            String C_masterName = carList_item.getString("masterName");
                                            String C_logoUrl = carList_item.getString("logoUrl");
                                            String C_logoUrlWp = carList_item.getString("logoUrlWp");
                                            String C_masterAllSpell = carList_item.getString("masterAllSpell");
                                            String C_minDealPrice = carList_item.getString("minDealPrice");
                                            String C_invoiceCount = carList_item.getString("invoiceCount");
                                            String C_brandId = carList_item.getString("brandId");
                                            String C_brandName = carList_item.getString("brandName");
                                            String C_imageUrlWp = carList_item.getString("imageUrlWp");
                                            String C_tranAndGearNum = carList_item.getString("tranAndGearNum");


                                            JSONArray tagList = carList_item.getJSONArray("tagList");

                                            String C_styleType = "无";
                                            String C_styleType_value = "无";
                                            if (tagList.size() != 0) {
                                                C_styleType = tagList.getJSONObject(0).getString("styleType");
                                                C_styleType_value = tagList.getJSONObject(0).getString("value");
                                            }


                                            Bean_version bean_version = new Bean_version();

                                            bean_version.setC_model_id(model_id);
                                            bean_version.setC_sourceList("stopSaleCarList");
                                            bean_version.setC_year_carList(year_carList);
                                            bean_version.setC_saleStatusList(saleStatusList);
                                            bean_version.setC_powerName(powerName);

                                            bean_version.setC_version_id(C_id);
                                            bean_version.setC_name(C_name);
                                            bean_version.setC_year(C_year);
                                            bean_version.setC_saleStatus(C_saleStatus);
                                            bean_version.setC_localDealPrice(C_localDealPrice);
                                            bean_version.setC_locaLatestDealPrice(C_locaLatestDealPrice);
                                            bean_version.setC_imageUrl(C_imageUrl);
                                            bean_version.setC_entireDealPrice(C_entireDealPrice);
                                            bean_version.setC_dealFinalPriceInfo(C_dealFinalPriceInfo);
                                            bean_version.setC_serialId(C_serialId);
                                            bean_version.setC_serialName(C_serialName);
                                            bean_version.setC_serialSecondLevel(C_serialSecondLevel);
                                            bean_version.setC_usedCarPrice(C_usedCarPrice);
                                            bean_version.setC_dropPrice(C_dropPrice);
                                            bean_version.setC_dealerDropAfterPrice(C_dealerDropAfterPrice);
                                            bean_version.setC_maintainValue(C_maintainValue);
                                            bean_version.setC_cityReferencePrice(C_cityReferencePrice);
                                            bean_version.setC_subscribeStatus(C_subscribeStatus);
                                            bean_version.setC_referPrice(C_referPrice);
                                            bean_version.setC_discountPrice(C_discountPrice);
                                            bean_version.setC_subsidizedReferPrice(C_subsidizedReferPrice);
                                            bean_version.setC_greenStandards(C_greenStandards);
                                            bean_version.setC_haveParam(C_haveParam);
                                            bean_version.setC_pvPercent(C_pvPercent);
                                            bean_version.setC_hasImageFlag(C_hasImageFlag);
                                            bean_version.setC_marketDate(C_marketDate);
                                            bean_version.setC_params(C_params);
                                            bean_version.setC_photoInfo(C_photoInfo);
                                            bean_version.setC_yiCheHuiTag(C_yiCheHuiTag);
                                            bean_version.setC_loanVo(C_loanVo);
                                            bean_version.setC_businessCardList(C_businessCardList);
                                            bean_version.setC_oilFuelTypeInt(C_oilFuelTypeInt);
                                            bean_version.setC_fuelUnitType(C_fuelUnitType);
                                            bean_version.setC_electricRechargeMileage(C_electricRechargeMileage);
                                            bean_version.setC_oilWear(C_oilWear);
                                            bean_version.setC_masterId(C_masterId);
                                            bean_version.setC_masterName(C_masterName);
                                            bean_version.setC_logoUrl(C_logoUrl);
                                            bean_version.setC_logoUrlWp(C_logoUrlWp);
                                            bean_version.setC_masterAllSpell(C_masterAllSpell);
                                            bean_version.setC_minDealPrice(C_minDealPrice);
                                            bean_version.setC_invoiceCount(C_invoiceCount);
                                            bean_version.setC_brandId(C_brandId);
                                            bean_version.setC_brandName(C_brandName);
                                            bean_version.setC_imageUrlWp(C_imageUrlWp);
                                            bean_version.setC_tranAndGearNum(C_tranAndGearNum);
                                            bean_version.setC_styleType(C_styleType);
                                            bean_version.setC_styleType_value(C_styleType_value);

                                            dao_version.MethodInsert(bean_version);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //    读取数据库,找到具有配置信息的版本ID
    public void Method_10_1_Down_Config_fenzu(){
        Dao_version dao_version = new Dao_version(0, 3);
        dao_version.Method_fenzu();
    }
    public void Method_10_Down_Config_ReadSQL(String Config_savePath, String ConfigAPI) throws InterruptedException {
        Dao_version dao_version = new Dao_version(0, 3);
        ArrayList<Object> BeanList = dao_version.MethodFind_HaveParam();
        int BeanListSize = BeanList.size();
        int All_GroupNumber = ((Bean_version) BeanList.get(BeanListSize - 1)).getGroupNumber();
        System.out.println("本次分组共\t" + All_GroupNumber);

        for (int i = 1; i <= All_GroupNumber; i++) {
            System.out.println("本次下载第\t" + i);

            ArrayList<Object> BeanList_FenZu = dao_version.MethodFind_HaveParam_FenZhu(i);
            System.out.println("组内个数: " + BeanList_FenZu.size());
            StringBuilder VersionIDs = new StringBuilder();

            if (BeanList_FenZu.size() != 0) {
                for (int j = 0; j < BeanList_FenZu.size(); j++) {
                    Bean_version bean_version_fenzu = (Bean_version) BeanList_FenZu.get(j);
                    VersionIDs.append(",").append(bean_version_fenzu.getC_version_id());
                }
                String finall_Versions = VersionIDs.substring(1);
                System.out.println(finall_Versions);
                String DownState = ((Bean_version) BeanList_FenZu.get(0)).getDownState();

                if (DownState.equals("-")) {
                    String content = Method_11_RequestConfigAPI(finall_Versions, ConfigAPI);
                    if (!content.equals("Error")) {
                        JSONObject jsonObject = JSON.parseObject(content);
                        String State = jsonObject.getString("status");
                        if (State.equals("11006")) {
                            do {
                                content = Method_11_RequestConfigAPI(finall_Versions, ConfigAPI);
                                JSONObject jsonObject2 = JSON.parseObject(content);
                                State = jsonObject2.getString("status");
                                Thread.sleep(2000);
                                System.out.println("进入循环");
                            } while (State.equals("11006"));
                        }
                        System.out.println("下载完成:\t" + i);
                        saveUntil.Method_SaveFile(Config_savePath + i + "_Config.txt", content);
                        dao_version.Method_1_ChangeDownState_byGroupNumber(i);
                    }
                }
            }

            BeanList_FenZu.clear();
        }
    }

    public void Method_10_Down_Config_oneGroup(int groupNumber,String Config_savePath, String ConfigAPI) throws InterruptedException {
        Dao_version dao_version = new Dao_version(0, 3);
        ArrayList<Object> BeanList_FenZu = dao_version.MethodFind_HaveParam_FenZhu(groupNumber);
        System.out.println("组内个数: " + BeanList_FenZu.size());
        StringBuilder VersionIDs = new StringBuilder();

        if (BeanList_FenZu.size() != 0) {
            for (int j = 0; j < BeanList_FenZu.size(); j++) {
                Bean_version bean_version_fenzu = (Bean_version) BeanList_FenZu.get(j);
                VersionIDs.append(",").append(bean_version_fenzu.getC_version_id());
            }
            String finall_Versions = VersionIDs.substring(1);
            System.out.println(finall_Versions);
            String DownState = ((Bean_version) BeanList_FenZu.get(0)).getDownState();

            if (DownState.equals("否")) {
                String content = Method_11_RequestConfigAPI(finall_Versions, ConfigAPI);
                if (!content.equals("Error")) {
                    JSONObject jsonObject = JSON.parseObject(content);
                    String State = jsonObject.getString("status");
                    if (State.equals("11006")) {
                        do {
                            content = Method_11_RequestConfigAPI(finall_Versions, ConfigAPI);
                            JSONObject jsonObject2 = JSON.parseObject(content);
                            State = jsonObject2.getString("status");
                            Thread.sleep(2000);
                            System.out.println("进入循环");
                        } while (State.equals("11006"));
                    }
                    System.out.println("下载完成:\t" + groupNumber);
                    saveUntil.Method_SaveFile(Config_savePath + groupNumber + "_Config.txt", content);
                    dao_version.Method_1_ChangeDownState_byGroupNumber(groupNumber);
                }
            }
        }

        BeanList_FenZu.clear();
    }

    public String Method_11_RequestConfigAPI(String versionID, String ConfigeUrl) {
        String resultJson = "Error";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String param = "{\"carIds\":\"" + versionID + "\",\"cityId\":\"201\"}";

        String o = "19DDD1FBDFF065D3A4DA777D2D7A81EC";
        String s = "cid=" + 508 + "&param=" + param + o + timestamp;
        String md5_Str = MD5Until.Method_Make_MD5(s);
        //System.out.println(md5_Str);
        String Cookie = "CIGUID=849ec451-0627-4ee7-8139-7d0a7233d10a; auto_id=6618b3b2d19037859fee9321a2165348; UserGuid=849ec451-0627-4ee7-8139-7d0a7233d10a; CIGDCID=1f1c18bfd1a13ef3a7c0b2edee9ef3ca; G_CIGDCID=1f1c18bfd1a13ef3a7c0b2edee9ef3ca; selectcity=110100; selectcityid=201; selectcityName=%E5%8C%97%E4%BA%AC; Hm_lvt_610fee5a506c80c9e1a46aa9a2de2e44=1702474832,1703750079; isWebP=true; locatecity=110100; bitauto_ipregion=101.27.236.186%3A%E6%B2%B3%E5%8C%97%E7%9C%81%E4%BF%9D%E5%AE%9A%E5%B8%82%3B201%2C%E5%8C%97%E4%BA%AC%2Cbeijing; yiche_did=5e58467469f6b2c8732f3492175f2a13_._1000_._0_._847319_._905548_._W2311281141108493357; csids=8014_2556_2855_5476_10188_6435_6209_2573_3750_5786; Hm_lpvt_610fee5a506c80c9e1a46aa9a2de2e44=1703842561";
        try {
            String param_url = URLEncoder.encode(param, "UTF-8");
            String main_url = ConfigeUrl + "cid=508&param=" + param_url;
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
            Thread.sleep(1080);
//            System.out.println(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    public void Method_12_Analysis_config_1_GetCoumens(String savePath) {
        Dao_configcolumn dao_configcolumn = new Dao_configcolumn(0, 4);
        List<String> fileList = readUntil.getFileNames(savePath);
        for (String file:fileList){
            System.out.println(file);
            String content = readUntil.Method_ReadFile(savePath+file);
            JSONObject jsonObject = JSON.parseObject(content).getJSONObject("data");
            JSONArray Items = jsonObject.getJSONArray("list");

            if (Items!=null){
                if (Items.size()!=0){
                    for (int i = 0; i < Items.size(); i++) {
                        JSONObject Item_One_Title = Items.getJSONObject(i);
//            一级标题
                        String title_1 = Item_One_Title.getString("name");
                        JSONArray Items2 = Item_One_Title.getJSONArray("items");

                        for (int j = 0; j < Items2.size(); j++) {
                            JSONObject Item_Two_Title = Items2.getJSONObject(j);
                            String title_2 = title_1+"_"+Item_Two_Title.getString("name");
//                        System.out.println(title_2);

                            Bean_configcolumn bean_configcolumn = new Bean_configcolumn();
                            bean_configcolumn.setC_column_one(title_1);
                            bean_configcolumn.setC_column_two(title_2);
                            dao_configcolumn.MethodInsert(bean_configcolumn);
                        }
                    }
                }else {
                    saveUntil.Method_SaveFile_True("E:\\ZKZD2023\\易车网\\Erroe.txt", file+"\t");
                }
            }


        }
    }

    public void Method_12_Analysis_Config_2_GetCoumns(String content){
        Dao_configcolumn dao_configcolumn = new Dao_configcolumn(0, 4);
        JSONObject jsonObject = JSON.parseObject(content).getJSONObject("data");
        JSONArray Items = jsonObject.getJSONArray("list");
        boolean b = (Items == null);
        if (!b){
            if (Items.size()!=0){
                for (int i = 0; i < Items.size(); i++) {
                    JSONObject Item_One_Title = Items.getJSONObject(i);

//            一级标题
                    String title_1 = Item_One_Title.getString("name");


                    JSONArray Items2 = Item_One_Title.getJSONArray("items");

                    for (int j = 0; j < Items2.size(); j++) {
                        JSONObject Item_Two_Title = Items2.getJSONObject(j);
                        String title_2 = title_1+"_"+Item_Two_Title.getString("name");
//                        System.out.println(title_2);

                        Bean_configcolumn bean_configcolumn = new Bean_configcolumn();
                        bean_configcolumn.setC_column_one(title_1);
                        bean_configcolumn.setC_column_two(title_2);
//                    dao_configcolumn.MethodInsert(bean_configcolumn);
                    }
                }
            }else {
//            saveUntil.Method_SaveFile_True("E:\\ZKZD2023\\易车网\\Erroe.txt", file+"\t");
            }
        }

    }


    public String Method_10_Down_Config(String main_url) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            browser.newContext(new Browser.NewContextOptions()
                    .setIgnoreHTTPSErrors(true)
                    .setJavaScriptEnabled(true)
//                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                    .setViewportSize(2880, 1800));
            Page page = browser.newPage();
            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
            headers.put("Referer", "https://car.yiche.com/chexingduibi/?carIds=164527,158156,167742,167743");
//            headers.put("Cookie", "CIGUID=849ec451-0627-4ee7-8139-7d0a7233d10a; auto_id=6618b3b2d19037859fee9321a2165348; UserGuid=849ec451-0627-4ee7-8139-7d0a7233d10a; CIGDCID=1f1c18bfd1a13ef3a7c0b2edee9ef3ca; G_CIGDCID=1f1c18bfd1a13ef3a7c0b2edee9ef3ca; selectcity=110100; selectcityid=201; selectcityName=%E5%8C%97%E4%BA%AC; Hm_lvt_610fee5a506c80c9e1a46aa9a2de2e44=1702474832,1703750079; yiche_did=5e58467469f6b2c8732f3492175f2a13_._1000_._0_._847319_._905548_._W2311281141108493357; csids=8014_2370_10046_2855_2556_5476_10188_6435_6209_2573; isWebP=true; locatecity=110100; bitauto_ipregion=101.27.232.51%3A%E6%B2%B3%E5%8C%97%E7%9C%81%E4%BF%9D%E5%AE%9A%E5%B8%82%3B201%2C%E5%8C%97%E4%BA%AC%2Cbeijing; Hm_lpvt_610fee5a506c80c9e1a46aa9a2de2e44=1704087725");
//            headers.put("X-City-Id","201");
//            headers.put("X-Ip-Address","101.27.232.51");
//            headers.put("X-Sign","d93cb7f925b266adf9cf1bb5e4b11ff7");
//            headers.put("X-Platform","pc");
//            headers.put("X-Timestamp","1704087724582");
//            headers.put("X-User-Guid","849ec451-0627-4ee7-8139-7d0a7233d10a");


            page.setExtraHTTPHeaders(headers);
            // 启用 JavaScript

            page.navigate(main_url);

            // 等待页面加载完成
            page.waitForLoadState(LoadState.LOAD);

            // 获取页面源码
//            String pageSource = page.content();

            //System.out.println(pageSource);
            Thread.sleep(1800);

            List<ElementHandle> lis = page.querySelectorAll("pre");

            StringBuilder sb = new StringBuilder();

            for (ElementHandle li : lis) {
                sb.append(li.innerHTML());
            }

//            System.out.println(sb.toString());

            Thread.sleep(200);
            // 关闭浏览器
//            Thread.sleep(100000);
            browser.close();
            return sb.toString();


        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public void Method_13_Down_ModelPic(String modelPicPath){
        Dao_model dao_model = new Dao_model(0, 2);
        ArrayList<Object> beanList = dao_model.MethodFind();

        for (Object bean : beanList) {
            Bean_model bean_model = (Bean_model) bean;
            String picurl = bean_model.getC_ModelPicUrl();
            String modelID = bean_model.getC_ModelID();
            saveUntil.Method_SavePic(modelPicPath, modelID + ".png", picurl);
        }
    }


}
