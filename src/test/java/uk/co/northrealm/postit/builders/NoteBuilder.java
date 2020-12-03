package uk.co.northrealm.postit.builders;

import java.util.Date;

import uk.co.northrealm.postit.model.Note;
import uk.co.northrealm.postit.model.Status;

public class NoteBuilder {
	
	private Note note = new Note();

	
    private NoteBuilder() {
    	//prevent direct construction
    }
    
	public NoteBuilder id(long id) {
		note.setId(id);
		return this;
	}

	public NoteBuilder sequence(long sequence) {
		note.setSequence(sequence);
		return this;
	}

	public NoteBuilder timestamp(Date timestamp) {
		note.setTimestamp(timestamp);
		return this;
	}

	public NoteBuilder title(String title) {
		note.setTitle(title);
		return this;
	}

	public NoteBuilder content(String content) {
		note.setContent(content);
		return this;
	}

	public NoteBuilder status(Status status) {
		note.setStatus(status);
		return this;
	}
	
	public Note build() {
		return note;
	}

	public static NoteBuilder builder() {
		return new NoteBuilder();
	}
}
