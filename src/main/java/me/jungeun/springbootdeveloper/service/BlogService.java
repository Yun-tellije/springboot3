package me.jungeun.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.jungeun.springbootdeveloper.domain.Article;
import me.jungeun.springbootdeveloper.dto.AddArticleRequest;
import me.jungeun.springbootdeveloper.dto.UpdateArticleRequest;
import me.jungeun.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 빈으로 등록
@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가, 빈을 생성자로 생성하는 lombok에서 지원
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

    @Transactional // 매칭한 메서드를 하나의 트랜잭션으로 묶는 역할
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
