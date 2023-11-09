package com.pblgllgs.mongo.repository;

import com.pblgllgs.mongo.collections.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByFirstNameStartsWith(String name);

    @Query(value = "{ 'age' :  {$gt : ?0 ,$lt : ?1 } }")
    List<Person> findByAgeQueryBetween(int minAge, int maxAge);

    List<Person> findByAgeBetween(int minAge, int maxAge);
}
