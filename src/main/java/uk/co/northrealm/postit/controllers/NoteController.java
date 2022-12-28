package uk.co.northrealm.postit.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uk.co.northrealm.postit.model.Note;
import uk.co.northrealm.postit.services.INoteService;

@Controller
public class NoteController {
	private final INoteService noteService;

	@Autowired
	public NoteController(INoteService noteService) {
		this.noteService = noteService;
	}

	@GetMapping("/")
	public String getLandingPage(Model model) {
		
		//Add all the current notes to the model
		model.addAttribute("notes", noteService.getAllNotes());
		return "list-notes";
	}

	@GetMapping("/add")
	public String getAddNotePage(Note note) {
		return "add-note";
	}

	@PostMapping("/addnote")
	public String postAddNote(@Valid Note note, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-note";
		}

		noteService.create(note);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String getEditNotePage(@PathVariable("id") long id, Model model) {
		
		//Add the note we are going to edit to the model
		Note note = noteService.getNoteById(id);
		model.addAttribute("note", note);
		
		return "edit-note";
	}

	@PostMapping("/update/{id}")
	public String postUpdateNote(@PathVariable("id") long id, @Valid Note note, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			return "edit-note";
		}

		noteService.updateTitleAndContent(id, note);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String getDeleteNote(@PathVariable("id") long id, Model model) {
		
		noteService.delete(id);
		
		return "redirect:/";
	}
}
