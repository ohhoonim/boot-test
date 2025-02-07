package com.ohhoonim.boot_test.testingLibraries;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
// import static org.hamcrest.Matchers.notANumber;
import static com.ohhoonim.boot_test.testingLibraries.IsNotANumber.notANumber;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ohhoonim.boot_test.model.Note;
/**
 * Hamcrest의 매처는 다양한 곳에서 사용되므로 JUnit만큼 필수 테스트 도구이다. 
 */
public class HamcrestTest {

    Note note = null;

    @BeforeEach
    public void setNote() {
        note = new Note(UUID.randomUUID(), "micro economics", "chapter 01", true);
    }

    @Test
    @DisplayName("junit은 예측값을 첫번째 위치시킨다")
    public void basic_junit() {
        assertEquals("micro economics", note.title());
    }

    @Test
    @DisplayName("hamcrest는 matcher를 사용하므로 코드 가독성을 향상시키는데 도움이 된다")
    public void basic_hamcrest() {
        MatcherAssert.assertThat(note.title(), equalTo("micro economics"));
        /**
         * hamcreat의 matcher 를 사용하게 되면 예상되는 값의 위치를 정하는 것이 쉽게된다. 
         * basicJunitTest() 와 비교해보자.
         * MatcherAssert.assertThat 보다는 AssertJ의 assertThat을 많이 사용하게 
         * 될 것이므로 여기서는 신경쓰지말자. 
         */
    }

    @Test
    public void coreMathers() {
        // 테스트코드 작성 전 뭐가 올지 모를때 사용하면 유용하다
        assertThat(note.title(), anything());
    }

    @Test
    public void logicalMatchers() {
        // matcher를 여러개 사용할때, anyOf, not 등이 있다.
        assertThat(note.title(), allOf(
                equalTo("micro economics"),
                hasToString("micro economics")));
    }

    @Test
    public void beanPropertyTest() {
        assertThat(note, not(hasProperty("title")));
        /** 
         * record는 javabaen이 되지 못한다. getter/setter 없음
         * record는 entity보다는 dto 개념으로 이해해야한다.
         */
    }

    @Test
    public void sugar() {
        // 아래 둘은 동일한 기능이다.
        assertThat(note.title(), is("micro economics"));
        assertThat(note.title(), equalTo("micro economics"));
    }

    @Test
    public void customMatcher() {
        // 실패를 해보면 , 
        assertThat(Math.sqrt(-1), is(notANumber()));

        // import static org.hamcrest.Matchers.notANumber;
        // import static com.ohhoonim.boot_test.testingLibraries.IsNotANumber.notANumber;
    }

}
