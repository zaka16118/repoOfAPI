import model.Student;
import model.UserService;
import model.impl.UserServiceImpl;

import java.io.*;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.*;

public class MyApplication {

    ThreadLocal<Long> longLocal = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return Thread.currentThread().getId();
        }
    };
    ThreadLocal<String> stringLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return Thread.currentThread().getName();
        }
    };


    void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }

    long getLong() {
        return longLocal.get();
    }

    String getString() {
        return stringLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        MyApplication myApplication = new MyApplication();
        System.out.println("ThreadId: "+myApplication.getLong());
        System.out.println("ThreadName: "+myApplication.getString());

        Thread t1 = new Thread() {
            @Override
            public void run() {
                myApplication.set();
                System.out.println("ThreadId: "+myApplication.getLong());
                System.out.println("ThreadName: "+myApplication.getString());
            }
        };

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myApplication.set();
                System.out.println("ThreadId: "+myApplication.getLong());
                System.out.println("ThreadName: "+myApplication.getString());
            }
        });

        WeakReference<String> sr = new WeakReference<String>(new String("hello"));
        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());
    }




    //测试对象序列化
    void serializableTest() throws Exception {
        File file = new File("student.out");
        //序列化
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        Student student = new Student("zxj", 123);
        outputStream.writeObject(student);
        outputStream.close();
        //反序列化
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        Object getStudent = inputStream.readObject();
        inputStream.close();
        System.out.println(getStudent);
    }

}
 class Obj{
    int[] obj ;
    public Obj(){
        obj = new int[1000];
    }
}