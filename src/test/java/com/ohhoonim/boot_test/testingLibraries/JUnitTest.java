package com.ohhoonim.boot_test.testingLibraries;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ohhoonim.boot_test.model.Note;
import com.ohhoonim.boot_test.model.Note.NotFoundNoteExeption;


/**
 * 많이 사용하는 기능들 위주로 테스트코드를 작성해두었습니다. 
 * 더 많은 내용은 아래 URL을 참고하세요
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests
 */

@DisplayNameGeneration(ReplaceUnderscores.class)
public class JUnitTest {


    @Test
    public void basicTest() {
        var note = new Note(UUID.randomUUID(), "micro economics", "chapter 01", true);
        assertEquals("micro economics", note.title());
        assertTrue(() -> note.isNew());
    }

    @Test
    @DisplayName("테스트 실패시 적절한 메시지 보여주기, 성공하면 메세지 안나옴 ")
    public void messageSupplierTest() {
        var note = new Note(UUID.randomUUID(), "micro economics", "chapter 01", true);
        assertTrue(note.isNew(), errorMessage.apply(Code.NotNewBook));
    }

    private final Function<Code, Supplier<String>> errorMessage = code -> {
        return () -> code + " : " + code.getMessage();
    };

    enum Code {
        NotNewBook("새로운 노트가 아닙니다"), NotFound("노트가 없습니다.");

        private final String message;

        private Code(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @Test
    @DisplayName("throw Exception")
    public void exceptionTest() {
        var note = new Note(UUID.randomUUID(), "micro economics", "chapter 01", true);
        assertThrows(NotFoundNoteExeption.class,
                () -> note.removeNote(UUID.randomUUID()));
    }

    Logger log = LoggerFactory.getLogger(JUnitTest.class);

    @DisplayName("각 테스트 실행전 사전준비")
    @BeforeEach
    public void beforeEachTest() {
        // 디버그 콘솔을 보세요.
        log.info("before test");

        // @BeforeAll, @BeforeEach
        // @AfterAll, @AfterEach
    }

    @Test
    public void displayNameGeneratorTest() {
        // @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        // class에만 사용가능한 어노테이션입니다.
    }

    @ParameterizedTest(name = "For example, year {0} is not supported.")
    @ValueSource(ints = { -1, -4 })
    public void parameterizedValueSourceTest(int year) {
        // run test...
    }

    @Test
    public void groupedTest() {
        var note = new Note(UUID.randomUUID(), "micro economics", "chapter 01", true);
        assertAll("note content check",
            () -> assertEquals("micro economics", note.title()),
            () -> assertEquals("chapter 01", note.contents())
        );
    }
}
