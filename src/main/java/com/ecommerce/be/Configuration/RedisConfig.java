package com.ecommerce.be.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class RedisConfig {

  // @Bean
  // StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
  //   StringRedisTemplate template = new StringRedisTemplate();
  //   template.setConnectionFactory(connectionFactory);
  //   return template;
  // }

  @Bean
  RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    return template;
  }
}