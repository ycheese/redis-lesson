package com.mirim.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final FakeProductDb db;

    public ProductService(FakeProductDb db) {
        this.db = db;
    }

    @Cacheable("products")
    public Product find(Long id) {
        return db.findById(id);          // 원본 창고만 부른다 — 레디스 코드가 한 줄도 없다
    }
}
