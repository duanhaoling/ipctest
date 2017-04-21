package com.example.ipctest.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ldh on 2017/4/21 0021.
 */

public class CacheHealthChecker extends BaseHealthChecker {
    public CacheHealthChecker(CountDownLatch latch) {
        super("Cache Service", latch);
    }

    @Override
    public void verifyService() {
        System.out.println("Checking " + this.getServiceName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
