package com.mirim.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final FakeProductDb db;

    public ProductService(FakeProductDb db) {
        this.db = db;
    }

    //TODO ①  이 메서드 위에 한 줄: "products"라는 이름의 캐시를 쓴다
    public Product find(Long id) {
        return db.findById(id);          // 원본 창고만 부른다 — 레디스 코드가 한 줄도 없다
    }
}
