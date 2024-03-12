package me.jungeun.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.jungeun.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailService userService;

    @Bean
    // 1) 스프링 시큐리티 기능 비활성화 | 스프링 시큐리티의 모든 기능을 사용하지 않도록 설정(인증, 인가 적용x)
    //                               일반적으로 정적 리소스에 설정
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console()) // 리소스와 h2의 데이터를 확인할 때 사용
                .requestMatchers("/static/**");
    }

    @Bean
    // 2) 특정 HTTP 요청에 대한 웹 기반 보안 구성
    //    인증/인가 및 로그인, 로그아웃 관련 설정
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests() // 3) 인증, 인가 설정
                .requestMatchers("/login", "/signup", "/user").permitAll()
                // 특정 요청과 일치하는 url에 대한 액세스 설정 | permitAll(): 누구나 접근 가능하게 설정
                .anyRequest().authenticated()
                // 위에 설정한 url 외의 요청 설정 | authenticated(): 별도의 인가는 필요하지 않지만 인증 접근 가능
                .and()
                .formLogin() // 4) 폼 기반 로그인 설정
                .loginPage("/login") // 로그인 페이지 경로 설정
                .defaultSuccessUrl("/articles")
                .and()
                .logout() // 5) 로그아웃 설정
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할 지 여부 설정
                .and()
                .csrf().disable() // 6) csrf 비활성화 | 원래는 csrf 공격 방지를 위해 활성화(실습이라 비활성화)
                .build();
    }

    @Bean
    // 7) 인증 관리자 관련 설정
    //    사용자 정보를 가져올 서비스를 재정의, 인증 방법을 설정할 때 사용
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService) // 8) 사용자 정보 서비스 설정 | UserDetailsService를 상속받은 클래스여야 함
                .passwordEncoder(bCryptPasswordEncoder) // 비밀번호 암호화를 위한 인코더 설정
                .and()
                .build();
    }

    @Bean
    // 9) 패스워드 인코더로 사용할 빈 등록
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
