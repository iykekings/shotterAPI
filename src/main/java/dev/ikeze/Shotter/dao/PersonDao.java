package dev.ikeze.Shotter.dao;

import dev.ikeze.Shotter.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonDao {
    int insertPerson(UUID id, Person person);
    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return  insertPerson(id, person);
    }

    List<Person> selectAllPeople();
}
