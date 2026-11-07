package com.mirim.redis;

import java.io.Serializable;

// Serializable: "Java 전용 방식"으로 저장할 수 있게 붙여 둔 것
public record Product(Long id, String name, int price) implements Serializable {}
