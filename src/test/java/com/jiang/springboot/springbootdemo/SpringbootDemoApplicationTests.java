package com.jiang.springboot.springbootdemo;

import com.jiang.springboot.bean.Employee;
import com.jiang.springboot.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;            //操作字符串

    @Autowired
    RedisTemplate redisTemplate;                        //k-v都是对象

    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;
    /**
     * String(字符串),List(列表),Set(集合),Hash(散列),ZSet(有序集合)
     * stringRedisTemplate.opsForValue();[String(字符串)]
     * stringRedisTemplate.opsForList();[List(列表)]
     * stringRedisTemplate.opsForSet();[Set(集合)]
     * stringRedisTemplate.opsForZSet();[ZSet(有序集合)]
     * stringRedisTemplate.opsForHash();[Hash(散列)]
     */
    @Test
    public void test01(){
        //给redis中保存数据
        //stringRedisTemplate.opsForValue().append("msg","hello");
        //String msg = stringRedisTemplate.opsForValue().get("msg");
        //System.out.println(msg);

        Employee employee = employeeMapper.getEmpById(1);
        //redisTemplate.opsForValue().set("emp-01",employee);
        //1、将数据以json的方式存储
        //  1.自己将对象转为json
        //  2.redisTemplate默认的序列化规则
        empRedisTemplate.opsForValue().set("emp-01",employee);
    }

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
