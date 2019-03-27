package com.jiang.springboot.service;

import com.jiang.springboot.bean.Employee;
import com.jiang.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存：以后再要相同的数据，直接从缓存中获取，不用调用方法
     *
     * CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字
     * 几个属性：
     *          cacheNames/value：指定缓存的名字
     *          key：缓存数据使用的key：可以用它来指定。默认是使用方法参数的值 1-方法的返回值
     *              编写SpEL：#id;参数id的值 #a0 #p0 #root.args[0]
     *          keyGenerator：key的生成器；可以自己指定key的生成器的组件id
     *              keyGenerator/key：二选一使用
     *          cacheManager：指定缓存管理器：或者cacheResolver指定获取解析器
     *          condition：指定符合条件的情况下才缓存
     *          unless：否定缓存：当unless指定的条件为true，方法的返回值就不会被缓存
     *                  unless = "#result == null"
     *          sync：是否使用异步模式
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = {"emp"})
    public Employee getEmp(Integer id){
        System.out.println("查询："+id+"号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return  emp;
    }

}
