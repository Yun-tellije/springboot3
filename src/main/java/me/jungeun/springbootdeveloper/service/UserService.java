package me.jungeun.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jungeun.springbootdeveloper.domain.User;
import me.jungeun.springbootdeveloper.dto.AddUserRequest;
import me.jungeun.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long save(AddUserRequest dto){
        // 직접 생성하여 패스워드를 암호화할 수 있게 수정
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                // 1) 패스워드 암호화 | 시큐리티를 설정하며 패스워드 인코딩용으로 등록한 빈을 사용해서 암호화한 후 저장
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) { // 이메일을 입력받아 users 테이블에서 유저를 찾고 없으면 예외 발생
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
