package com.jiang.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * 一、搭建基本环境
 * 1、导入数据库文件，创建出department和employee表
 * 2、创建javaBean封装数据
 * 3、整合MyBatis操作数据库
 *      1.配置数据源信息
 *      2.使用注解版的MyBatis
 *          1）、@MapperScan指定需要扫描的mapper接口所在的包
 * 二、快速体验缓存
 *      步骤：
 *          1、开启基于注解的缓存
 *          2、标注缓存即可
 *              @Cacheable
 *              @CacheEvict
 *              @CachePut
 *  默认使用的是ConcurrentMapCacheManager==ConcurrentMapCache 将数据保存在ConcurrentMap<Object,Object>
 *  开发中使用缓存中间件，redis，memcached，ehcache
 *  整合redis作为缓存
 *  Redis是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库，缓存和消息中间。
 *  1.安装redis：使用docker
 *  2.引入redis的starter
 *
 */
@MapperScan("com.jiang.springboot.mapper")
@SpringBootApplication
@EnableCaching
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

}
