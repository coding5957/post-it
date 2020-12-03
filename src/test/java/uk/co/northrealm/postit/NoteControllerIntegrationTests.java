package uk.co.northrealm.postit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.common.collect.Iterables;

import uk.co.northrealm.postit.builders.NoteBuilder;
import uk.co.northrealm.postit.model.Note;
import uk.co.northrealm.postit.model.Status;
import uk.co.northrealm.postit.repositories.NoteRepository;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private NoteRepository noteRepository;

	@Test
	void testCreateNewNote() throws Exception {
		Long maxSeq = noteRepository.findMaxSequence().orElse(0L);
		
    	Note n = NoteBuilder.builder().title("title1").content("content1").build();

		mockMvc.perform(MockMvcRequestBuilders.post("/addnote")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).param("title", n.getTitle()).param("content", n.getContent()))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());

		Iterable<Note> notes = noteRepository.findAllByOrderBySequenceAsc();
		Note dbNote = Iterables.getLast(notes);
		
		assertEquals(maxSeq + 1, dbNote.getSequence());
		assertEquals(Status.ACTIVE, dbNote.getStatus());
	}

	@Test
	void testUpdateExistingNote() throws Exception {
		Long maxSeq = noteRepository.findMaxSequence().orElse(0L);
		
    	Note n = NoteBuilder.builder().timestamp(new Date()).title("title1").content("content1").sequence(maxSeq + 1).build();
		n = noteRepository.save(n);
		
		n.setTitle("modifiedtitle1");
		n.setContent("modifiedcontent1");

		mockMvc.perform(MockMvcRequestBuilders.post("/update/{id}", n.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).param("title", n.getTitle()).param("content", n.getContent()))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());

		Note dbNote = noteRepository.findById(n.getId()).get();
		
		assertEquals(maxSeq + 1, dbNote.getSequence());
		assertEquals(Status.ACTIVE, dbNote.getStatus());
		assertEquals("modifiedtitle1", dbNote.getTitle());
		assertEquals("modifiedcontent1", dbNote.getContent());
	}

	@Test
	void testDeleteExistingNote() throws Exception {
		Long maxSeq = noteRepository.findMaxSequence().orElse(0L);
		
    	Note n = NoteBuilder.builder().timestamp(new Date()).title("title1").content("content1").sequence(maxSeq + 1).build();
		n = noteRepository.save(n);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/delete/{id}", n.getId()))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());

		Optional<Note> dbNote = noteRepository.findById(n.getId());
		assertTrue(dbNote.isEmpty());
	}
}
