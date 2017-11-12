package com.lzp.lambda02;

import org.junit.Test;

import java.util.function.Function;

/**
 * @Description: 数组引用
 * @Author: lzp
 * @Date: 2017/11/12 21:25
 * @Version V1.0
 */
public class TestArrayRef {

    /**
     * type[] :: new;
     */
    @Test
    public void test01() {
        Function<Integer, String[]> fun = (i) -> new String[i];

        Function<Integer, String[]> fun2 = String[]::new;
        String[] strs = fun2.apply(10);
        System.out.println(strs.length);
    }
}
