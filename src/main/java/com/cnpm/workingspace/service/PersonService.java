package com.cnpm.workingspace.service;

import com.cnpm.workingspace.model.Person;

import java.util.*;

public interface PersonService {
    List<Person> getAllPerson();
    void insertPerson(Person person);
    boolean updatePerson(Person person,int id);
    void deletePerson(int id);
    Optional<Person> getPersonById(int id);
}
