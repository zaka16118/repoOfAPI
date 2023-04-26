package model;

import java.lang.*;
import java.util.*;
import java.util.concurrent.*;

public class emptyTest extends chouxiangClass {

    test t= new test();

 emptyTest(){
     System.out.println("aa");
 }
    public static void updateList(List<Integer> list){
            list.set(3,3333333);
    }
    public static int getValue(int param) {

        int i = param;
        try {
            System.out.println(i);
            return ++i;
        } finally {
            ++i;
            System.out.println(i);
            return i;
        }
    }

    @Override
    public void func1() {

    }

    public static void main(String[] args)  {
        System.out.println(100%3);
        System.out.println(100%3.0f);
        System.out.println(100%3.0);
        System.out.println(100%3.0);
        System.out.println(3.0*4);
    }
//        ExecutorService executorService = Executors.newFixedThreadPool(6);
//        Future future = executorService.submit(new MyCallable());
//        System.out.println(Thread.currentThread().getName()+"------"+future.get());
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 8, 60L
//                , TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
//        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 60L
//                , TimeUnit.MINUTES, new ArrayBlockingQueue<>(20));


    //todo （i+=i-=i*=i） ==> i=i+(i-i*i)=(2-i)*i=-10*12= -120

}


class MyCallable implements Callable {
    @Override
    public Object call() throws Exception {
        return 4 + 5;
    }
}

