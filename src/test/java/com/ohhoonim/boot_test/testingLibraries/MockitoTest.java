package com.ohhoonim.boot_test.testingLibraries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ohhoonim.boot_test.model.Note;
import com.ohhoonim.boot_test.model.NotePort;
import com.ohhoonim.boot_test.model.NoteService;

// 4. mockito 확장 
@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    // 1. 사전 준비 : 서비스 레이어 
    //    Note.java, NoteService.java

    // 2. Mock객체를 주입할 대상 객체    
    @InjectMocks
    NoteService noteService;

    // 3. 대상 객체에 mock으로 처리할 DI된 객체
    @Mock
    NotePort notePort;

    // 5. start UnitTest
    @Test
    public void noteTest() {
        // 7-3. noteId에대한 테스트코드내 리펙토링
        var uuid = UUID.randomUUID();
        // 7-2. notePort.getNote에 대한 stub 처리
        var mockId = uuid;
        var mockNote = new Note(mockId, "spring", "boot test", true);
        when(notePort.getNote(any(UUID.class))).thenReturn(Optional.of(mockNote));

        // 6. 기본로직 작성
        var emptyIdNote = new Note(null, "spring", "boot test", true);
        var newId = uuid;
        Optional<Note> newNote = noteService.addNote(emptyIdNote, newId);
        // 최초에는 addNote가 void였으나 리턴타입 변경 -> usecase 변경

        assertThat(newNote.get()).isInstanceOf(Note.class);
        // 7-1. notePort.addNote에 대한 mock 처리 (호출횟수 체크)
        verify(notePort, times(1))
                .addNote(any(Note.class), any(UUID.class));

    }
    // mockito를 이용하면 sw테스팅의 통합테스트와 유사해해보이지만
    // TDD는 테스팅 활동이 아니다. model에 대한 설계활동이다. 
    // sw테스팅과 TDD를 구분 하자 

    @Test
    public void getNoteTest() {
        // 할 필요는 없다. 여기서는 그냥 exception 예시용
        var noIdNote = new Note(null, null, null, false);
        assertThatThrownBy(() ->noteService.getNote(noIdNote) )
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("requried");
        // 여기서는 assertj를 활용하였다. (junit에도 유사 기능이 있다)
        // stub에대한 expetion처리는 아래와 같이 사용하면 된다.
        // when(notePort.getNote(any())).thenThrow(new RuntimeException());
    }

}
