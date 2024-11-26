package org.poc.util;

import org.poc.data.Person;
import org.poc.data.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class ConversionBenchmark {

    public static void measurePerformance(int numberOfObjects, BiFunction<Object, Class<Animal>, Animal> converter) {
        System.out.println("\nMeasuring performance for converting " + numberOfObjects + " objects...");

        List<Person> persons = generateRandomPersons(numberOfObjects);

        long startTime = System.currentTimeMillis();

        for (Person person : persons) {
            converter.apply(person, Animal.class);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Time taken to convert " + numberOfObjects + " objects: " + duration + " milliseconds");
    }

    private static List<Person> generateRandomPersons(int numberOfObjects) {
        List<Person> persons = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfObjects; i++) {
            String name = "Person" + i;
            int age = random.nextInt(100);
            String gender = random.nextBoolean() ? "Male" : "Female";
            String taxId = "TaxId" + i;
            String phoneNumber = "Phone" + i;
            String email = "email" + i + "@example.com";
            String country = "Country" + i;
            String city = "City" + i;
            String postalCode = "PostalCode" + i;
            String state = "State" + i;
            String street = "Street" + i;

            persons.add(new Person(name, age, gender, taxId, phoneNumber, email, country, city, postalCode, state, street));
        }

        return persons;
    }
}