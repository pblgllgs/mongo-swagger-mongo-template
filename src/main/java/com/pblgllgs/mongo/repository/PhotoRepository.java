package com.pblgllgs.mongo.repository;

import com.pblgllgs.mongo.collections.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
}
