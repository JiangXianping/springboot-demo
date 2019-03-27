package com.jiang.springboot.service;

import com.jiang.springboot.bean.Employee;
import com.jiang.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    public Employee getEmp(Integer id){
        System.out.println("查询："+id+"号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return  emp;
    }

}
