package com.mirim.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 실습 편의상 저장도 GET으로 합니다 (curl로 바로 호출하려고)
@RestController
public class BasicController {

    private final StringRedisTemplate redisTemplate;

    public BasicController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);              // → SET key value
        return "saved";
    }

    @GetMapping("/find")
    public String find(@RequestParam String key) {
        String value = redisTemplate.opsForValue().get(key);      // → GET key
        return value == null ? "(nil)" : value;
    }
}
