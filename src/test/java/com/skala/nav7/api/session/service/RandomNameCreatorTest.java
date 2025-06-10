package com.skala.nav7.api.session.service;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomNameCreatorTest {
    private RandomNameCreator randomNameCreator;

    @BeforeEach
    void setUp() {
        this.randomNameCreator = new RandomNameCreator();
    }

    @Test
    void 랜덤으로_세글자_이름을_생성한다() {
        String randomName = randomNameCreator.create();
        System.out.println(randomName);
        Assertions.assertEquals(3, randomName.length());
    }

    @Test
    void 랜덤_이름_10개_생성시_모두_다르다() {
        Set<String> nameSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            String name = randomNameCreator.create();
            Assertions.assertEquals(3, name.length(), "이름 길이는 3글자여야 한다");
            nameSet.add(name);
        }

        Assertions.assertEquals(10, nameSet.size(), "10개의 랜덤 이름이 모두 달라야 한다");
    }
}