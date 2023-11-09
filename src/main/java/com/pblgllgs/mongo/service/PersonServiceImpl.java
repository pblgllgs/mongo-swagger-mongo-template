package com.pblgllgs.mongo.service;

import com.pblgllgs.mongo.collections.Person;
import com.pblgllgs.mongo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public String savePerson(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> findByStartWithFirstName(String firstName) {
        List<Person> person = personRepository.findByFirstNameStartsWith(firstName);
        if (person == null) {
            throw new RuntimeException("NOT_FOUND");
        }
        return person;
    }

    @Override
    public List<Person> findByAge(Integer minAge, Integer maxAge) {
        List<Person> person = personRepository.findByAgeQueryBetween(minAge, maxAge);
        if (person == null) {
            throw new RuntimeException("NOT_FOUND");
        }
        return person;
    }

    @Override
    public List<Person> findAllPeople() {
        return personRepository.findAll();
    }

    @Override
    public void deletePersonById(String id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()) {
            throw new RuntimeException("NOT_FOUND");
        }
        personRepository.deleteById(id);
    }

    @Override
    public Page<Person> search(String firstName, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();
        if (firstName != null && !firstName.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(firstName, "i"));
        }
        if (minAge != null && maxAge != null) {
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if (city != null && !city.isEmpty()) {
            criteria.add(Criteria.where("addresses.city").is(city));
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Person.class), pageable, () -> mongoTemplate.count(query.skip(0).limit(0), Person.class)
        );
    }

    @Override
    public List<Document> findOldestPersonByCity() {
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");
        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestPerson");
        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);
        return mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
    }
}
