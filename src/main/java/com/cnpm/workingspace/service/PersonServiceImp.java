package com.cnpm.workingspace.service;

import com.cnpm.workingspace.model.Person;
import com.cnpm.workingspace.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImp implements PersonService{
	
	
    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public void insertPerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public boolean updatePerson(Person person,int id) {
        Optional<Person> curPerson=getPersonById(id);
        if(curPerson.isPresent()){
            Person curPersonV=curPerson.get();
            curPersonV.setFirstName(person.getFirstName());
            curPersonV.setLastName(person.getLastName());
            curPersonV.setAddress(person.getAddress());
            curPersonV.setCity(person.getCity());
            personRepository.save(curPersonV);
            return true;
        }
        return false;
    }

    @Override
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    @Override
    public Optional<Person> getPersonById(int id) {
        return personRepository.findById(id);
    }
}
