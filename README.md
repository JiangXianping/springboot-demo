# springboot-demo
 keyGenerator：key的生成器   getEmp[1]；可以自己指定key的生成器的组件id<br/>
     keyGenerator/key：二选一使用<br/>
 cacheManager：指定缓存管理器：或者cacheResolver指定获取解析器<br/>
condition：指定符合条件的情况下才缓存<br/>
          ，condition="#id>0"<br/>
           condition ="#id>1"；第一个参数的值>1的时候才进行缓存<br/>
unless：否定缓存：当unless指定的条件为true，方法的返回值就不会被缓存<br/>
unless = "#result == null"<br/>
sync：是否使用异步模式 （unless不可用）<br/>
原理：<br/>
            1.自动配置类：<br/>
            2.缓存的配置类<br/>
            org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
            org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
            org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
            org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
            org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
            org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
            org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
            org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
            org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
            org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
            org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
            3、那个配置类默认生效 SimpleCacheConfiguration<br/>
            4.给容器中注册了一个CacheManager:ConcurrentMapCacheManager（默认）<br/>
            5.可以获取和创建ConcurrentMapCache类型的缓存组件，他的作用将数据保存在ConcurrentMap中；<br/>
            运行流程：
            @Cacheable
            1.方法运行之前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取；
                （CacheManager先获取相对应的缓存），第一次获取缓存如果没有Cache组件会自动创建
            2.去Cache中查找缓存的内容，使用一个key，默认就是方法的参数；
                key是按照某种策略生成的:默认是使用KeyGenerator生成的，默认使用SimpleKeyGenerator生成Key
                    SimpleKeyGenerator生成Key的默认策略：
                           如果没有参数：key=new SimpleKey();
                           如果有一个参数：key=参数的值
                           如果有多个参数：key=new SimpleKey(params);
            3.没有查到缓存就调用目标方法
            4.将目标方法返回的结果，放进缓存中。
            @Cacheable 标注的方法执行先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存
            如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；
      
            核心
                1.使用CacheManager【ConcurrentCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
                2.key使用keyGenerator生成的，默认是SimpleKeyGenerator
     
     @CachePut(value="emp",key = "#employee.id")
     /  
       @CachePut: 既调用方法，又更新缓存数据；
       修改了数据库的某个数据，同时更新缓存；
       运行时机：
            1.先调用目标方法
            2.将目标方法的结果缓存起来
            3.更新一号员工{"id":1,"lastName":"zhangsan","email":null,"gender":0,"dId":null}
                    将方法的返回叶放进缓存了
                    key：传入的employee对象   值：返回的employee对象
            4.查询1号员工
                应该是更新后的员工：
                    key = "#employee.id"：使用传入的参数的员工id
                    key = "#result.id"：使用返回后的id
                    @Cacheable 的key是不能用#result.id
                为什么是没更新前的？
      
