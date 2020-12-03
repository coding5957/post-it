package uk.co.northrealm.postit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.co.northrealm.postit.model.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    
	@Query(value = "SELECT max(sequence) FROM Note")
	Optional<Long> findMaxSequence();
	
	Iterable<Note> findAllByOrderBySequenceAsc();
	
	Iterable<Note> findBySequenceGreaterThanOrderBySequenceAsc(Long seq);
}
