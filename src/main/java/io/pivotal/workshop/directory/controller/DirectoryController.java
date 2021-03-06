package io.pivotal.workshop.directory.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.pivotal.workshop.directory.domain.Person;
import io.pivotal.workshop.directory.repository.DirectoryRepository;

@RestController
//is a convenience annotation that does nothing more than adding the @Controller and @ResponseBody annotations
//@ResponseBody:Annotation that indicates a method return value should be bound to the web response body
//@Controller:This annotation serves as a specialization of @Component,
// allowing for implementation classes to be autodetected through classpath scanning.
// It is typically used in combination with annotated handler methods based on the RequestMapping annotation.
public class DirectoryController {

	private DirectoryRepository repo;

	@Autowired
	//annotation allows you to skip configurations elsewhere of what to inject and just does it for you.
	public DirectoryController(DirectoryRepository repo) {
		this.repo = repo;
	}

	@RequestMapping("/directory")
	//maps HTTP requests to handler methods of MVC and REST controllers.
	public ResponseEntity<Iterable<Person>> findAll() {
		return ResponseEntity.ok(this.repo.findAll());
	}
	
	
	//SOLUTION: Challenges
	@RequestMapping("/directory/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		Optional<Person> result = this.repo.findById(id);

		if (result.isPresent()) {
			return ResponseEntity.ok(result.get());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
	}

	@RequestMapping("/directory/search")
	public ResponseEntity<?> searchByEmail(@RequestParam String email) {
		Optional<Person> result = this.repo.findByEmail(email);

		if (result.isPresent()) {
			return ResponseEntity.ok(result.get());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
	}

	@RequestMapping(value = "/directory/{id}", method = { RequestMethod.DELETE })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	//This annotation marks a handler method or exception class with the status code() and reason() that should be returned.
	public void deletePerson(@PathVariable String id) {
		this.repo.delete(id);
	}
	
	
	@RequestMapping(value = "/directory", method = { RequestMethod.POST })
	public ResponseEntity<?> addPerson(@RequestBody Person person) {

		return this.save(person);
	}

	@RequestMapping(value = "/directory", method = { RequestMethod.PUT })
	public ResponseEntity<?> updatePerson(@RequestBody Person person) {
		return this.save(person);
	}

	private ResponseEntity<?> save(Person person) {
		
		Person result = this.repo.save(person);

		final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/directory/{id}").build()
				.expand(person.getId()).toUri();

		return ResponseEntity.created(location).body(result);
	}
	

	//SOLUTION: Homework
	/*
	@RequestMapping(value = "/directory", method = { RequestMethod.POST })
	public ResponseEntity<?> addPerson(@Valid @RequestBody Person person, BindingResult bindingResult) {

		return this.save(person, bindingResult);
	}

	@RequestMapping(value = "/directory", method = { RequestMethod.PUT })
	public ResponseEntity<?> updatePerson(@Valid @RequestBody Person person, BindingResult bindingResult) {
		return this.save(person, bindingResult);
	}

	private ResponseEntity<?> save(Person person, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PersonError(bindingResult.getAllErrors()));
		}

		Person result = this.repo.save(person);

		final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/directory/{id}").build()
				.expand(person.getId()).toUri();

		return ResponseEntity.created(location).body(result);
	}
	*/
}
