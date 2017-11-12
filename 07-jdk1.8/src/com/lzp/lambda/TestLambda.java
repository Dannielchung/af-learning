package com.lzp.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: lzp
 * @Date: 2017/11/12 12:44
 * @Version V1.0
 */
public class TestLambda {

    List<Employee> employees = Arrays.asList(new Employee(1L, "张三", 20),
            new Employee(2L, "李四", 20),
            new Employee(3L, "王五", 15),
            new Employee(4L, "张六", 35),
            new Employee(5L, "田七", 50));

    /**
     * 调用 Collections.sort() 方法，通过定制排序比较两个 Employee (先按年龄比，年龄相同按姓名比)，使用 Lambda 作为参数传递
     */
    @Test
    public void test01() {
        Collections.sort(employees, (x, y) -> {
            if (x.getAge() == y.getAge()) {
                return x.getName().compareTo(y.getName());
            }
            return Integer.compare(x.getAge(), y.getAge());
        });
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    /**
     * ①声明函数式接口，接口中声明抽象方法，public String getValue(String str);
     * ②声明类 TestLambda ，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。
     * ③再将一个字符串的第2个和第4个索引位置进行截取子串
     */
    @Test
    public void test02() {
        System.out.println(strHandler("abc", (e) -> e.toUpperCase()));
        System.out.println(strHandler("abcderfgh", (e) -> e.substring(2, 4)));
    }

    public String strHandler(String str, MyFunciton myFunciton) {
        return myFunciton.getValue(str);
    }

    /**
     * ①声明一个带两个泛型的函数式接口，泛型类型为<T, R>  T 为参数，R 为返回值
     * ②接口中声明对应抽象方法
     * ③在 TestLambda 类中声明方法，使用接口作为参数，计算两个 long 型参数的和。
     * ④再计算两个 long 型参数的乘积。
     */
    @Test
    public void test03() {
        System.out.println(calc(10,20,(x,y) -> x+y));
        System.out.println(calc(10,20,(x,y) -> x*y));
    }

    public Long calc(long l1,long l2,MyCalc<Long,Long> myCalc) {
        return myCalc.getValue(l1,l2);
    }
}
