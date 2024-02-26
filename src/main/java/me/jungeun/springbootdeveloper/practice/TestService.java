package me.jungeun.springbootdeveloper.practice;

import me.jungeun.springbootdeveloper.practice.Member;
import me.jungeun.springbootdeveloper.practice.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    MemberRepository memberRepository;

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }
}
