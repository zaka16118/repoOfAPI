import model.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MGGG1 {
    public static void main(String[] args)  {

        double i = divideFunc(23, 0);
        System.out.println(i);
        System.out.println("jhhhh");
        try {
            throwChecked(7);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throwRuntime(3);
    }
    public static void throwRuntime(int a){
        if( a > 0) {
            // 自行抛出 RuntimeException 异常，既可以显式捕获该异常
            // 也可完全不理会该异常，把该异常交给该方法调用者处理
            throw new RuntimeException("a 的值大于 0，不符合要求");
        }
    }

    public static void throwChecked(int a) throws Exception{
        if (a > 0){
            //自行抛出 Exception 异常
            //该代码必须处于 try 块里，或处于带 throws 声明的方法中
            throw new Exception("a 的值大于 0，不符合要求");
        }
    }
    private static double divideFunc(int a, int b) {
            try {
                return a/b;
            }catch (Exception e){
                System.out.println("已捕获异常");
            }
            finally {
                System.out.println("final");
            }
            return 0;
    }
}


