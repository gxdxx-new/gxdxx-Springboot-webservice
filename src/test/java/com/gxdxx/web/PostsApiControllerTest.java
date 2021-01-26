package com.gxdxx.web;

import com.gxdxx.domain.posts.Posts;
import com.gxdxx.domain.posts.PostsRepository;
import com.gxdxx.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@WebMvcTest : JPA기능이 작동하지 않아서 Controller와 ControllerAdvice등 외부 연동과 관련된 부분만 활성화됨
//JPA기능까지 한번에 테스트할 때는 @SpringBootTest와 TestRestTemplate을 사용하면 됨
@RunWith(SpringRunner.class)    // 스프링부트 테스트와 Junit을 연결
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 랜덤 포트 실행
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;  // servlet container를 사용해서 실제 서버가 동작하는것처럼 테스트 수행. Mock mvc는 servlet container생성하지 않음.

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        //responseEntity : HTTP에서 응답에 대응하는 클래스, 상태코드에 따른 응답을 편하게 작성할수 있도록 도와줌
        //postForEntity : HTTP Post 메소드를 편리하게 쓸 수 있도록 제공하는 빌트인 메서드
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }
}