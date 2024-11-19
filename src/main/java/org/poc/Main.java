package org.poc;

import org.poc.data.Animal;
import org.poc.data.Person;
import org.poc.util.ComplexTypeConverter;

import java.text.MessageFormat;

public class Main {

    public static void main(String[] args) {
        Person john = new Person("John Doe", 30, "Male", "123-45-6789",
                "555-1234", "john.doe@example.com", "USA", "New York",
                "10001", "NY", "123 Main St");

        Animal pig = ComplexTypeConverter.convert(john, Animal.class);

        System.out.println(MessageFormat.format("Animal info:\nName: {0}\nAge: {1}\nSex: {2}",
                                                        pig.getName(), pig.getAge(), pig.getSex()));
    }
}