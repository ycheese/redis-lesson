package com.mirim.redis;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class FakeNoticeDb {

    private final Map<Long, Notice> table = Map.of(
            1L, new Notice(1L, "midterm-schedule", "teacher-kim"),
            2L, new Notice(2L, "club-fair", "student-council")
    );
    private final AtomicInteger callCount = new AtomicInteger();

    public Notice findById(Long id) {
        callCount.incrementAndGet();
        return table.get(id);
    }

    public int getCallCount() {
        return callCount.get();
    }
}
