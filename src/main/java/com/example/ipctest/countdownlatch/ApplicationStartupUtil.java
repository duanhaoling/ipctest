package com.example.ipctest.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ldh on 2017/4/21 0021.
 * <p>
 * 模拟了一个应用程序启动类，它开始时启动了n个线程类，
 * 这些线程将检查外部系统并通知闭锁，并且启动类一直在闭锁上等待着。一旦验证和检查了所有外部服务，那么启动类恢复执行。
 */

public class ApplicationStartupUtil {
    //List of service checkers
    private static List<BaseHealthChecker> _services;

    //This latch will be used to wait on
    private static CountDownLatch _latch;

    private ApplicationStartupUtil() {
    }

    //饿汉单例模式
    private final static ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();

    public static ApplicationStartupUtil getInstance() {
        return INSTANCE;
    }

    public static boolean checkExternalServices() throws Exception {
        //Initialize the latch with number of service checkers
        _latch = new CountDownLatch(3);

        //All add checker in lists
        _services = new ArrayList<BaseHealthChecker>();
        _services.add(new NetworkHealthChecker(_latch));
        _services.add(new CacheHealthChecker(_latch));
        _services.add(new DatabaseHealthChecker(_latch));

        //Start service checkers using executor framework
        Executor executor = Executors.newFixedThreadPool(_services.size());

        for (final BaseHealthChecker v : _services) {
            executor.execute(v);
        }

        //Now wait till all services are checked
        _latch.await();

        //Services are file and now proceed startup
        for (final BaseHealthChecker v : _services) {
            if (!v.isServiceUp()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        boolean result = false;
        try {
            result = checkExternalServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("External services validation completed !! Result was :: " + result);
    }
}
