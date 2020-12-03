package uk.co.northrealm.postit.services;

import uk.co.northrealm.postit.model.Note;
import uk.co.northrealm.postit.model.Status;

public interface INoteService {

	
	/**
	 * Create a new note
	 * @param newNote
	 * @return the newly created note
	 */
	Note create(Note newNote);
	
	
	/**
	 * Update the title and content of an existing note
	 * @param id
	 * @param modifiedNote
	 * @return the updated note
	 * @throws EntityNotFoundException 
	 */
	Note updateTitleAndContent(long id, Note modifiedNote);
	

	/**
	 * Delete a note
	 * @param id
	 * @throws EntityNotFoundException 
	 */
	void delete(long id);

	/**
	 * Update note status
	 * @param id
	 * @param newStatus
	 * @return previous status
	 * @throws EntityNotFoundException 
	 */
	Status updateStatus(long id, Status newStatus);
	
	
	/**
	 * Get all notes
	 * @return notes
	 */
	Iterable<Note> getAllNotes();
	
	/**
	 * Get the note of the given id
	 * @param id
	 * @return note
	 * @throws EntityNotFoundException 
	 */
	Note getNoteById(long id);
}
