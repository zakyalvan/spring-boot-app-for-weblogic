package com.jakartawebs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jakartawebs.entity.Person;
import com.jakartawebs.service.PeopleService;

@RestController
@RequestMapping(value="/people")
public class PeopleController {
	@Autowired
	private PeopleService peopleService;
	
	@RequestMapping(method=RequestMethod.POST)
	public HttpEntity<Person> register(@Validated @RequestBody Person person, BindingResult bindings) {
		if(bindings.hasErrors()) {
			return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Person>(peopleService.register(person), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public HttpEntity<Person> details(@PathVariable Long id) {
		if(!peopleService.registered(id)) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Person>(peopleService.details(id), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Person> list() {
		return peopleService.list();
	}
}
