package com.ohhoonim.boot_test.testingLibraries;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.JsonPath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@WebMvcTest(PostController.class)
public class JsonPathTest {

    @Test
    @DisplayName("resource 폴더에서 파일 읽기")
    public void loadJsonTest() {
        var postsStream = new ClassPathResource("posts.json");
        assertThat(postsStream.getFilename()).isEqualTo("posts.json");
    }

    // posts.json - https://jsonplaceholder.typicode.com/posts
    // users.json - https://jsonplaceholder.typicode.com/users/1
    @Test
    @DisplayName("json 문자열을 파싱하기")
    public void parseJsonTest() throws IOException {
        var postsStream = new ClassPathResource("posts.json");
        var docCtx = JsonPath.parse(postsStream.getInputStream());

        List<Post> posts = docCtx.json();
        assertThat(posts.size()).isEqualTo(100);
    }

    @Test
    @DisplayName("특정 user의 포스트가 있는지 확인")
    public void userIdFilterTest() throws IOException {
        var postsStream = new ClassPathResource("posts.json");
        var docCtx = JsonPath.parse(postsStream.getInputStream());

        List<Post> postsByNine = docCtx.read("$[?(@.userId == 9)]");
        assertThat(postsByNine.size()).isLessThan(11);
    }

    @Test
    @DisplayName("property가 존재하는지 체크")
    public void hasPropertyTest() throws IOException {
        var postsStream = new ClassPathResource("users.json");
        var docCtx = JsonPath.parse(postsStream.getInputStream());

        String bs = docCtx.read("$['company']['bs']");
        assertThat(bs).isNotNull();
        assertThat(bs).contains("real-time");
    }

    @Autowired
    MockMvcTester mockMvc;

    @Test
    @DisplayName("restController test")
    void restController() {

        mockMvc.get().uri("/posts")
                .accept(MediaType.APPLICATION_JSON)
                .assertThat().bodyJson()
                .extractingPath("$.[0].title")
                .isEqualTo("가을아침");

    }

    private record Post(
            Integer userId,
            Integer id,
            String title,
            String body) {
    }

}

@RestController
class PostController {

    @GetMapping("/posts")
    public List<Post> posts() {
        return List.of(
                new Post(1, 1, "가을아침", "아이유"),
                new Post(1, 1, "날개", "태연"),
                new Post(1, 1, "우리의 새벽은 낮보다 뜨겁다", "세븐틴"));
    }

    private record Post(
            Integer userId,
            Integer id,
            String title,
            String body) {
    }

}