package com.jiang.springboot.service;

import com.jiang.springboot.bean.Employee;
import com.jiang.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp") //抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存：以后再要相同的数据，直接从缓存中获取，不用调用方法
     *
     * CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字
     * 几个属性：
     *          cacheNames/value：指定缓存的名字；将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存。
     *          key：缓存数据使用的key：可以用它来指定。默认是使用方法参数的值 1-方法的返回值
     *              编写SpEL：#id;参数id的值 #a0 #p0 #root.args[0]
     *
     *          keyGenerator：key的生成器   getEmp[1]；可以自己指定key的生成器的组件id
     *              keyGenerator/key：二选一使用
     *          cacheManager：指定缓存管理器：或者cacheResolver指定获取解析器
     *          condition：指定符合条件的情况下才缓存
     *                  ，condition="#id>0"
     *                  condition ="#id>1"；第一个参数的值>1的时候才进行缓存
     *          unless：否定缓存：当unless指定的条件为true，方法的返回值就不会被缓存
     *                  unless = "#result == null"
     *          sync：是否使用异步模式 （unless不可用）
     *  原理：
     *      1.自动配置类：
     *      2.缓存的配置类
     *      org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *      3、那个配置类默认生效 SimpleCacheConfiguration
     *
     *      4.给容器中注册了一个CacheManager:ConcurrentMapCacheManager（默认）
     *      5.可以获取和创建ConcurrentMapCache类型的缓存组件，他的作用将数据保存在ConcurrentMap中；
     *
     *      运行流程：
     *      @Cacheable
     *      1.方法运行之前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取；
     *          （CacheManager先获取相对应的缓存），第一次获取缓存如果没有Cache组件会自动创建
     *      2.去Cache中查找缓存的内容，使用一个key，默认就是方法的参数；
     *          key是按照某种策略生成的:默认是使用KeyGenerator生成的，默认使用SimpleKeyGenerator生成Key
     *              SimpleKeyGenerator生成Key的默认策略：
     *                     如果没有参数：key=new SimpleKey();
     *                     如果有一个参数：key=参数的值
     *                     如果有多个参数：key=new SimpleKey(params);
     *      3.没有查到缓存就调用目标方法
     *      4.将目标方法返回的结果，放进缓存中。
     *
     *      @Cacheable 标注的方法执行先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存
     *      如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；
     *
     *      核心
     *          1.使用CacheManager【ConcurrentCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
     *          2.key使用keyGenerator生成的，默认是SimpleKeyGenerator
     * @param id
     * @return
     */
    @Cacheable(cacheNames = {"emp"}/*,keyGenerator = "myKeyGenerator",condition = "#id>1",unless = "#a0==2"*/)
    public Employee getEmp(Integer id){
        System.out.println("查询："+id+"号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return  emp;
    }

    /**
     * @CachePut: 既调用方法，又更新缓存数据；
     * 修改了数据库的某个数据，同时更新缓存；
     * 运行时机：
     *      1.先调用目标方法
     *      2.将目标方法的结果缓存起来
     *      3.更新一号员工{"id":1,"lastName":"zhangsan","email":null,"gender":0,"dId":null}
     *              将方法的返回叶放进缓存了
     *              key：传入的employee对象   值：返回的employee对象
     *      4.查询1号员工
     *          应该是更新后的员工：
     *              key = "#employee.id"：使用传入的参数的员工id
     *              key = "#result.id"：使用返回后的id
     *              @Cacheable 的key是不能用#result.id
     *          为什么是没更新前的？
     */
    @CachePut(value="emp",key = "#employee.id")
    public Employee updateEmp(Employee employee){
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict:缓存清除
     * key：指定要清除的数据
     * allEntries = true 清除所有数据
     * beforeInvocation = false；缓存的清除是否在方法之前执行
     *      默认代表是在方法执行之后执行
     */
    @CacheEvict(value = "emp",key = "#id"/*,allEntries = true,beforeInvocation = false*/)
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp："+id);
    }


    // @Caching 定义复杂的缓存规则
    @Caching(
            cacheable = {
                    @Cacheable(/*value = "emp",*/ key = "#lastName")
            },
            put = {
                    @CachePut(/*value = "emp",*/key = "#result.id"),
                    @CachePut(/*value = "emp",*/key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }
}
