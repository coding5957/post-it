package uk.co.northrealm.postit.services;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.northrealm.postit.model.Note;
import uk.co.northrealm.postit.model.Status;
import uk.co.northrealm.postit.repositories.NoteRepository;

@Service
public class NoteService implements INoteService {
	private final Logger logger = LoggerFactory.getLogger(NoteService.class);	

	private final NoteRepository noteRepository;
	
	@Autowired
	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}
	
	@Override
	@Transactional
	public Note create(Note newNote) {
		
		Date now = new Date();
		newNote.setTimestamp(now);
		
		//Set the note sequence
		Long maxSeq = noteRepository.findMaxSequence().orElse(0L);
		newNote.setSequence(maxSeq + 1);
		
		newNote = noteRepository.save(newNote);
		logger.info("New note created {}", newNote);
		
		return newNote;
	}

	@Override
	@Transactional
	public Note updateTitleAndContent(long id, Note modifiedNote) {

		//Get the note and update the properties
		Note note = getNoteById(id);
		
		note.setTitle(modifiedNote.getTitle());
		note.setContent(modifiedNote.getContent());
		
		note = noteRepository.save(note);
		logger.info("Note updated {}", note);
		
		return note;
	}

	@Override
	@Transactional
	public void delete(long id) {
		
		Note note = getNoteById(id);

		noteRepository.delete(note);
		
		//Renumber the notes to fill the gap created by the delete
		//e.g. 1,2,3,4 -> 1,3,4 - 2 has been deleted, renumber -> 1,2,3
		
		Iterable<Note> notes = noteRepository.findBySequenceGreaterThanOrderBySequenceAsc(note.getSequence());

		//Use AtomicLong so we can update the value from within the lambda 
		AtomicLong seq = new AtomicLong(note.getSequence()); 
		
		notes.forEach((n) -> {
			n.setSequence(seq.getAndAdd(1));
			noteRepository.save(n);
		});
		
		logger.info("Note deleted {}", note);
	}

	@Override
	@Transactional
	public Status updateStatus(long id, Status newStatus) {
		
		Note note = getNoteById(id);
		
		Status prevStatus = note.getStatus();
		note.setStatus(newStatus);

		noteRepository.save(note);
		logger.info("Note status updated to {}", note.getStatus());
		
		return prevStatus;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Note> getAllNotes() {
		return noteRepository.findAllByOrderBySequenceAsc();
	}

	@Override
	@Transactional(readOnly=true)
	public Note getNoteById(long id) {
		logger.info("Looking for note {}", id);
		
		return noteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Invalid Id:" + id));
	}
}
