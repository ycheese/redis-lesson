package com.mirim.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    private final FakeNoticeDb db;

    public NoticeService(FakeNoticeDb db) {
        this.db = db;
    }

    // TODO: [캐시] 설계 카드의 '캐시 이름'으로 바꾸기 — CacheConfig의 TODO 이름과 한 글자까지 같게
    @Cacheable("notices")
    public Notice find(Long id) {
        return db.findById(id);
    }
}
