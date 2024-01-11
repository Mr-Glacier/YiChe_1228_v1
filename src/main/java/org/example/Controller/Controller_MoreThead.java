package org.example.Controller;

import org.example.Dao_pic.Dao_version_360;
import org.example.Entity_pic.Version_out_pic;
import org.example.Until.SentEmail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller_MoreThead extends Thread {
//    多线程测试使用

    private String threadName;

    private List<Object> CommentsList = new ArrayList<>();


    private String mainPath = "F:\\A_ZKZD_2024\\易车图片\\version_360\\version_out_360\\";

    public Controller_MoreThead(String threadName, List<Object> CommentsList) {
        this.threadName = threadName;
        this.CommentsList = new CopyOnWriteArrayList<>(CommentsList);
    }


    @Override
    public void run() {
        System.out.println("线程名称" + threadName);
        Method_12_Down_360outPic(mainPath);
    }

    public void Method_12_Down_360outPic(String outpicSavePath){
        Dao_version_360 dao_version_360_out = new Dao_version_360(1, 4);
//
//        ArrayList<Object> BeanList  = dao_version_360_out.MethodFind();

        for (Object bean:CommentsList){
            Version_out_pic version = (Version_out_pic) bean;

            String picurl =  "https:"+version.getC_url().replace("{0}", "");
            String styleId = version.getC_styleId();

            File file = new File(outpicSavePath+styleId+"\\");
            if (!file.exists()){
                boolean a = file.mkdirs();
                System.out.println("创建文件夹\t" + styleId);
//                System.out.println("创建文件夹 :\t"+outpicSavePath+styleId+"\\" +"\t"+a);
            }
            int C_ID = version.getC_ID();
            String model_id = version.getC_modelId();
            String version_id = version.getC_styleId();
            String color_id= version.getC_colorId();
            String num = version.getC_num();
            String wight = version.getC_weight();
            String type = version.getC_listtype();
            String DownState = version.getDownState();
            String picName = model_id +"_"+version_id+"_"+color_id+"_"+num+"_"+wight+"_"+type+".png";
            if(DownState.equals("否")){
                boolean b = Method_SavePic(outpicSavePath+styleId+"\\",picName,picurl);
                if (b){
                    dao_version_360_out.Method_ChangeState(C_ID);
                }
            }
        }
    }

    public boolean Method_SavePic(String path,String fileName,String urlString){

        synchronized (this) {
            try {
                // 构造URL
                URL url = new URL(urlString);
                // 打开连接
                URLConnection con = url.openConnection();
                // 输入流
                InputStream is = con.getInputStream();
                // 1K的数据缓冲
                byte[] bs = new byte[81920];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                String filename = path + fileName;  //下载路径及下载图片名称
                File file = new File(filename);
                FileOutputStream os = new FileOutputStream(file, true);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                // 完毕，关闭所有链接
                os.close();
                is.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    public static void main(String[] args) {
        Dao_version_360 dao_version_360_out = new Dao_version_360(1, 4);
        ArrayList<Object> list1 = dao_version_360_out.MethodFind_outPic(1);
        ArrayList<Object> list2 = dao_version_360_out.MethodFind_outPic(2);
        ArrayList<Object> list3 = dao_version_360_out.MethodFind_outPic(3);
        Controller_MoreThead thread1 = new Controller_MoreThead("Thread-1", list1);
        Controller_MoreThead thread2 = new Controller_MoreThead("Thread-2", list2);
        Controller_MoreThead thread3 = new Controller_MoreThead("Thread-3", list3);
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

// 主线程会等待 thread1 和 thread2 执行完毕后再继续执行
        System.out.println("All threads have finished.");

        SentEmail sentEmail = new SentEmail();
        sentEmail.Method_SentEmail(0, "多线程下载完成.....");

    }
}
