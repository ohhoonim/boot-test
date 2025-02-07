package com.ohhoonim.boot_test.model;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

public record Note (
    UUID noteId,
    String title,
    String contents,
    boolean isNew
) implements Serializable{
    public int removeNote(UUID noteId) {
        if (!this.noteId.equals(noteId)) {
            // throw new RuntimeException("노트가 없습니다.");
            throw new NotFoundNoteExeption("노트가 없습니다.");
        }
        return 1;
    }

    public interface Usecase {
        public Optional<Note> addNote(Note emptyIdNote, UUID newNoteId);
        public Optional<Note> getNote(Note hasIdNote);
    }

    public class NotFoundNoteExeption extends RuntimeException{
        public NotFoundNoteExeption(String message) {
            super(message);
        }
    }
}
