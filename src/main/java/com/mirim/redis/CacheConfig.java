package com.mirim.redis;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;   // Boot 3이면 Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
// "이 앱에서 캐시를 쓰겠다"는 스위치
@EnableCaching
public class CacheConfig {

    private <T> RedisCacheConfiguration jsonFor(Class<T> type, Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .serializeValuesWith(SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(type)));
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration("products", jsonFor(Product.class, Duration.ofSeconds(30)))   // 지난 시간 그대로
                // TODO: [캐시] "notices"를 NoticeService의 캐시 이름과 같게, 60초를 설계 카드의 'TTL'로 바꾸기
                .withCacheConfiguration("notices", jsonFor(Notice.class, Duration.ofSeconds(60)))
                .build();
    }
}
