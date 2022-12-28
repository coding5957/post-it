package uk.co.northrealm.postit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.co.northrealm.postit.builders.NoteBuilder;
import uk.co.northrealm.postit.model.Note;
import uk.co.northrealm.postit.model.Status;
import uk.co.northrealm.postit.repositories.NoteRepository;

class NoteServiceUnitTest {

	private INoteService noteService;
    private NoteRepository mockedNoteRepository;

    @BeforeEach
    public void setUp() {
        mockedNoteRepository = mock(NoteRepository.class);
        noteService = new NoteService(mockedNoteRepository);
    }
	
    @Test
    public void testCreateFirstNewNote() {
    	Note n = NoteBuilder.builder().title("title1").content("content1").build();
    	
		when(mockedNoteRepository.findMaxSequence()).thenReturn(Optional.empty());
		when(mockedNoteRepository.save(n)).thenReturn(n);
		
    	n = noteService.create(n);
    	
    	//no existing notes so this should be sequence 1
    	assertEquals(1, n.getSequence());
    	
    	assertNotNull(n.getTimestamp());
    	assertEquals(Status.ACTIVE, n.getStatus());
    	assertEquals("title1", n.getTitle());
    	assertEquals("content1", n.getContent());
    	
		verify(mockedNoteRepository, atMost(1)).findMaxSequence();
    }

    @Test
    public void testCreateNewNote() {
    	Note n = NoteBuilder.builder().title("title1").content("content1").build();
    	
		when(mockedNoteRepository.findMaxSequence()).thenReturn(Optional.of(10L));
		when(mockedNoteRepository.save(n)).thenReturn(n);
		
    	n = noteService.create(n);
    	
    	//max sequence is 10 so this note should be 11
    	assertEquals(11, n.getSequence());
    	
    	assertNotNull(n.getTimestamp());
    	assertEquals(Status.ACTIVE, n.getStatus());
    	assertEquals("title1", n.getTitle());
    	assertEquals("content1", n.getContent());
    	
		verify(mockedNoteRepository, atMost(1)).findMaxSequence();
    }

    @Test
    public void testUpdateNote() {
    	Note dbNote = NoteBuilder.builder().timestamp(new Date()).title("title1").content("content1").
    						id(100L).sequence(1).build();
    	
    	Note modifiedNote = NoteBuilder.builder().title("modifiedtitle1").content("modifiedcontent1").build();
    	
		when(mockedNoteRepository.findById(100L)).thenReturn(Optional.of(dbNote));
		when(mockedNoteRepository.save(dbNote)).thenReturn(dbNote);
		
    	Note n = noteService.updateTitleAndContent(100L, modifiedNote);
    	
    	assertEquals(1, n.getSequence());
    	assertNotNull(n.getTimestamp());
    	assertEquals(Status.ACTIVE, n.getStatus());
    	assertEquals("modifiedtitle1", n.getTitle());
    	assertEquals("modifiedcontent1", n.getContent());
    	
		verify(mockedNoteRepository, atMost(1)).findById(100L);
    }

    @Test
    public void testUpdateNonExistentNote() {
    	Note n = NoteBuilder.builder().title("modifiedtitle1").content("modifiedcontent1").build();
    	
		when(mockedNoteRepository.findById(100L)).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> {
	    	noteService.updateTitleAndContent(100L, n);
	    });
		
		verify(mockedNoteRepository, atMost(1)).findById(100L);
    }
    
    @Test
    public void testDeleteNote() {
    	Note dbNote1 = NoteBuilder.builder().title("title1").content("content1").
    						id(100L).sequence(1).build();
    	
    	Note dbNote2 = NoteBuilder.builder().title("title2").content("content2").
    						id(101L).sequence(2).build();

    	Note dbNote3 = NoteBuilder.builder().title("title3").content("content3").
    						id(102L).sequence(3).build();

		when(mockedNoteRepository.findById(100L)).thenReturn(Optional.of(dbNote1));
    	
    	List<Note> i = Arrays.asList(dbNote2, dbNote3);
		when(mockedNoteRepository.findBySequenceGreaterThanOrderBySequenceAsc(1L)).thenReturn(i);

		// notes -> 1,2,3
		// delete 1 -> 2,3
		// renumber -> 1,2
		noteService.delete(100L);
		
    	assertEquals(1, i.get(0).getSequence());
    	assertEquals(2, i.get(1).getSequence());
    	
		verify(mockedNoteRepository, atMost(1)).findById(100L);
		verify(mockedNoteRepository, atMost(1)).findBySequenceGreaterThanOrderBySequenceAsc(1L);
    }
    
	@Test
    public void testDeletingNotExistentNote() {
		when(mockedNoteRepository.findById(1234L)).thenReturn(Optional.empty());
		  
		assertThrows(EntityNotFoundException.class, () -> {
			noteService.delete(1234L);
	    });
    }
	
    @Test
    public void testStatusUpdate() {
    	Note n = NoteBuilder.builder().title("title1").content("content1").
					id(100L).sequence(1).timestamp(new Date()).status(Status.ACTIVE).build();
    	
		when(mockedNoteRepository.findById(100L)).thenReturn(Optional.of(n));
		when(mockedNoteRepository.save(n)).thenReturn(n);
		
    	Status prevStatus = noteService.updateStatus(100, Status.INACTIVE);
    	
    	assertEquals(Status.ACTIVE, prevStatus);
    	
    	assertEquals(100, n.getId());
    	assertEquals(1, n.getSequence());
    	assertNotNull(n.getTimestamp());
    	assertEquals(Status.INACTIVE, n.getStatus());
    	assertEquals("title1", n.getTitle());
    	assertEquals("content1", n.getContent());
    }
}
