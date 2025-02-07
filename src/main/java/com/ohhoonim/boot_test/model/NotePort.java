package com.ohhoonim.boot_test.model;

import java.util.Optional;
import java.util.UUID;

public interface NotePort {

    void addNote(Note emptyIdNote, UUID newNoteId);

    Optional<Note> getNote(UUID newNoteId);

}
