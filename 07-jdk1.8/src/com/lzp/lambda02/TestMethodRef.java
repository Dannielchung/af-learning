package com.lzp.lambda02;

import com.lzp.lambda.Employee;
import org.junit.Test;

import java.util.function.*;

/**
 * @Description: 方法引用
 * @Author: lzp
 * @Date: 2017/11/12 20:46
 * @Version V1.0
 */
public class TestMethodRef {

    /**
     * 方法引用：若 Lambda 体中的功能，已经有方法提供了实现，我们可以使用方法引用
     * （可以把方法引用理解为 Lambda 表达式的另外一种表现形式）
     *
     * 1. 对象的引用 :: 实例方法名
     * 2. 类名 :: 静态方法名
     * 3. 类名 :: 实例方法名
     *
     * 注意：
     * ①方法引用方法的参数列表与返回值类型，必须与需要实现函数式接口中抽象方法的参数列表与返回值类型保持一致。
     * ②若方法引用的是实例方法时，若 Lambda 的参数列表的第一个参数，是该实例方法的调用者，第二个参数(无参)是实例方法的参数时，ClassName::MethodName
     */
    @Test
    public void test() {

    }

    /**
     * 类名 :: 实例方法名
     * 若方法引用的是实例方法时，若 Lambda 的参数列表的第一个参数，是该实例方法的调用者，第二个参数(无参)是实例方法的参数时，ClassName::MethodName
     */
    @Test
    public void test04() {
        Function<Employee,String> function = (e) -> e.show();
        Function<Employee,String> function2 = Employee::show;
    }

    @Test
    public void test03() {
        BiPredicate<String,String> predicate = (x,y) -> x.equals(y);
        System.out.println(predicate.test("abc","sss"));
        System.out.println("--------------------------------------------");
        BiPredicate<String,String> predicate2 = String::equals;
        System.out.println(predicate2.test("123","123"));
    }

    /**
     * 类名 :: 静态方法名
     */
    @Test
    public void test02() {
        BinaryOperator<Double> operator = (x,y) -> Math.max(x,y);
        System.out.println(operator.apply(15.55D,55.55D));
        System.out.println("--------------------------------------------");
        BinaryOperator<Double> operator2 = Math::max;
        System.out.println(operator2.apply(15.55D,55.55D));
    }

    /**
     * 格式
     * 对象的引用 :: 实例方法名
     */
    @Test
    public void test01() {
        Consumer<String> con = (s) -> System.out.println(s);
        con.accept("sss");

        System.out.println("--------------------------------------------");

        Consumer<String> con2 = System.out::println;
        con2.accept("qqqq");

        System.out.println("--------------------------------------------");
        Employee employee = new Employee();
        Supplier<String> sup = () -> employee.getName();
        System.out.println(sup.get());
        System.out.println("--------------------------------------------");
        Supplier<String> sup2 = employee::getName;
        System.out.println(sup2.get());
    }
}
