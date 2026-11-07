package com.mirim.redis;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

// 오늘의 원본 창고 (MySQL 대신). 느리게 만들지 않는다. 몇 번 불렸는지만 정확히 센다.
@Component
public class FakeProductDb {

    private final Map<Long, Product> table = Map.of(
            501L, new Product(501L, "wireless-keyboard", 45000),
            502L, new Product(502L, "wireless-mouse", 29000)
    );

    private final AtomicInteger callCount = new AtomicInteger();

    public Product findById(Long id) {
        callCount.incrementAndGet();
        return table.get(id);
    }

    public int getCallCount() {
        return callCount.get();
    }
}
