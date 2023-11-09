package com.pblgllgs.mongo.repository;

import com.pblgllgs.mongo.collections.Address;
import com.pblgllgs.mongo.collections.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
}
