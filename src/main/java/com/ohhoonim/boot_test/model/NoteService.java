package com.ohhoonim.boot_test.model;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ohhoonim.boot_test.model.Note.Usecase;

@Service
public class NoteService implements Usecase {

    // hexagonal architecture의 port
    private final NotePort notePort;

    public NoteService(NotePort notePort) {
        this.notePort = notePort;
    }

    @Override
    public Optional<Note> addNote(Note emptyIdNote, UUID newNoteId) {
        if (newNoteId == null) {
            throw new RuntimeException("requried id");
        }
        notePort.addNote(emptyIdNote, newNoteId);
        return notePort.getNote(newNoteId);
        // getNote 메서드도 있지만 repository의 일관성유지를 위해 port를 호출
    }

    @Override
    public Optional<Note> getNote(Note hasIdNote) {
        if (hasIdNote != null && hasIdNote.noteId() == null) {
            throw new RuntimeException("requried id");
        }
        return notePort.getNote(hasIdNote.noteId());
    }

}
