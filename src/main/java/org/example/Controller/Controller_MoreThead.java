package org.example.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller_MoreThead extends Thread {
    private static final Lock lock = new ReentrantLock();
    private String threadName;

    private List<Object> CommentsList = new ArrayList<>();


    private String mainPath = "E:\\ZKZD2023\\大V项目\\多线程测试\\";
    private String typePath = "7_SecondCommentsData\\";
}
