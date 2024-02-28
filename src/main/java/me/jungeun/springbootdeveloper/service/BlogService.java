package me.jungeun.springbootdeveloper.service;


import lombok.RequiredArgsConstructor;
import me.jungeun.springbootdeveloper.domain.Article;
import me.jungeun.springbootdeveloper.dto.AddArticleRequest;
import me.jungeun.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가, 빈을 생성자로 생성하는 lombok에서 지원
@Service // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity()); // save()로 저장된 값들을 article DB에 저장
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }
}
