package com.pblgllgs.mongo.service;

import com.pblgllgs.mongo.collections.Person;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

public interface PersonService {

    String savePerson(Person person);

    List<Person> findByAge(Integer minAge, Integer maxAge);
    List<Person> findByStartWithFirstName(String firstName);

    List<Person> findAllPeople();

    void deletePersonById(String id);

    Page<Person> search(String firstName, Integer minAge, Integer maxAge, String city, Pageable pageable);

    List<Document> findOldestPersonByCity();
}
