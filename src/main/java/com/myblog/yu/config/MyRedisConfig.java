package com.myblog.yu.config;


import com.myblog.yu.bean.CategoryInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.net.UnknownHostException;
import java.time.Duration;

/**
 * 缓存管理器
 * @author 容
 * @version 1.0
 * @date 2020/7/7 15:37
 */
@Configuration
public class MyRedisConfig {
    /**
     * 使用redisTemplate自带的序列化工具序列化employee
     * @param redisConnectionFactory
     * @return

     * @throws UnknownHostException
     */
    @Bean

    public RedisTemplate<Object, CategoryInfo> empRedisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, CategoryInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<CategoryInfo> serializer = new Jackson2JsonRedisSerializer<CategoryInfo>(CategoryInfo.class);
        template.setDefaultSerializer(serializer);
        return template;
    }

//    /**
//     * redis缓存管理器
//     * @param
//     * @return
//     */
//    @Bean //spring boot 1.0版本 ，弊端只能存放一个cache，如果要创造多个缓存也要建立多个
//    public RedisCacheManager employeeCacheManager(RedisTemplate<Object, Employee> empRedisTemplate){
//        RedisCacheManager cacheManager  = new RedisCacheManager();
//        cacheManager.setTransactionAware(true);
//        return cacheManager;
//    }


    /**
     * 注意点：redis数据库有缓存，记得清除后重启，
     * @param redisConnectionFactory
     * @return
     */
    @Primary //将某个缓存管理器作为默认的
    @Bean //spring boot 2.0版本 ，相对于1.0版本不需要多次缓存（多次建立对应缓存的方法），参考上方
    public RedisCacheManager employeeRedisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofDays(1))   // 设置缓存过期时间为一天
                        .disableCachingNullValues()     // 禁用缓存空值，不缓存null校验
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new
                                GenericJackson2JsonRedisSerializer()));     // 设置CacheManager的值序列化方式为json序列化，可加入@Class属性
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration).build();     // 设置默认的cache组件
        //key多了一个前缀
        //使用前缀，默认会将CacheName作为key前缀
    }

}
