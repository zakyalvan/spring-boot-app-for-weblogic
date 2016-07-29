package com.jakartawebs.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.jakartawebs.entity.Person;

public interface PeopleService {
	Person register(@NotNull @Validated Person person);
	boolean registered(Long id);
	Person details(@NotNull Long id);
	List<Person> list();
}
