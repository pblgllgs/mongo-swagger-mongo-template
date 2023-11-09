package com.pblgllgs.mongo.controllers;

import com.pblgllgs.mongo.collections.Person;
import com.pblgllgs.mongo.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<String> savePerson(@RequestBody Person person){
        return new ResponseEntity<>(personService.savePerson(person),HttpStatus.CREATED);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Person>> findByStartWithFirstName(@RequestParam("name") String firstName){
        return new ResponseEntity<>(personService.findByStartWithFirstName(firstName), HttpStatus.OK);
    }

    @GetMapping("/age")
    public ResponseEntity<List<Person>> findByAge(
            @RequestParam("minAge") Integer minAge,
            @RequestParam("maxAge") Integer maxAge){
        return new ResponseEntity<>(personService.findByAge(minAge,maxAge), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAllPeople(){
        return new ResponseEntity<>(personService.findAllPeople(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable("id") String id){
        personService.deletePersonById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/person")
    public ResponseEntity<Page<Person>> searchPerson(
            @RequestParam(value = "name",required = false) String firstName,
            @RequestParam(value = "minAge",required = false) Integer minAge,
            @RequestParam(value = "maxAge",required = false) Integer maxAge,
            @RequestParam(value = "city",required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "asc") String sort
    ){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sort).descending());

        return new ResponseEntity<>(personService.search(firstName,minAge,maxAge,city,pageable), HttpStatus.OK);
    }

    @GetMapping("/oldest")
    public List<Document> findOldestPersonByCity(){
        return personService.findOldestPersonByCity();
    }
}
