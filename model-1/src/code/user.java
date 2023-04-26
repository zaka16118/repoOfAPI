package code;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class user extends sub {

    static String x="1";
    static int y=1;
    public static int a = 1;
    public static void main(String[] args) {
        t1 t123 = new t1();
        t123.start();
        System.out.println("main");
    }




}
class t1 extends Thread{
    @Override
    public void run() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("runnnning");
        System.out.println("aaaaaaa");
    }
}