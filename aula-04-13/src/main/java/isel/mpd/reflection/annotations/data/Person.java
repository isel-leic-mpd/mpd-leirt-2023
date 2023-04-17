package isel.mpd.reflection.annotations.data;

import isel.mpd.reflection.annotations.Attribute;

import java.time.LocalDate;

//@DBEntity(dbName="PersonDB")

public class Person {

    @Attribute(dbName="#NAME")
    private final String name;

    @Attribute(converter="LocalDateConverter")
    private final LocalDate birthDate;

    public Person(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }
}