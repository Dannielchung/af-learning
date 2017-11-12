package com.lzp.lambda02;

import com.lzp.lambda.Employee;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Description: 构造器引用  具体调用那个构造器取决于函数式接口的参数
 *                  ClassName::new
 * @Author: lzp
 * @Date: 2017/11/12 21:17
 * @Version V1.0
 */
public class TestConstructorRef {

    @Test
    public void test03(){
        BiFunction<String,Long,Employee> function = Employee::new;
    }

    @Test
    public void test02(){
        Function<String,Employee> function = Employee::new;
    }

    @Test
    public void test01(){
        Supplier<Employee> sup = () -> new Employee();

        System.out.println("------------------------------");

        Supplier<Employee> sup2 = Employee::new;
        System.out.println(sup2.get());
    }
}
