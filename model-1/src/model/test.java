package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class test {
    test(){
        System.out.println("f父类");
    }
    final static ThreadLocal<Integer> threadLocalInteget = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        int[] a = {1, 2, 4};
        System.out.println("main a[0]:" + a[0]);
        threadLocalInteget.set(1);
        System.out.println("main local:"+threadLocalInteget.get());
        String s = "aaa";
        System.out.println("main thread start ------");
        Thread thread = new Thread() {
            @Override
            public void run() {
                a[0] = 7;
                System.out.println("main a[0] in new Thread:" + a[0]);
                System.out.println("CurrentThread name: " + Thread.currentThread().getName());
                threadLocalInteget.set(3);
                System.out.println("noName Thread localInteger: "+threadLocalInteget.get());
                t2.start();
                try {
//                    sleep(1000);
//                    t2.interrupt();
                    System.out.println("main 睡醒了");
                    System.out.println("s的值: " + s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
//        for (int i = 0;i < 10;i++){
//            System.out.println("main is running ------");
//
//        }
//        t1.sleep(5000);
//
//        System.out.println("CurrentThread name: "+Thread.currentThread().getName());
//        t1.start();

        thread.start();
        Thread.sleep(5000); //让当前main线程sleep下来，等待要验证的其他线程执行完各自的任务
        System.out.println("after update in main a[0]:" + a[0]);
        System.out.println("after update main localInteger : "+threadLocalInteget.get());
        System.out.println("main ending------");
    }

    public static Thread t2 = new Thread() {
        @Override
        public void run() {
            System.out.println("CurrentThread name: " + Thread.currentThread().getName());
            System.out.println("t2 线程开启");
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "被打断了");
                e.printStackTrace();
            }
            System.out.println("t2 is running ------");
        }
    };

    public static Thread t1 = new Thread() {
        @Override
        public void run() {
            System.out.println("CurrentThread name: " + Thread.currentThread().getName());
            for (int i = 0; i < 10; i++) {
                System.out.println("t1 is running ------");
            }
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    //获取1-100之间的质数和
    public static int getSum() {
        int sum = 0;
        for (int i = 2; i <= 100; i++) {
            if (test.zs(i)) {
                System.out.println(i + "是质数");
                sum += i;
            }
        }
        return sum;
    }

    //判断是否为质数 prime number
    public static boolean zs(int val) {
        int v = val;
        boolean flag = true;
        for (int i = 2; i < v; i++) {
            if (v % i == 0) {
                //break;
                flag = false;
                break;
            }
        }
        return flag;
    }

}
