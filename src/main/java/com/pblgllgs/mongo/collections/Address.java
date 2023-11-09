package com.pblgllgs.mongo.collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "addresses")
public class Address {

    @Id
    private String id;
    private String address1;
    private String address2;
    private String city;
}
