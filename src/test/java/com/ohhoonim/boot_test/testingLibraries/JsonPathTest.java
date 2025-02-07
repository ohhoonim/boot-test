package com.ohhoonim.boot_test.testingLibraries;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.jayway.jsonpath.JsonPath;

public class JsonPathTest {
    
    @Test
    public void loadJsonTest() {
        var postsStream = new ClassPathResource("posts.json");
        assertThat(postsStream.getFilename()).isEqualTo("posts.json");
    }

    @Test
    public void parseJsonTest() throws IOException {
        var postsStream = new ClassPathResource("posts.json");
        var docCtx= JsonPath.parse(postsStream.getInputStream());

        List<Post> posts = docCtx.json();
        assertThat(posts.size()).isEqualTo(100); 
    }

    private record Post(
        Integer userId,
        Integer id,
        String title,
        String body
    ) {}

    // 자 이제 본격적으로 JsonPath테스트
    // posts.json, user.json 의 결과가 restapi를 호출한 결과라고 가정해보자. 
    // 작성된 코드보다는 JsonPath의 필터 문법에 집중하자 

    @Test
    @DisplayName("특정 user의 포스트가 있는지 확인")
    public void userIdFilterTest() throws IOException {
        var postsStream = new ClassPathResource("posts.json");
        var docCtx= JsonPath.parse(postsStream.getInputStream());

        List<Post> postsByNine = docCtx.read("$[?(@.userId == 9)]");
        assertThat(postsByNine.size()).isLessThan(11);
    }

   @Test
   public void hasPropertyTest() throws IOException {
        var postsStream = new ClassPathResource("users.json");
        var docCtx= JsonPath.parse(postsStream.getInputStream());

        String bs = docCtx.read("$['company']['bs']");
        assertThat(bs).isNotNull();
        assertThat(bs).contains("real-time");
   } 
}
