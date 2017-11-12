package com.lzp.lambda;

/**
 * @Description:
 * @Author: lzp
 * @Date: 2017/11/12 13:01
 * @Version V1.0
 */
public interface MyCalc<T,R> {
    public R getValue(T t1,T t2);
}
