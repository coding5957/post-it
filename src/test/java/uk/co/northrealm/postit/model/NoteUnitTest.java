package uk.co.northrealm.postit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

class NoteUnitTest {

	@Test
	void testConstructor() {
		
		Date now = new Date();
		Note n = new Note(now, "title1", "content1");
		
		assertEquals(now, n.getTimestamp());
		assertEquals("title1", n.getTitle());
		assertEquals("content1", n.getContent());
	}
	
}
