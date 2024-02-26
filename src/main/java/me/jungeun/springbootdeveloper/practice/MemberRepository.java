package me.jungeun.springbootdeveloper.practice;

import me.jungeun.springbootdeveloper.practice.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
