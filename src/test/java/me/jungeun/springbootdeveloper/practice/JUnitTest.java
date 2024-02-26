package me.jungeun.springbootdeveloper.practice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {

    @DisplayName("1 + 2는 3이다") // 테스트 이름을 명시
    @Test // 테스트를 수행하는 메서드임을 알림
    public void junitTest(){
        // JUnit은 테스트끼리 영향을 주지 않도록 각 테스트를 실행할 때마다 테스르릍 위한 실행 객체를 만들고 테스트가 종료되면 실행 객체를 삭제
        int a = 1;
        int b = 2;
        int sum = 3;

        Assertions.assertEquals(a + b, sum); // 값이 같은지 확인
                                                      // 첫번째 인수: 기대하는 값, 두번째 인수: 실제로 검증할 값
    }


}
