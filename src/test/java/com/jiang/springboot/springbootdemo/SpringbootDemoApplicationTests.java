package com.jiang.springboot.springbootdemo;

import com.jiang.springboot.bean.Employee;
import com.jiang.springboot.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void contextLoads() {
        Employee employee = employeeMapper.getEmpById(1);
        System.out.println(employee.getId());

    }

    @Test
    public void insertEmp(){
        Employee employee = new Employee();
        employee.setGender(0);
        employee.setEmail("898989@qq.com");
        employee.setLastName("lisi");
        employee.setdId(2);
        employeeMapper.insertEmployee(employee);
    }

}
