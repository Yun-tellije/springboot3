package me.jungeun.springbootdeveloper.practice;

import me.jungeun.springbootdeveloper.practice.Member;
import me.jungeun.springbootdeveloper.practice.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트 생성 | @SpringBootApplication이 있는 클래스를 찾고 그 클래스에 포함된 빈 찾음
                // 찾은 후, 테스트용 애플리케이션 컨텍스트를 만듦
@AutoConfigureMockMvc // MockMvc을 자동 생성, 구성 | 컨트롤러를 테스트할 때 사용되는 클래스
                      // MockMvc: 애플리케이션을 서버에 배포하지 않고 테스트용 MVC 환경을 만들어 요청 및 전송, 응답 기능을 제공하는 유틸리티 클래스
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void cleanUp(){
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception{
        // given: 테스트 준비
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));

        // when: 테스트를 실제로 진행
        final ResultActions result = mockMvc.perform(get(url) // perform() -> 요청을 전송하는 역할
                                                              // ResultActions 객체 -> andExpect() 메서드 제공(반환값 검증하고 확인)
                .accept(MediaType.APPLICATION_JSON)); // accept() -> 요청을 보낼 때 무슨 타입으로 응답을 받을지 결정하는 메서드

        // then: 테스트 결과를 검증
        result
                .andExpect(status().isOk()) // andExpect() -> 응답을 검증 ex) isOk()를 사용해 응답 코드가 OK(200)인지 확인

                // 응답의 0번째 값이 DB에서 저장한 값과 같은지 확인
                // jsonPath("$[0].${필드명}") -> JSON의 응답값을 가져오는 역할을 하는 메서드 | id, name을 가져와 저장된 값과 같은지 확인
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}