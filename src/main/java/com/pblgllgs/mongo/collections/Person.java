package com.pblgllgs.mongo.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@Document(collection = "persons")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Person {
    @Id
    private String personId;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<String> hobbies;
    private List<Address> addresses;
}
