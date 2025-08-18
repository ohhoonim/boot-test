package com.ohhoonim.boot_test.model;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class NoteAdaptor implements NotePort {

    @Override
    public void addNote(Note emptyIdNote, UUID newNoteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addNote'");
    }

    @Override
    public Optional<Note> getNote(UUID newNoteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNote'");
    }
    
}
