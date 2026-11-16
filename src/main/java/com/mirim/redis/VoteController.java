package com.mirim.redis;

import java.util.Set;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {

    // TODO: [랭킹] 설계 카드의 "키 이름"으로 바꾸기
    private static final String KEY = "vote:lunch:today";
    private final StringRedisTemplate redisTemplate;

    public VoteController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/vote")
    public String vote(@RequestParam String menu) {
        // ZINCRBY
        Double score = redisTemplate.opsForZSet().incrementScore(KEY, menu, 1);
        // TODO: [랭킹] 설계 카드에서 TTL을 준다고 정했으면 아래 줄의 // 를 지우고, 기간을 카드의 TTL로 바꾸기
        // redisTemplate.expire(KEY, Duration.ofDays(1));
        return menu + " votes=" + score;
    }

    @GetMapping("/vote/top")
    public String top() {
        // ZREVRANGE ... WITHSCORES
        Set<TypedTuple<String>> top = redisTemplate.opsForZSet().reverseRangeWithScores(KEY, 0, 2);
        StringBuilder sb = new StringBuilder();
        int place = 1;
        for (TypedTuple<String> t : top) {
            sb.append(place++).append(". ").append(t.getValue()).append(" (").append(t.getScore()).append(")\n");
        }
        return sb.toString();
    }
}
