package com.skala.nav7.api.session.service;

import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RandomNameCreator {

    private static final List<String> LAST_NAMES = List.of(
            "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안", "송", "류", "홍", "노",
            "양", "백", "문", "전", "허", "남", "심", "구", "하", "진", "곽", "성", "차", "주", "우", "구", "나", "민", "유", "나", "백"
    );

    private static final List<String> FIRST_NAME_PART1 = List.of(
            "가", "나", "다", "라", "마", "바", "사", "하", "윤", "수", "은", "채", "예", "다", "준", "성", "연", "영", "주", "재", "현",
            "유", "아", "혜", "지", "도", "태", "승", "소", "지", "선", "시", "지", "슬", "진", "명", "동", "형"
    );

    private static final List<String> FIRST_NAME_PART2 = List.of(
            "은", "진", "우", "빈", "정", "현", "희", "림", "영", "연", "훈", "석", "찬", "혁", "결", "율", "서", "나", "예", "솔",
            "환", "겸", "경", "진", "재", "온", "해", "담", "민", "범", "범", "슬", "우", "재", "정", "혁", "하", "영"
    );

    private final Random random = new Random();

    public String create() {
        StringBuilder name = new StringBuilder();
        String lastName = LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));
        String firstName1 = FIRST_NAME_PART1.get(random.nextInt(FIRST_NAME_PART1.size()));
        String firstName2 = FIRST_NAME_PART2.get(random.nextInt(FIRST_NAME_PART2.size()));
        return name.append(lastName).append(firstName1).append(firstName2).toString();
    }
}