package com.pblgllgs.mongo.data;

import com.github.javafaker.Faker;
import com.pblgllgs.mongo.collections.Address;
import com.pblgllgs.mongo.collections.Person;
import com.pblgllgs.mongo.repository.AddressRepository;
import com.pblgllgs.mongo.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pblgl
 * Created on 09-11-2023
 */

@Component
@RequiredArgsConstructor
public class Loader implements CommandLineRunner {
    private final PersonService personService;
    private final AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Person> people = personService.findAllPeople();
        if (people.isEmpty()) {
            Faker faker = new Faker();
            for (int i = 0; i < 500; i++) {
                Address address1 = Address.builder()
                        .address1(faker.address().firstName())
                        .address2(faker.address().secondaryAddress())
                        .city(faker.address().city())
                        .build();
                addressRepository.save(address1);
                Address address2 =  Address.builder()
                        .address1(faker.address().firstName())
                        .address2(faker.address().secondaryAddress())
                        .city(faker.address().city())
                        .build();
                addressRepository.save(address2);
                Person person = Person.builder()
                        .age(faker.number().numberBetween(1,99))
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .hobbies(List.of(faker.programmingLanguage().name(),faker.programmingLanguage().name() ))
                        .addresses(List.of(address1, address2))
                        .build();
                personService.savePerson(person);
            }
        }
    }
}
