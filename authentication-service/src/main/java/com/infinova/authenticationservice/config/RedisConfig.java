package com.infinova.authenticationservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 * redis配置<br>
 * @author ldw
 */
//@EnableRedisHttpSession
@EnableCaching
@Configuration
public class RedisConfig {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean("redisTemplate")
	public RedisTemplate redisTemplate(@Lazy RedisConnectionFactory connectionFactory) {

		RedisTemplate redis = new RedisTemplate();
		// 可以将任何对象泛化为字符串并序列化
		GenericToStringSerializer<String> keySerializer = new GenericToStringSerializer<String>(String.class);
		// key采用GenericToStringSerializer的序列化方式
		redis.setKeySerializer(keySerializer);
		redis.setHashKeySerializer(keySerializer);
		//序列化object对象为json字符串
		GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
		redis.setValueSerializer(valueSerializer);
		redis.setHashValueSerializer(valueSerializer);

		redis.setConnectionFactory(connectionFactory);

		return redis;
	}

}
