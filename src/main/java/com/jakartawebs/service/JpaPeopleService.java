package com.jakartawebs.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.jakartawebs.entity.Person;
import com.jakartawebs.repository.PeopleRepository;

@Service
@Validated
@Transactional(readOnly=true)
public class JpaPeopleService implements PeopleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(JpaPeopleService.class);
	
	@Autowired
	private PeopleRepository peopleRepository;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Person register(Person person) {
		LOGGER.info("Register person with name '{}' and email address '{}'", person.getName(), person.getEmail());
		return peopleRepository.save(person);
	}
	
	@Override
	public boolean registered(Long id) {
		return peopleRepository.exists(id);
	}

	@Override
	public Person details(Long id) {
		return peopleRepository.findOne(id);
	}

	@Override
	public List<Person> list() {
		return peopleRepository.findAll();
	}
}