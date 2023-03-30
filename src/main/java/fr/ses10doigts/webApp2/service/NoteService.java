package fr.ses10doigts.webApp2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Note;
import fr.ses10doigts.webApp2.repository.NoteRepository;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public void deleteNote(long id) {
	noteRepository.deleteById(id);
    }

    public Note getNote(long id) {
	return noteRepository.findById(id).orElse(null);
    }
}
