package com.jakartawebs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jakartawebs.entity.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {

}
