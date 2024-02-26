package me.jungeun.springbootdeveloper.practice;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 접근 제어자가 protected인 기본 생성자를 생성
@AllArgsConstructor
@Getter
@Entity // JPA가 관리하는 엔티티로 지정, 실제 DB 테이블과 매핑
public class Member {

    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키의 생성 방식을 결정, 기본키 자동으로 1씩 증가
                                                        // AUTO: DB 방언에 따라 방식을 자동으로 선택(기본값)
                                                        // IDENTITY: 기본 키 생성을 DB에 위임
                                                        // SEQUENCE: DB 시퀀스를 사용해서 기본 키를 할당하는 방법, 오라클에서 주로 사용
                                                        // TABLE: 키 생성 테이블 사용
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false) // DB의 컬럼과 필드를 매핑해줌, name이라는 not null 컬럼과 매핑
                                             // name: 필드와 매핑할 컬럼 이름, default: 필드 이름
                                             // nullable: 컬럼의 null 허용 여부, default: true
                                             // unique: 컬럼의 유일한 값 여부, default: false
                                             // columeDefinition: 컬럼 정보 설정, default 값 줄 수 있음
    private String name;
}
