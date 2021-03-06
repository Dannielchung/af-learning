package com.lzp.stream01;

import com.lzp.lambda02.Employee;
import com.lzp.lambda02.Employee.Status;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * 3. 终止操作
 */
public class TestStreamAPI3 {
	
	List<Employee> employees = Arrays.asList(
			new Employee("张三", 18, 9999.99, Status.BUSY),
			new Employee("李四", 38, 5555.99, Status.FREE),
			new Employee("王五", 50, 6666.66, Status.VOCATION),
			new Employee("赵六", 16, 3333.33, Status.BUSY),
			new Employee("田七", 8, 7777.77, Status.FREE),
			new Employee("田七", 8, 7777.77, Status.FREE),
			new Employee("田七", 8, 7777.77, Status.FREE)
	);

	/*
	 	收集
		collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
	 */
	@Test
	public void test12(){
		Optional<Double> op = employees.stream()
				 .map(Employee::getSalary)
				 .collect(Collectors.reducing(Double::sum));

		System.out.println(op.get());
	}

	@Test
	public void test11(){
		String str = employees.stream()
				 .map(Employee::getName)
				 .collect(Collectors.joining(",", "---", "---"));
		System.out.println(str);
	}



	@Test
	public void test10(){
		DoubleSummaryStatistics dss = employees.stream()
				 .collect(Collectors.summarizingDouble(Employee::getSalary));

		System.out.println(dss.getCount());
		System.out.println(dss.getMax());
		System.out.println(dss.getMin());
	}

	//分区
	@Test
	public void test9(){
		Map<Boolean, List<Employee>> map = employees.stream()
				 .collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
		System.out.println(map);
	}



	//多级分组
	@Test
	public void test8(){
		Map<Status, Map<String, List<Employee>>> map = employees.stream()
				 .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
					 if(((Employee) e).getAge() <= 35){
						 return "青年";
					 }else if(((Employee) e).getAge() <= 60){
						 return "中年";
					 }else{
						 return "老年";
					 }
				 })));

		System.out.println(map);
	}



	//分组
	@Test
	public void test7(){
		Map<Status, List<Employee>> map = employees.stream()
				 .collect(Collectors.groupingBy(Employee::getStatus));

		System.out.println(map);
	}



	@Test
	public void test6(){
		//总数
		long count = employees.stream()
				 .collect(Collectors.counting());
		System.out.println(count);

		//总和
		Double sum = employees.stream()
				 .collect(Collectors.summingDouble(Employee::getSalary));
		System.out.println(sum);

		//平均值
		Double avg = employees.stream()
				 .collect(Collectors.averagingDouble(Employee::getSalary));
		System.out.println(avg);

		//最大值
		Optional<Employee> max = employees.stream()
				 .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
		System.out.println(max.get());

		//最小值
		Optional<Double> min = employees.stream()
				 .map(Employee::getSalary)
				 .collect(Collectors.minBy(Double::compare));
		System.out.println(min.get());
	}



	@Test
	public void test5(){
		List<String> list = employees.stream()
				 .map(Employee::getName)
				 .collect(Collectors.toList());

		list.forEach(System.out::println);

		System.out.println("----------------------------");

		Set<String> set = employees.stream()
				 .map(Employee::getName)
				 .collect(Collectors.toSet());

		set.forEach(System.out::println);

		System.out.println("----------------------------");

		HashSet<String> hs = employees.stream()
				 .map(Employee::getName)
				 .collect(Collectors.toCollection(HashSet::new));

		hs.forEach(System.out::println);
	}

	/*
		归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
	public static Stream<Character> filterCharacter(String str){
		List<Character> list = new ArrayList<>();

		for (Character ch : str.toCharArray()) {
			list.add(ch);
		}

		return list.stream();
	}

	//需求：获取职员名字中“七”出现的次数
	@Test
	public void test4(){
		Optional<Integer> sum = employees.stream()
				 .map(Employee::getName)
				 .flatMap(TestStreamAPI3::filterCharacter)
				 .map((ch) -> {
					 if(ch.equals('七')){
						 return 1;
					 }else{
						 return 0;
					 }
				 }).reduce(Integer::sum);

		System.out.println(sum.get());
	}

	@Test
	public void test3(){
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

		Integer sum = list.stream()
			.reduce(0, (x, y) -> x + y);
		System.out.println(sum);

		System.out.println("----------------------------");

		Optional<Double> op = employees.stream()
				 .map(Employee::getSalary)
				 .reduce(Double::sum);
		System.out.println(op.get());

	}

	/*
	 	查找与匹配
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配所有元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */
	@Test
	public void test2(){
		long count = employees.stream()
			 	 .count();
		System.out.println(count);

		Optional<Employee> max = employees.stream()
				 .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
		System.out.println(max.get());

		Optional<Double> min = employees.stream()
				 .map(Employee::getSalary)
				 .min(Double::compare);
		System.out.println(min.get());
	}

	@Test
	public void test1(){
		boolean b1 = employees.stream()
				.allMatch((e) -> e.getStatus().equals(Status.BUSY));
		System.out.println(b1);

		boolean b2 = employees.stream()
				 .anyMatch((e) -> e.getStatus().equals(Status.BUSY));
		System.out.println(b2);

		boolean b3 = employees.stream()
				 .noneMatch((e) -> e.getStatus().equals(Status.BUSY));
		System.out.println(b3);

		Optional<Employee> op = employees.stream()
				 .sorted((e1, e2) -> -Double.compare(e1.getSalary(), e2.getSalary()))
				 .findFirst();
		Employee emp = op.get();
		System.out.println(emp);

		System.out.println("---------------------");

		Optional<Employee> op2 = employees.parallelStream()
				 .filter((e) -> e.getStatus().equals(Status.FREE))
				 .findAny();
		System.out.println(op2.get());
	}

}
