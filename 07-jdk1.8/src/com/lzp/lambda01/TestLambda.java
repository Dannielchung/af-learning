package com.lzp.lambda01;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @Description: 引入lambda表达式
 * @Author: lzp
 * @Date: 2017/11/12 17:16
 * @Version V1.0
 */
public class TestLambda {

    @Test
    public void test() {

    }

    /**
     * 以Runnable接口为例
     */
    @Test
    public void test01() {
        //之前的写法：
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("之前的写法,start run()...");
            }
        }).start();
        //lambda表达式写法：
        new Thread(() -> System.out.println("lambda表达式,start run()...")).start();
    }

    /**
     * 语法格式一：无参数，无返回值
     * 	() -> System.out.println("Hello Lambda!");
     */
    @Test
    public void test02() {
        //之前的写法：
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("之前的写法,start run()...");
            }
        }).start();
        //lambda表达式写法：
        new Thread(() -> System.out.println("lambda表达式,start run()...")).start();
    }

    /**
     * 语法格式二：有一个参数，并且无返回值
     * 		(s) -> System.out.println(s);
     * 语法格式三：若参数列表只有一个参数时，小括号可以省略不写
     * 		s -> System.out.println(s);
     */
    @Test
    public void test03() {
        Consumer<String> con = (s) -> System.out.println(s);
        con.accept("abcde");
    }

    /**
     * 语法格式四：有两个或多个参数，并且有返回值，并且 Lambda 体中有多条语句
     * 		Comparator<String> com = (x, y) -> {
     *			System.out.println("Lambda 表达式");
     *			return Integer.compare(x.length(), y.length());
     *		};
     * 语法格式五：有两个或多个参数，并且有返回值，Lambda 体中只有一条语句，那么大括号 和 return 都可以省略不写
     *		(x, y) -> Integer.compare(x, y);
     *
     * 语法格式六：Lambda 表达式参数列表的数据类型可以省略不写，因为 JVM 编译器可以通过上下文推断出数据类型，即“类型推断”
     * 		(Long x, Long y) -> Integer.compare(x, y);
     */
    @Test
    public void test04(){
        int i = 0; // jdk8种默认是final修饰
        Comparator<String> com = (x, y) -> {
            System.out.println("Lambda 表达式" + i);
            return Integer.compare(x.length(), y.length());
        };
        com.compare("abc", "ddddd");
    }


}
