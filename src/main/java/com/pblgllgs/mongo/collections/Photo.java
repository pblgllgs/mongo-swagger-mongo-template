package com.pblgllgs.mongo.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@Data
@Document(collection = "photos")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Photo{

    @Id
    private String id;
    private String title;
    private Binary photo;
}
