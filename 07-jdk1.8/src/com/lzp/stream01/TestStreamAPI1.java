package com.lzp.stream01;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/*
 * 一、Stream API 操作的三个步骤
 * 
 * 1. 创建 Stream
 * 
 * 2. 中间操作
 * 
 * 3. 终止操作(终端操作)
 */
public class TestStreamAPI1 {

	//1. 创建 Stream
	@Test
	public void test1(){
		//①通过 Collection 系列集合的 stream() 或  parallelStream() 
		List<String> list = new ArrayList<>();
		Stream<String> stream1 = list.stream();
		
		//②通过 Arrays 工具类的静态方法 stream()
		Integer[] nums = new Integer[10];
		Stream<Integer> stream2 = Arrays.stream(nums);
		
		//③通过 Stream 的静态方法 of() 
		Stream stream3 = Stream.of(1,2,3,4,5,6);
		
		//④获取无限流
		//迭代
		Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
		stream4.limit(10).forEach(System.out::println);
		
		//生成
		Stream.generate(Math::random)
			  .limit(5)
			  .forEach(System.out::println);
	}
	
}
