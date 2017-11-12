package com.lzp.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: lzp
 * @Date: 2017/11/12 12:46
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    private String name;
    private Integer age;

    public Employee(String name,long id) {
    }

    public Employee(String name) {
    }

    public String show(){
        return "呵呵";
    }
}
