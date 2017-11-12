package com.lzp.lambda02;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
 * 一、Java 8 内置的四大核心函数式接口：
 * 
 * Consumer<T> : 消费型接口
 * 		void accept(T t);
 * 
 * Supplier<T> : 供给型接口
 * 		T get();
 * 
 * Function<T, R> : 函数型接口
 * 		R apply(T t);
 * 
 * Predicate<T> : 断言型接口
 * 		boolean test(T t);
 */
public class TestLambda {
	//Predicate<T> : 断言型接口
	@Test
	public void test4(){
		List<String> strs = Arrays.asList("www", "lzplzp", "cn", "Hello", "Lambda!");
		
		List<String> list = filterStr(strs, (s) -> s.length() > 3);
		
		for (String s : list) {
			System.out.println(s);
		}
	}
	
	public List<String> filterStr(List<String> strs, Predicate<String> pre){
		List<String> list = new ArrayList<>();
		
		for (String str : strs) {
			if(pre.test(str)){
				list.add(str);
			}
		}
		
		return list;
	}
	
	//Function<T, R> : 函数型接口
	@Test
	public void test3(){
		String upper = strHandler("abcde", (s) -> s.toUpperCase());
		System.out.println(upper);
		
		System.out.println("----------------------");
		
		String newStr = strHandler("\t\t\t\tabcde    ", (s) -> s.trim());
		System.out.println(newStr);
	}
	
	public String strHandler(String str, Function<String, String> fun){
		return fun.apply(str);
	}
	
	//Supplier<T> : 供给型接口
	@Test
	public void test2(){
		List<Integer> list = getNumber(10, () -> (int)(Math.random() * 101));
		
		for (Integer num : list) {
			System.out.println(num);
		}
	}
	
	public List<Integer> getNumber(int n, Supplier<Integer> sup){
		List<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < n; i++) {
			list.add(sup.get());
		}
		
		return list;
	}

	//Consumer<T> : 消费型接口
	@Test
	public void test1(){
		happy(10000, (l) -> System.out.println("你们喜欢大宝剑，每次消费 " + l + " 元"));
	}
	
	public void happy(long money, Consumer<Long> con){
		con.accept(money);
	}
	
}
