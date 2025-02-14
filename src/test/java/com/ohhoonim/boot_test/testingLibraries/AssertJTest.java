package com.ohhoonim.boot_test.testingLibraries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ohhoonim.boot_test.model.Note;

public class AssertJTest {

    // hamcrest vs assertJ 
    @Test
    @DisplayName("matcher를 사용한 경우")
    public void usingMatcher() {
        assertThat("foo", is(equalTo("foo")));
    }

    @Test
    @DisplayName("AssertJ의 fluent를 활용한 경우")
    public void basicAssertJ() {
        assertThat("foo").isEqualTo("foo");
    }

    // BDD 스타일 given/when/then
    @Test
    @DisplayName("BDD 스타일의 then")
    public void bddStyleEntry() {
        // assertThat을 then으로 변경해주면 됨
        // import static org.assertj.core.api.BDDAssertions.then;
        // BDD스타일 : given/when/then 형식으로 테스트코드를 작성
        then("foo").isEqualTo("foo");
    }

    // assertJ 체이닝 사용법 
    @Test
    public void noteTest() {
        var note = new Note(UUID.randomUUID(), 
            "economics", 
            "micro economic", 
            false);
        assertThat(note.title()).isNotNull().contains("mics");
        assertThat(note).isInstanceOf(Note.class);
    }

    // assert descriptoin (테스트에 대할 설명. 필요하지 않을 수도 있지만)
    @Test
    public void assertDescription() {
        var note = new Note(UUID.randomUUID(), 
            "economics", 
            "micro economic", 
            false);
        // fail 시켜보면
        assertThat(note).as("check instance : %s", note.title())
            // .isInstanceOf(String.class);
            .isInstanceOf(Note.class);
        // 사용시 주의 사항 as를 제일 먼저 사용할 것 
        // overridingErrorMessaage, withFailMessage 사용시도 동일
    }

    // exception 테스트 방법
    @Test
    public void exceptionAssertion() {
        var noteId = UUID.randomUUID();

        var note = new Note(noteId, 
            "economics", 
            "micro economic", 
            false);

        
        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() -> note.removeNote(UUID.randomUUID()));

        assertThatThrownBy(() -> note.removeNote(UUID.randomUUID()))
            .isInstanceOf(RuntimeException.class);

        assertThatNoException().isThrownBy(() -> note.removeNote(noteId));
        
    }

}
