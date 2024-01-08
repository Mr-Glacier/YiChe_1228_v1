package org.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.example.Dao.Dao_choseConfig;
import org.example.Dao.Dao_config;
import org.example.Entity.Bean_config_chose;
import org.example.Entity.Bean_version_config;
import org.example.Until.ReadUntil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller_Config {


    public static void Method_1_Analysis_baseConfig(String contentJSON,String filename) {

//        1.构建哈希Map
        Map<String, String> columnMap = new HashMap<>();
        columnMap.put("被动安全_被动行人保护", "C_被动安全_被动行人保护");
        columnMap.put("被动安全_侧安全气帘", "C_被动安全_侧安全气帘");
        columnMap.put("被动安全_第二排侧气囊", "C_被动安全_第二排侧气囊");
        columnMap.put("被动安全_第二排正向气囊", "C_被动安全_第二排正向气囊");
        columnMap.put("被动安全_第一排侧气囊", "C_被动安全_第一排侧气囊");
        columnMap.put("被动安全_儿童座椅接口", "C_被动安全_儿童座椅接口");
        columnMap.put("被动安全_副驾驶安全气囊", "C_被动安全_副驾驶安全气囊");
        columnMap.put("被动安全_副驾驶座垫式气囊", "C_被动安全_副驾驶座垫式气囊");
        columnMap.put("被动安全_后排安全带式气囊", "C_被动安全_后排安全带式气囊");
        columnMap.put("被动安全_后排座椅防下滑气囊", "C_被动安全_后排座椅防下滑气囊");
        columnMap.put("被动安全_缺气保用轮胎", "C_被动安全_缺气保用轮胎");
        columnMap.put("被动安全_膝部气囊", "C_被动安全_膝部气囊");
        columnMap.put("被动安全_中央安全气囊", "C_被动安全_中央安全气囊");
        columnMap.put("被动安全_主驾驶安全气囊", "C_被动安全_主驾驶安全气囊");
        columnMap.put("变速箱_变速箱类型", "C_变速箱_变速箱类型");
        columnMap.put("变速箱_变速箱描述", "C_变速箱_变速箱描述");
        columnMap.put("变速箱_挡位数", "C_变速箱_挡位数");
        columnMap.put("变速箱_卡车倒退挡位数", "C_变速箱_卡车倒退挡位数");
        columnMap.put("变速箱_卡车前进挡位数", "C_变速箱_卡车前进挡位数");
        columnMap.put("玻璃/后视镜_车窗防夹手功能", "C_玻璃后视镜_车窗防夹手功能");
        columnMap.put("玻璃/后视镜_车窗一键升降", "C_玻璃后视镜_车窗一键升降");
        columnMap.put("玻璃/后视镜_车内化妆镜", "C_玻璃后视镜_车内化妆镜");
        columnMap.put("玻璃/后视镜_第二排电动车窗", "C_玻璃后视镜_第二排电动车窗");
        columnMap.put("玻璃/后视镜_第一排电动车窗", "C_玻璃后视镜_第一排电动车窗");
        columnMap.put("玻璃/后视镜_多层隔音玻璃", "C_玻璃后视镜_多层隔音玻璃");
        columnMap.put("玻璃/后视镜_感应雨刷功能", "C_玻璃后视镜_感应雨刷功能");
        columnMap.put("玻璃/后视镜_后风挡遮阳帘", "C_玻璃后视镜_后风挡遮阳帘");
        columnMap.put("玻璃/后视镜_后排侧隐私玻璃", "C_玻璃后视镜_后排侧隐私玻璃");
        columnMap.put("玻璃/后视镜_后排侧遮阳帘", "C_玻璃后视镜_后排侧遮阳帘");
        columnMap.put("玻璃/后视镜_后雨刷", "C_玻璃后视镜_后雨刷");
        columnMap.put("玻璃/后视镜_可加热喷水嘴", "C_玻璃后视镜_可加热喷水嘴");
        columnMap.put("玻璃/后视镜_流媒体外后视镜显示屏", "C_玻璃后视镜_流媒体外后视镜显示屏");
        columnMap.put("玻璃/后视镜_内后视镜功能", "C_玻璃后视镜_内后视镜功能");
        columnMap.put("玻璃/后视镜_外后视镜功能", "C_玻璃后视镜_外后视镜功能");
        columnMap.put("车机/互联_AR实景导航", "C_车机互联_AR实景导航");
        columnMap.put("车机/互联_OTA升级", "C_车机互联_OTA升级");
        columnMap.put("车机/互联_V2X通讯", "C_车机互联_V2X通讯");
        columnMap.put("车机/互联_车机WiFi功能", "C_车机互联_车机WiFi功能");
        columnMap.put("车机/互联_车机网络", "C_车机互联_车机网络");
        columnMap.put("车机/互联_车机系统", "C_车机互联_车机系统");
        columnMap.put("车机/互联_车机系统存储[GB]", "C_车机互联_车机系统存储GB");
        columnMap.put("车机/互联_车机系统内存[GB]", "C_车机互联_车机系统内存GB");
        columnMap.put("车机/互联_车机芯片", "C_车机互联_车机芯片");
        columnMap.put("车机/互联_车载导航地图", "C_车机互联_车载导航地图");
        columnMap.put("车机/互联_道路救援呼叫", "C_车机互联_道路救援呼叫");
        columnMap.put("车机/互联_多指飞屏操控", "C_车机互联_多指飞屏操控");
        columnMap.put("车机/互联_副驾屏幕", "C_车机互联_副驾屏幕");
        columnMap.put("车机/互联_副驾屏幕材质", "C_车机互联_副驾屏幕材质");
        columnMap.put("车机/互联_副驾屏幕尺寸[英寸]", "C_车机互联_副驾屏幕尺寸英寸");
        columnMap.put("车机/互联_副驾屏幕分辨率[px]", "C_车机互联_副驾屏幕分辨率px");
        columnMap.put("车机/互联_副驾屏幕刷新率[Hz]", "C_车机互联_副驾屏幕刷新率Hz");
        columnMap.put("车机/互联_副驾屏幕像素密度[PPI]", "C_车机互联_副驾屏幕像素密度PPI");
        columnMap.put("车机/互联_高精地图", "C_车机互联_高精地图");
        columnMap.put("车机/互联_蓝牙/车载电话", "C_车机互联_蓝牙车载电话");
        columnMap.put("车机/互联_面部识别", "C_车机互联_面部识别");
        columnMap.put("车机/互联_手机互联/映射", "C_车机互联_手机互联映射");
        columnMap.put("车机/互联_手势控制", "C_车机互联_手势控制");
        columnMap.put("车机/互联_语音分区唤醒", "C_车机互联_语音分区唤醒");
        columnMap.put("车机/互联_语音连续识别", "C_车机互联_语音连续识别");
        columnMap.put("车机/互联_语音免唤醒", "C_车机互联_语音免唤醒");
        columnMap.put("车机/互联_语音识别控制功能", "C_车机互联_语音识别控制功能");
        columnMap.put("车机/互联_语音助手唤醒词", "C_车机互联_语音助手唤醒词");
        columnMap.put("车机/互联_远程控制功能", "C_车机互联_远程控制功能");
        columnMap.put("车机/互联_中控彩色屏幕", "C_车机互联_中控彩色屏幕");
        columnMap.put("车机/互联_中控屏幕材质", "C_车机互联_中控屏幕材质");
        columnMap.put("车机/互联_中控屏幕尺寸[英寸]", "C_车机互联_中控屏幕尺寸英寸");
        columnMap.put("车机/互联_中控屏幕分辨率[px]", "C_车机互联_中控屏幕分辨率px");
        columnMap.put("车机/互联_中控屏幕亮度[nit]", "C_车机互联_中控屏幕亮度nit");
        columnMap.put("车机/互联_中控屏幕色彩[万色]", "C_车机互联_中控屏幕色彩万色");
        columnMap.put("车机/互联_中控屏幕刷新率[Hz]", "C_车机互联_中控屏幕刷新率Hz");
        columnMap.put("车机/互联_中控屏幕像素密度[PPI]", "C_车机互联_中控屏幕像素密度PPI");
        columnMap.put("车机/互联_中控下屏幕材质", "C_车机互联_中控下屏幕材质");
        columnMap.put("车机/互联_中控下屏幕尺寸[英寸]", "C_车机互联_中控下屏幕尺寸英寸");
        columnMap.put("车机/互联_中控下屏幕分辨率[px]", "C_车机互联_中控下屏幕分辨率px");
        columnMap.put("车机/互联_中控下屏幕像素密度[PPI]", "C_车机互联_中控下屏幕像素密度PPI");
        columnMap.put("车轮制动_备胎", "C_车轮制动_备胎");
        columnMap.put("车轮制动_备胎放置方式", "C_车轮制动_备胎放置方式");
        columnMap.put("车轮制动_后轮胎规格", "C_车轮制动_后轮胎规格");
        columnMap.put("车轮制动_后制动器类型", "C_车轮制动_后制动器类型");
        columnMap.put("车轮制动_轮胎规格", "C_车轮制动_轮胎规格");
        columnMap.put("车轮制动_轮胎数", "C_车轮制动_轮胎数");
        columnMap.put("车轮制动_前轮胎规格", "C_车轮制动_前轮胎规格");
        columnMap.put("车轮制动_前制动器类型", "C_车轮制动_前制动器类型");
        columnMap.put("车轮制动_驻车制动类型", "C_车轮制动_驻车制动类型");
        columnMap.put("车身_车门开启方式", "C_车身_车门开启方式");
        columnMap.put("车身_车门数", "C_车身_车门数");
        columnMap.put("车身_车型种类", "C_车身_车型种类");
        columnMap.put("车身_风阻系数[Cd]", "C_车身_风阻系数Cd");
        columnMap.put("车身_高度[mm]", "C_车身_高度mm");
        columnMap.put("车身_官方后备厢容积[L]", "C_车身_官方后备厢容积L");
        columnMap.put("车身_官方前备厢容积[L]", "C_车身_官方前备厢容积L");
        columnMap.put("车身_后轮距[mm]", "C_车身_后轮距mm");
        columnMap.put("车身_货箱高度[mm]", "C_车身_货箱高度mm");
        columnMap.put("车身_货箱宽度[mm]", "C_车身_货箱宽度mm");
        columnMap.put("车身_货箱长度[mm]", "C_车身_货箱长度mm");
        columnMap.put("车身_接近角[°]", "C_车身_接近角");
        columnMap.put("车身_空载最小离地间隙[mm]", "C_车身_空载最小离地间隙mm");
        columnMap.put("车身_宽度[mm]", "C_车身_宽度mm");
        columnMap.put("车身_离去角[°]", "C_车身_离去角");
        columnMap.put("车身_满载质量[kg]", "C_车身_满载质量kg");
        columnMap.put("车身_满载最小离地间隙[mm]", "C_车身_满载最小离地间隙mm");
        columnMap.put("车身_气罐容积[L]", "C_车身_气罐容积L");
        columnMap.put("车身_牵引质量[kg]", "C_车身_牵引质量kg");
        columnMap.put("车身_前轮距[mm]", "C_车身_前轮距mm");
        columnMap.put("车身_实测后备厢容积[L]", "C_车身_实测后备厢容积L");
        columnMap.put("车身_实测前备厢容积[L]", "C_车身_实测前备厢容积L");
        columnMap.put("车身_通过角[°]", "C_车身_通过角");
        columnMap.put("车身_油箱容积[L]", "C_车身_油箱容积L");
        columnMap.put("车身_载重质量[kg]", "C_车身_载重质量kg");
        columnMap.put("车身_长度[mm]", "C_车身_长度mm");
        columnMap.put("车身_整备质量[kg]", "C_车身_整备质量kg");
        columnMap.put("车身_轴距[mm]", "C_车身_轴距mm");
        columnMap.put("车身_最大爬坡度[%]", "C_车身_最大爬坡度");
        columnMap.put("车身_最小转弯半径[m]", "C_车身_最小转弯半径m");
        columnMap.put("车身_座位数", "C_车身_座位数");
        columnMap.put("灯光功能_LED日间行车灯", "C_灯光功能_LED日间行车灯");
        columnMap.put("灯光功能_车内氛围灯", "C_灯光功能_车内氛围灯");
        columnMap.put("灯光功能_大灯功能", "C_灯光功能_大灯功能");
        columnMap.put("灯光功能_大灯清洗装置", "C_灯光功能_大灯清洗装置");
        columnMap.put("灯光功能_灯光特色功能", "C_灯光功能_灯光特色功能");
        columnMap.put("灯光功能_近光灯光源", "C_灯光功能_近光灯光源");
        columnMap.put("灯光功能_前雾灯", "C_灯光功能_前雾灯");
        columnMap.put("灯光功能_远光灯光源", "C_灯光功能_远光灯光源");
        columnMap.put("灯光功能_主动式环境氛围灯", "C_灯光功能_主动式环境氛围灯");
        columnMap.put("灯光功能_转向辅助灯", "C_灯光功能_转向辅助灯");
        columnMap.put("底盘转向_差速锁", "C_底盘转向_差速锁");
        columnMap.put("底盘转向_车体结构", "C_底盘转向_车体结构");
        columnMap.put("底盘转向_后桥描述", "C_底盘转向_后桥描述");
        columnMap.put("底盘转向_后桥速比", "C_底盘转向_后桥速比");
        columnMap.put("底盘转向_后桥允许载荷[kg]", "C_底盘转向_后桥允许载荷kg");
        columnMap.put("底盘转向_后悬架类型", "C_底盘转向_后悬架类型");
        columnMap.put("底盘转向_缓速器类型", "C_底盘转向_缓速器类型");
        columnMap.put("底盘转向_卡车驱动形式", "C_底盘转向_卡车驱动形式");
        columnMap.put("底盘转向_可调悬架功能", "C_底盘转向_可调悬架功能");
        columnMap.put("底盘转向_可调悬架种类", "C_底盘转向_可调悬架种类");
        columnMap.put("底盘转向_客车后悬架类型", "C_底盘转向_客车后悬架类型");
        columnMap.put("底盘转向_客车前悬架类型", "C_底盘转向_客车前悬架类型");
        columnMap.put("底盘转向_前桥描述", "C_底盘转向_前桥描述");
        columnMap.put("底盘转向_前桥允许载荷[kg]", "C_底盘转向_前桥允许载荷kg");
        columnMap.put("底盘转向_前悬架类型", "C_底盘转向_前悬架类型");
        columnMap.put("底盘转向_驱动形式", "C_底盘转向_驱动形式");
        columnMap.put("底盘转向_四驱形式", "C_底盘转向_四驱形式");
        columnMap.put("底盘转向_限滑差速器", "C_底盘转向_限滑差速器");
        columnMap.put("底盘转向_中央差速器结构", "C_底盘转向_中央差速器结构");
        columnMap.put("底盘转向_助力类型", "C_底盘转向_助力类型");
        columnMap.put("底盘转向_最大涉水深度[mm]", "C_底盘转向_最大涉水深度mm");
        columnMap.put("电池/补能_CLTC纯电续航[km]", "C_电池补能_CLTC纯电续航km");
        columnMap.put("电池/补能_CLTC综合续航[km]", "C_电池补能_CLTC综合续航km");
        columnMap.put("电池/补能_EPA纯电续航[km]", "C_电池补能_EPA纯电续航km");
        columnMap.put("电池/补能_NEDC纯电续航[km]", "C_电池补能_NEDC纯电续航km");
        columnMap.put("电池/补能_NEDC综合续航[km]", "C_电池补能_NEDC综合续航km");
        columnMap.put("电池/补能_WLTC纯电续航[km]", "C_电池补能_WLTC纯电续航km");
        columnMap.put("电池/补能_WLTC综合续航[km]", "C_电池补能_WLTC综合续航km");
        columnMap.put("电池/补能_百公里耗电量[kWh/100km]", "C_电池补能_百公里耗电量kWh100km");
        columnMap.put("电池/补能_车辆充电口", "C_电池补能_车辆充电口");
        columnMap.put("电池/补能_车辆换电", "C_电池补能_车辆换电");
        columnMap.put("电池/补能_电池电量[kWh]", "C_电池补能_电池电量kWh");
        columnMap.put("电池/补能_电池类型", "C_电池补能_电池类型");
        columnMap.put("电池/补能_电池能量密度[Wh/kg]", "C_电池补能_电池能量密度Whkg");
        columnMap.put("电池/补能_电池温度管理", "C_电池补能_电池温度管理");
        columnMap.put("电池/补能_电池组质保", "C_电池补能_电池组质保");
        columnMap.put("电池/补能_电芯品牌", "C_电池补能_电芯品牌");
        columnMap.put("电池/补能_对外放电功率", "C_电池补能_对外放电功率");
        columnMap.put("电池/补能_对外放电功能", "C_电池补能_对外放电功能");
        columnMap.put("电池/补能_高压快充平台", "C_电池补能_高压快充平台");
        columnMap.put("电池/补能_换电时间[min]", "C_电池补能_换电时间min");
        columnMap.put("电池/补能_家用充电桩", "C_电池补能_家用充电桩");
        columnMap.put("电池/补能_快充电量[%]", "C_电池补能_快充电量");
        columnMap.put("电池/补能_快充功率[kW]", "C_电池补能_快充功率kW");
        columnMap.put("电池/补能_快充口位置", "C_电池补能_快充口位置");
        columnMap.put("电池/补能_快充时间[h]", "C_电池补能_快充时间h");
        columnMap.put("电池/补能_快充时间[min]", "C_电池补能_快充时间min");
        columnMap.put("电池/补能_慢充电量[%]", "C_电池补能_慢充电量");
        columnMap.put("电池/补能_慢充功率[kW]", "C_电池补能_慢充功率kW");
        columnMap.put("电池/补能_慢充口位置", "C_电池补能_慢充口位置");
        columnMap.put("电池/补能_慢充时间[h]", "C_电池补能_慢充时间h");
        columnMap.put("电池/补能_首任车主电池组质保", "C_电池补能_首任车主电池组质保");
        columnMap.put("电池/补能_随车充电枪", "C_电池补能_随车充电枪");
        columnMap.put("电动机_电动机品牌", "C_电动机_电动机品牌");
        columnMap.put("电动机_电动机型号", "C_电动机_电动机型号");
        columnMap.put("电动机_电动机总功率[kW]", "C_电动机_电动机总功率kW");
        columnMap.put("电动机_电动机总马力[Ps]", "C_电动机_电动机总马力Ps");
        columnMap.put("电动机_电动机总扭矩[N·m]", "C_电动机_电动机总扭矩Nm");
        columnMap.put("电动机_电机布局", "C_电动机_电机布局");
        columnMap.put("电动机_电机类型", "C_电动机_电机类型");
        columnMap.put("电动机_后电动机最大功率[kW]", "C_电动机_后电动机最大功率kW");
        columnMap.put("电动机_后电动机最大扭矩[N·m]", "C_电动机_后电动机最大扭矩Nm");
        columnMap.put("电动机_前电动机最大功率[kW]", "C_电动机_前电动机最大功率kW");
        columnMap.put("电动机_前电动机最大扭矩[N·m]", "C_电动机_前电动机最大扭矩Nm");
        columnMap.put("电动机_驱动电机数", "C_电动机_驱动电机数");
        columnMap.put("电动机_系统综合功率[kW]", "C_电动机_系统综合功率kW");
        columnMap.put("电动机_系统综合马力[Ps]", "C_电动机_系统综合马力Ps");
        columnMap.put("电动机_系统综合扭矩[N·m]", "C_电动机_系统综合扭矩Nm");
        columnMap.put("发动机_发动机布局", "C_发动机_发动机布局");
        columnMap.put("发动机_发动机型号", "C_发动机_发动机型号");
        columnMap.put("发动机_缸盖材料", "C_发动机_缸盖材料");
        columnMap.put("发动机_缸体材料", "C_发动机_缸体材料");
        columnMap.put("发动机_供油方式", "C_发动机_供油方式");
        columnMap.put("发动机_环保标准", "C_发动机_环保标准");
        columnMap.put("发动机_进气形式", "C_发动机_进气形式");
        columnMap.put("发动机_每缸气门数[个]", "C_发动机_每缸气门数个");
        columnMap.put("发动机_排量[L]", "C_发动机_排量L");
        columnMap.put("发动机_排量[mL]", "C_发动机_排量mL");
        columnMap.put("发动机_配气机构", "C_发动机_配气机构");
        columnMap.put("发动机_气缸排列形式", "C_发动机_气缸排列形式");
        columnMap.put("发动机_气缸数[个]", "C_发动机_气缸数个");
        columnMap.put("发动机_燃油标号", "C_发动机_燃油标号");
        columnMap.put("发动机_压缩比", "C_发动机_压缩比");
        columnMap.put("发动机_最大功率[kW]", "C_发动机_最大功率kW");
        columnMap.put("发动机_最大功率转速[rpm]", "C_发动机_最大功率转速rpm");
        columnMap.put("发动机_最大净功率[kW]", "C_发动机_最大净功率kW");
        columnMap.put("发动机_最大马力[Ps]", "C_发动机_最大马力Ps");
        columnMap.put("发动机_最大扭矩[N·m]", "C_发动机_最大扭矩Nm");
        columnMap.put("发动机_最大扭矩转速[rpm]", "C_发动机_最大扭矩转速rpm");
        columnMap.put("辅助/操控配置_倒车车侧预警系统", "C_辅助操控配置_倒车车侧预警系统");
        columnMap.put("辅助/操控配置_低速四驱", "C_辅助操控配置_低速四驱");
        columnMap.put("辅助/操控配置_底盘透视", "C_辅助操控配置_底盘透视");
        columnMap.put("辅助/操控配置_陡坡缓降", "C_辅助操控配置_陡坡缓降");
        columnMap.put("辅助/操控配置_发动机启停", "C_辅助操控配置_发动机启停");
        columnMap.put("辅助/操控配置_防侧翻系统", "C_辅助操控配置_防侧翻系统");
        columnMap.put("辅助/操控配置_后驻车雷达", "C_辅助操控配置_后驻车雷达");
        columnMap.put("辅助/操控配置_驾驶辅助影像", "C_辅助操控配置_驾驶辅助影像");
        columnMap.put("辅助/操控配置_驾驶模式选择", "C_辅助操控配置_驾驶模式选择");
        columnMap.put("辅助/操控配置_开门碰撞预警", "C_辅助操控配置_开门碰撞预警");
        columnMap.put("辅助/操控配置_可变转向比", "C_辅助操控配置_可变转向比");
        columnMap.put("辅助/操控配置_疲劳提醒", "C_辅助操控配置_疲劳提醒");
        columnMap.put("辅助/操控配置_前驻车雷达", "C_辅助操控配置_前驻车雷达");
        columnMap.put("辅助/操控配置_蠕行模式", "C_辅助操控配置_蠕行模式");
        columnMap.put("辅助/操控配置_上坡辅助", "C_辅助操控配置_上坡辅助");
        columnMap.put("辅助/操控配置_涉水感应系统", "C_辅助操控配置_涉水感应系统");
        columnMap.put("辅助/操控配置_坦克转弯", "C_辅助操控配置_坦克转弯");
        columnMap.put("辅助/操控配置_巡航系统", "C_辅助操控配置_巡航系统");
        columnMap.put("辅助/操控配置_遥控泊车", "C_辅助操控配置_遥控泊车");
        columnMap.put("辅助/操控配置_夜视系统", "C_辅助操控配置_夜视系统");
        columnMap.put("辅助/操控配置_远程召唤", "C_辅助操控配置_远程召唤");
        columnMap.put("辅助/操控配置_整体主动转向系统", "C_辅助操控配置_整体主动转向系统");
        columnMap.put("辅助/操控配置_自动泊车", "C_辅助操控配置_自动泊车");
        columnMap.put("辅助/操控配置_自动驻车", "C_辅助操控配置_自动驻车");
        columnMap.put("辅助驾驶功能_并线辅助(BSM/BSD)", "C_辅助驾驶功能_并线辅助BSMBSD");
        columnMap.put("辅助驾驶功能_车道保持(LKAS)", "C_辅助驾驶功能_车道保持LKAS");
        columnMap.put("辅助驾驶功能_车道居中保持", "C_辅助驾驶功能_车道居中保持");
        columnMap.put("辅助驾驶功能_车道偏离预警(LDWS)", "C_辅助驾驶功能_车道偏离预警LDWS");
        columnMap.put("辅助驾驶功能_城市辅助驾驶", "C_辅助驾驶功能_城市辅助驾驶");
        columnMap.put("辅助驾驶功能_倒车循迹", "C_辅助驾驶功能_倒车循迹");
        columnMap.put("辅助驾驶功能_道路交通标识识别", "C_辅助驾驶功能_道路交通标识识别");
        columnMap.put("辅助驾驶功能_高速辅助驾驶", "C_辅助驾驶功能_高速辅助驾驶");
        columnMap.put("辅助驾驶功能_后方碰撞预警", "C_辅助驾驶功能_后方碰撞预警");
        columnMap.put("辅助驾驶功能_前方碰撞预警", "C_辅助驾驶功能_前方碰撞预警");
        columnMap.put("辅助驾驶功能_匝道自动驶出/入", "C_辅助驾驶功能_匝道自动驶出入");
        columnMap.put("辅助驾驶功能_主动刹车", "C_辅助驾驶功能_主动刹车");
        columnMap.put("辅助驾驶功能_自动变道辅助", "C_辅助驾驶功能_自动变道辅助");
        columnMap.put("辅助驾驶硬件_超声波雷达", "C_辅助驾驶硬件_超声波雷达");
        columnMap.put("辅助驾驶硬件_车内摄像头", "C_辅助驾驶硬件_车内摄像头");
        columnMap.put("辅助驾驶硬件_辅助驾驶系统", "C_辅助驾驶硬件_辅助驾驶系统");
        columnMap.put("辅助驾驶硬件_辅助驾驶芯片", "C_辅助驾驶硬件_辅助驾驶芯片");
        columnMap.put("辅助驾驶硬件_毫米波雷达", "C_辅助驾驶硬件_毫米波雷达");
        columnMap.put("辅助驾驶硬件_环境感知摄像头", "C_辅助驾驶硬件_环境感知摄像头");
        columnMap.put("辅助驾驶硬件_环境感知摄像头像素[万]", "C_辅助驾驶硬件_环境感知摄像头像素万");
        columnMap.put("辅助驾驶硬件_环视摄像头", "C_辅助驾驶硬件_环视摄像头");
        columnMap.put("辅助驾驶硬件_环视摄像头像素[万]", "C_辅助驾驶硬件_环视摄像头像素万");
        columnMap.put("辅助驾驶硬件_激光雷达10%反射率探测距离[m]", "C_辅助驾驶硬件_激光雷达10反射率探测距离m");
        columnMap.put("辅助驾驶硬件_激光雷达点云数量[万/秒]", "C_辅助驾驶硬件_激光雷达点云数量万秒");
        columnMap.put("辅助驾驶硬件_激光雷达品牌", "C_辅助驾驶硬件_激光雷达品牌");
        columnMap.put("辅助驾驶硬件_激光雷达数量", "C_辅助驾驶硬件_激光雷达数量");
        columnMap.put("辅助驾驶硬件_激光雷达线数", "C_辅助驾驶硬件_激光雷达线数");
        columnMap.put("辅助驾驶硬件_激光雷达型号", "C_辅助驾驶硬件_激光雷达型号");
        columnMap.put("辅助驾驶硬件_激光雷达最远探测距离[m]", "C_辅助驾驶硬件_激光雷达最远探测距离m");
        columnMap.put("辅助驾驶硬件_驾驶辅助级别", "C_辅助驾驶硬件_驾驶辅助级别");
        columnMap.put("辅助驾驶硬件_前方感知摄像头类型", "C_辅助驾驶硬件_前方感知摄像头类型");
        columnMap.put("辅助驾驶硬件_前方感知摄像头像素[万]", "C_辅助驾驶硬件_前方感知摄像头像素万");
        columnMap.put("辅助驾驶硬件_芯片算力", "C_辅助驾驶硬件_芯片算力");
        columnMap.put("基本信息_CLTC纯电续航[km]", "C_基本信息_CLTC纯电续航km");
        columnMap.put("基本信息_CLTC综合油耗[L/100km]", "C_基本信息_CLTC综合油耗L100km");
        columnMap.put("基本信息_EPA纯电续航[km]", "C_基本信息_EPA纯电续航km");
        columnMap.put("基本信息_NEDC纯电续航[km]", "C_基本信息_NEDC纯电续航km");
        columnMap.put("基本信息_NEDC综合油耗[L/100km]", "C_基本信息_NEDC综合油耗L100km");
        columnMap.put("基本信息_WLTC纯电续航[km]", "C_基本信息_WLTC纯电续航km");
        columnMap.put("基本信息_WLTC综合油耗[L/100km]", "C_基本信息_WLTC综合油耗L100km");
        columnMap.put("基本信息_变速箱", "C_基本信息_变速箱");
        columnMap.put("基本信息_厂商", "C_基本信息_厂商");
        columnMap.put("基本信息_厂商指导价", "C_基本信息_厂商指导价");
        columnMap.put("基本信息_车款名称", "C_基本信息_车款名称");
        columnMap.put("基本信息_车身结构", "C_基本信息_车身结构");
        columnMap.put("基本信息_城市参考价", "C_基本信息_城市参考价");
        columnMap.put("基本信息_电动机[Ps]", "C_基本信息_电动机Ps");
        columnMap.put("基本信息_发动机", "C_基本信息_发动机");
        columnMap.put("基本信息_官方0-100km/h加速[s]", "C_基本信息_官方0100kmh加速s");
        columnMap.put("基本信息_环保标准", "C_基本信息_环保标准");
        columnMap.put("基本信息_级别", "C_基本信息_级别");
        columnMap.put("基本信息_快充电量[%]", "C_基本信息_快充电量");
        columnMap.put("基本信息_快充时间[h]", "C_基本信息_快充时间h");
        columnMap.put("基本信息_亏电状态油耗[L/100km]", "C_基本信息_亏电状态油耗L100km");
        columnMap.put("基本信息_慢充电量[%]", "C_基本信息_慢充电量");
        columnMap.put("基本信息_慢充时间[h]", "C_基本信息_慢充时间h");
        columnMap.put("基本信息_内饰颜色", "C_基本信息_内饰颜色");
        columnMap.put("基本信息_能源类型", "C_基本信息_能源类型");
        columnMap.put("基本信息_上市时间", "C_基本信息_上市时间");
        columnMap.put("基本信息_首任车主质保政策", "C_基本信息_首任车主质保政策");
        columnMap.put("基本信息_外观颜色", "C_基本信息_外观颜色");
        columnMap.put("基本信息_长*宽*高[mm]", "C_基本信息_长宽高mm");
        columnMap.put("基本信息_整车质保", "C_基本信息_整车质保");
        columnMap.put("基本信息_最大功率/最大扭矩", "C_基本信息_最大功率最大扭矩");
        columnMap.put("基本信息_最高车速[km/h]", "C_基本信息_最高车速kmh");
        columnMap.put("空调/制冷_车内PM2.5过滤装置", "C_空调制冷_车内PM25过滤装置");
        columnMap.put("空调/制冷_车载冰箱", "C_空调制冷_车载冰箱");
        columnMap.put("空调/制冷_车载空气净化器", "C_空调制冷_车载空气净化器");
        columnMap.put("空调/制冷_第二排空调", "C_空调制冷_第二排空调");
        columnMap.put("空调/制冷_第三排空调", "C_空调制冷_第三排空调");
        columnMap.put("空调/制冷_第一排空调", "C_空调制冷_第一排空调");
        columnMap.put("空调/制冷_负离子发生器", "C_空调制冷_负离子发生器");
        columnMap.put("空调/制冷_空气质量监测", "C_空调制冷_空气质量监测");
        columnMap.put("空调/制冷_热泵空调", "C_空调制冷_热泵空调");
        columnMap.put("空调/制冷_香氛系统", "C_空调制冷_香氛系统");
        columnMap.put("内部配置_ETC装置", "C_内部配置_ETC装置");
        columnMap.put("内部配置_HUD抬头显示", "C_内部配置_HUD抬头显示");
        columnMap.put("内部配置_单踏板模式", "C_内部配置_单踏板模式");
        columnMap.put("内部配置_电动可调踏板", "C_内部配置_电动可调踏板");
        columnMap.put("内部配置_多功能方向盘", "C_内部配置_多功能方向盘");
        columnMap.put("内部配置_方向盘材质", "C_内部配置_方向盘材质");
        columnMap.put("内部配置_方向盘调节", "C_内部配置_方向盘调节");
        columnMap.put("内部配置_方向盘换挡", "C_内部配置_方向盘换挡");
        columnMap.put("内部配置_方向盘加热", "C_内部配置_方向盘加热");
        columnMap.put("内部配置_换挡形式", "C_内部配置_换挡形式");
        columnMap.put("内部配置_内置行车记录仪", "C_内部配置_内置行车记录仪");
        columnMap.put("内部配置_全液晶仪表盘", "C_内部配置_全液晶仪表盘");
        columnMap.put("内部配置_手机无线充电", "C_内部配置_手机无线充电");
        columnMap.put("内部配置_手机无线最大充电功率[W]", "C_内部配置_手机无线最大充电功率W");
        columnMap.put("内部配置_行车电脑显示屏", "C_内部配置_行车电脑显示屏");
        columnMap.put("内部配置_仪表屏幕材质", "C_内部配置_仪表屏幕材质");
        columnMap.put("内部配置_仪表屏幕尺寸[英寸]", "C_内部配置_仪表屏幕尺寸英寸");
        columnMap.put("内部配置_仪表屏幕分辨率[px]", "C_内部配置_仪表屏幕分辨率px");
        columnMap.put("内部配置_仪表屏幕像素密度[PPI]", "C_内部配置_仪表屏幕像素密度PPI");
        columnMap.put("内部配置_主动降噪", "C_内部配置_主动降噪");
        columnMap.put("外部配置_侧滑门", "C_外部配置_侧滑门");
        columnMap.put("外部配置_车侧脚踏板", "C_外部配置_车侧脚踏板");
        columnMap.put("外部配置_车顶行李架", "C_外部配置_车顶行李架");
        columnMap.put("外部配置_低速行车警示音", "C_外部配置_低速行车警示音");
        columnMap.put("外部配置_电动扰流板", "C_外部配置_电动扰流板");
        columnMap.put("外部配置_电动尾门", "C_外部配置_电动尾门");
        columnMap.put("外部配置_电吸门", "C_外部配置_电吸门");
        columnMap.put("外部配置_感应尾门", "C_外部配置_感应尾门");
        columnMap.put("外部配置_光感天幕", "C_外部配置_光感天幕");
        columnMap.put("外部配置_货箱宝", "C_外部配置_货箱宝");
        columnMap.put("外部配置_轮圈材质", "C_外部配置_轮圈材质");
        columnMap.put("外部配置_哨兵(千里眼)模式", "C_外部配置_哨兵千里眼模式");
        columnMap.put("外部配置_天窗类型", "C_外部配置_天窗类型");
        columnMap.put("外部配置_拖车钩", "C_外部配置_拖车钩");
        columnMap.put("外部配置_拖车取电口", "C_外部配置_拖车取电口");
        columnMap.put("外部配置_尾门玻璃独立开启", "C_外部配置_尾门玻璃独立开启");
        columnMap.put("外部配置_尾门位置记忆", "C_外部配置_尾门位置记忆");
        columnMap.put("外部配置_无框车门", "C_外部配置_无框车门");
        columnMap.put("外部配置_无钥匙进入", "C_外部配置_无钥匙进入");
        columnMap.put("外部配置_无钥匙启动", "C_外部配置_无钥匙启动");
        columnMap.put("外部配置_钥匙类型", "C_外部配置_钥匙类型");
        columnMap.put("外部配置_隐藏电动门把手", "C_外部配置_隐藏电动门把手");
        columnMap.put("外部配置_运动外观套件", "C_外部配置_运动外观套件");
        columnMap.put("外部配置_整车无线充电", "C_外部配置_整车无线充电");
        columnMap.put("外部配置_主动闭合式进气格栅", "C_外部配置_主动闭合式进气格栅");
        columnMap.put("外部配置_自动开合车门", "C_外部配置_自动开合车门");
        columnMap.put("影音娱乐_USB/Type-C接口数量", "C_影音娱乐_USBTypeC接口数量");
        columnMap.put("影音娱乐_车内娱乐功能", "C_影音娱乐_车内娱乐功能");
        columnMap.put("影音娱乐_车载APP应用市场", "C_影音娱乐_车载APP应用市场");
        columnMap.put("影音娱乐_车载CD/DVD", "C_影音娱乐_车载CDDVD");
        columnMap.put("影音娱乐_多媒体/充电接口", "C_影音娱乐_多媒体充电接口");
        columnMap.put("影音娱乐_分屏功能", "C_影音娱乐_分屏功能");
        columnMap.put("影音娱乐_后排多媒体屏幕材质", "C_影音娱乐_后排多媒体屏幕材质");
        columnMap.put("影音娱乐_后排多媒体屏幕尺寸[英寸]", "C_影音娱乐_后排多媒体屏幕尺寸英寸");
        columnMap.put("影音娱乐_后排多媒体屏幕分辨率[px]", "C_影音娱乐_后排多媒体屏幕分辨率px");
        columnMap.put("影音娱乐_后排多媒体屏幕数量", "C_影音娱乐_后排多媒体屏幕数量");
        columnMap.put("影音娱乐_后排多媒体屏幕刷新率[Hz]", "C_影音娱乐_后排多媒体屏幕刷新率Hz");
        columnMap.put("影音娱乐_后排多媒体屏幕像素密度[PPI]", "C_影音娱乐_后排多媒体屏幕像素密度PPI");
        columnMap.put("影音娱乐_后排控制多媒体", "C_影音娱乐_后排控制多媒体");
        columnMap.put("影音娱乐_模拟声浪", "C_影音娱乐_模拟声浪");
        columnMap.put("影音娱乐_行李厢电源接口", "C_影音娱乐_行李厢电源接口");
        columnMap.put("影音娱乐_扬声器数量", "C_影音娱乐_扬声器数量");
        columnMap.put("影音娱乐_音响品牌", "C_影音娱乐_音响品牌");
        columnMap.put("影音娱乐_座舱220V/230V电源", "C_影音娱乐_座舱220V230V电源");
        columnMap.put("主动安全_ABS防抱死", "C_主动安全_ABS防抱死");
        columnMap.put("主动安全_安全带未系提醒", "C_主动安全_安全带未系提醒");
        columnMap.put("主动安全_车内生物监测", "C_主动安全_车内生物监测");
        columnMap.put("主动安全_车身稳定控制(ESP/DSC/VSC等)", "C_主动安全_车身稳定控制ESPDSCVSC等");
        columnMap.put("主动安全_牵引力控制(ASR/TCS/TRC等)", "C_主动安全_牵引力控制ASRTCSTRC等");
        columnMap.put("主动安全_胎压监测", "C_主动安全_胎压监测");
        columnMap.put("主动安全_制动辅助(BA/EBA/BAS等)", "C_主动安全_制动辅助BAEBABAS等");
        columnMap.put("主动安全_制动力分配(EBD/CBC等)", "C_主动安全_制动力分配EBDCBC等");
        columnMap.put("座椅配置_第二排座椅电动调节", "C_座椅配置_第二排座椅电动调节");
        columnMap.put("座椅配置_第二排座椅调节方式", "C_座椅配置_第二排座椅调节方式");
        columnMap.put("座椅配置_第二排座椅功能", "C_座椅配置_第二排座椅功能");
        columnMap.put("座椅配置_第三排座椅电动调节", "C_座椅配置_第三排座椅电动调节");
        columnMap.put("座椅配置_第三排座椅调节方式", "C_座椅配置_第三排座椅调节方式");
        columnMap.put("座椅配置_第三排座椅功能", "C_座椅配置_第三排座椅功能");
        columnMap.put("座椅配置_第一排座椅功能", "C_座椅配置_第一排座椅功能");
        columnMap.put("座椅配置_电动脚托", "C_座椅配置_电动脚托");
        columnMap.put("座椅配置_福祉座椅", "C_座椅配置_福祉座椅");
        columnMap.put("座椅配置_副驾座椅电动调节", "C_座椅配置_副驾座椅电动调节");
        columnMap.put("座椅配置_副驾座椅调节方式", "C_座椅配置_副驾座椅调节方式");
        columnMap.put("座椅配置_后排杯架", "C_座椅配置_后排杯架");
        columnMap.put("座椅配置_后排折叠桌板", "C_座椅配置_后排折叠桌板");
        columnMap.put("座椅配置_后排座椅电动放倒", "C_座椅配置_后排座椅电动放倒");
        columnMap.put("座椅配置_后排座椅放倒方式", "C_座椅配置_后排座椅放倒方式");
        columnMap.put("座椅配置_加热/制冷杯架", "C_座椅配置_加热制冷杯架");
        columnMap.put("座椅配置_老板键", "C_座椅配置_老板键");
        columnMap.put("座椅配置_零重力座椅功能", "C_座椅配置_零重力座椅功能");
        columnMap.put("座椅配置_运动风格座椅", "C_座椅配置_运动风格座椅");
        columnMap.put("座椅配置_中央扶手", "C_座椅配置_中央扶手");
        columnMap.put("座椅配置_主驾座椅电动调节", "C_座椅配置_主驾座椅电动调节");
        columnMap.put("座椅配置_主驾座椅调节方式", "C_座椅配置_主驾座椅调节方式");
        columnMap.put("座椅配置_座椅布局", "C_座椅配置_座椅布局");
        columnMap.put("座椅配置_座椅材质", "C_座椅配置_座椅材质");

        try {
            JSONObject Item_data = JSON.parseObject(contentJSON).getJSONObject("data");

            JSONArray Item_list = Item_data.getJSONArray("list");

            if (null != Item_list) {
                if (Item_list.size() > 0) {

//                1.构建空的BeanList
                    ArrayList<Bean_version_config> bean_version_configs_carList = new ArrayList<>();

                    JSONArray carList = Item_list.getJSONObject(0).getJSONArray("items").getJSONObject(0).getJSONArray("paramValues");

                    for (int i = 0; i < carList.size(); i++) {
                        Bean_version_config bean_version_config = new Bean_version_config();
                        String version_id = carList.getJSONObject(i).getString("id");
                        bean_version_config.setC_version_id(version_id);
                        bean_version_config.setC_source(filename);
                        bean_version_configs_carList.add(bean_version_config);
                    }


                    for (int i = 0; i < Item_list.size(); i++) {
                        JSONObject title_one_Object = Item_list.getJSONObject(i);
                        String title_one = title_one_Object.getString("name");
                        JSONArray title_one_Items = title_one_Object.getJSONArray("items");
                        if (title_one.equals("选配包") || title_one.equals("特色配置")) {
                            System.out.println("暂不需要选配 与 特色配置");
                        } else {
                            for (int j = 0; j < title_one_Items.size(); j++) {
                                JSONObject title_two_Object = title_one_Items.getJSONObject(j);
                                String title_two = title_two_Object.getString("name");
                                String columnName = title_one + "_" + title_two;
                                JSONArray paramValues = title_two_Object.getJSONArray("paramValues");
                                for (int k = 0; k < paramValues.size(); k++) {
                                    JSONObject one_car = paramValues.getJSONObject(k);
                                    String version_id = one_car.getString("id");
                                    String value = "";
                                    JSONArray subList = one_car.getJSONArray("subList");
                                    for (int l = 0; l < subList.size(); l++) {

                                        JSONObject one_date_value = subList.getJSONObject(l);

                                        String one_value = one_date_value.getString("value");

                                        String price = "";
                                        String desc = "";
                                        if (one_date_value.containsKey("price")) {
                                            price = " price:" + (one_date_value.getString("price").equals("") ? "为空" : one_date_value.getString("price"));
                                        }
                                        if (one_date_value.containsKey("desc")) {
                                            desc = " desc:" + one_date_value.getString("desc");
                                        }
                                        value = value + one_value + price + desc;
                                    }

                                    for (int l = 0; l < bean_version_configs_carList.size(); l++) {
                                        String car_versionId = bean_version_configs_carList.get(l).getC_version_id();
                                        if (version_id.equals(car_versionId)) {
                                            Class c = bean_version_configs_carList.get(l).getClass();
                                            Field field = c.getDeclaredField(columnMap.get(columnName));
                                            field.setAccessible(true);
                                            field.set(bean_version_configs_carList.get(l), value);
                                            break;
                                        }
                                    }


                                }
                            }
                        }
                    }


                    Dao_config dao_config = new Dao_config(0, 5);
                    for (int i = 0; i < bean_version_configs_carList.size(); i++) {
                        dao_config.MethodInsert(bean_version_configs_carList.get(i));
                    }
                    bean_version_configs_carList.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Method_2_Analysis_choseConfig(String contentJSON,String filename){
        Dao_choseConfig dao_choseConfig = new Dao_choseConfig(0, 6);
        try {
            JSONObject Item_data = JSON.parseObject(contentJSON).getJSONObject("data");

            JSONArray Item_list = Item_data.getJSONArray("list");

            if (null != Item_list) {
                if (Item_list.size() > 0) {

                    for (int i = 0; i < Item_list.size(); i++) {
                        JSONObject title_one_Object = Item_list.getJSONObject(i);
                        String title_one = title_one_Object.getString("name");
                        JSONArray title_one_Items = title_one_Object.getJSONArray("items");
                        if (title_one.equals("选配包")||title_one.equals("特色配置")) {
                            for (int j = 0; j < title_one_Items.size(); j++) {
                                JSONObject title_two_Object = title_one_Items.getJSONObject(j);
                                String title_two = title_two_Object.getString("name");
                                String columnName = title_one + "_" + title_two;
                                String desc_main = title_two_Object.getString("desc");
                                JSONArray paramValues = title_two_Object.getJSONArray("paramValues");
                                for (int k = 0; k < paramValues.size(); k++) {
                                    JSONObject one_car = paramValues.getJSONObject(k);
                                    String version_id = one_car.getString("id");
                                    JSONArray subList = one_car.getJSONArray("subList");
                                    for (int l = 0; l < subList.size(); l++) {
                                        JSONObject one_date_value = subList.getJSONObject(l);
                                        String one_value = one_date_value.getString("value");
                                        String price = "为空";
                                        String desc = "为空";
                                        if (one_date_value.containsKey("price")) {
                                            price = " price:" + (one_date_value.getString("price").equals("") ? "为空" : one_date_value.getString("price"));
                                        }
                                        if (one_date_value.containsKey("desc")) {
                                            desc = " desc:" + one_date_value.getString("desc");
                                        }
                                        Bean_config_chose bean_config_chose = new Bean_config_chose();
                                        bean_config_chose.setC_version_id(version_id);
                                        bean_config_chose.setC_congig_name(columnName);
                                        bean_config_chose.setC_config_value(one_value);
                                        bean_config_chose.setC_config_desc(desc);
                                        bean_config_chose.setC_config_price(price);
                                        bean_config_chose.setC_config_describe(desc_main);
                                        bean_config_chose.setC_source(filename);
                                        bean_config_chose.setC_congig_type(title_one);
                                        dao_choseConfig.MethodInsert(bean_config_chose);
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


    public static void main(String[] args) {
        ReadUntil readUntil = new ReadUntil();
        String mainPath = "E:\\ZKZD2024\\易车网\\20240104\\versionConfig3\\";
        List<String> fileList = readUntil.getFileNames(mainPath);
        for (String fileName:fileList){
            String content = readUntil.Method_ReadFile(mainPath+fileName);
            System.out.println("本次解析 :\t "+fileName);
            Method_1_Analysis_baseConfig(content,fileName);
            //Method_2_Analysis_choseConfig(content,fileName);
//            Method_2_Analysis_specialConfig(content,fileName);
        }
    }



}
