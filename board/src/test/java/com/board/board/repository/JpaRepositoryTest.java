package com.board.board.repository;

import com.board.board.config.JpaConfig;
import com.board.board.domain.Article;
import com.board.board.domain.ArticleComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // 반드시 필요한 설정인지 확인
@DataJpaTest

@ActiveProfiles("testdb")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaRepositoryTest {

    private ArticleRepository articleRepository;
    private ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository)
    {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
//    void givenTestData_whenInsert_thenWorksFine() {
//
//        // Given
//        long previousCount = articleRepository.count();
//        Article savedArticle = articleRepository.save(
//                Article.of("new article", "new content", "#spring"));
//        // When
//        List<Article> articles = articleRepository.findAll();
//
//        // Then
//        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
//    }

//    @BeforeEach
//    void setUp() {
//        // 초기화
//        articleCommentRepository.deleteAll();
//        articleRepository.deleteAll();
//
//        // 데이터 추가
//        Article article1 = articleRepository.saveAndFlush(Article.of("title1", "content1", "#spring"));
//        Article article2 = articleRepository.saveAndFlush(Article.of("title2", "content2", "#java"));
//        Article article3 = articleRepository.saveAndFlush(Article.of("title3", "content3", "#jpa"));
//
//        articleCommentRepository.saveAndFlush(ArticleComment.of(article1, "comment1 for article1"));
//        articleCommentRepository.saveAndFlush(ArticleComment.of(article1, "comment2 for article1"));
//        articleCommentRepository.saveAndFlush(ArticleComment.of(article2, "comment1 for article2"));
//        articleCommentRepository.saveAndFlush(ArticleComment.of(article3, "comment1 for article3"));
//    }


    @DisplayName("update 테스트")
    void givenTestData_whenUpdating_thenWorksFine() {

        // Given
        Long id = 1L; // 찾으려는 ID
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다. ID: " + id)); // 예외 메시지를 수정
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @Test
    @DisplayName("delete 테스트")
    void givenTestData_whenDeleting_thenWorksFine() {

        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousCommentCount - deletedCommentsSize);
    }


}

