package org.poc;

import org.poc.data.Animal;
import org.poc.data.Person;
import org.poc.util.CachedComplexTypeConverter;
import org.poc.util.ComplexTypeConverter;

import java.text.MessageFormat;

import static org.poc.util.ConversionBenchmark.measurePerformance;

public class Main {

    public static void main(String[] args) {
        Person john = new Person("John Doe", 30, "Male", "123-45-6789",
                "555-1234", "john.doe@example.com", "USA", "New York",
                "10001", "NY", "123 Main St");

        Animal pig = ComplexTypeConverter.convert(john, Animal.class);

        System.out.println(MessageFormat.format("Mapped animal using default converter. Animal info:\nName: {0}\nAge: {1}\nSex: {2}",
                                                        pig.getName(), pig.getAge(), pig.getSex()));

        Animal dog = CachedComplexTypeConverter.convert(john, Animal.class);

        System.out.println(MessageFormat.format("Mapped animal using cached converter. Animal info:\nName: {0}\nAge: {1}\nSex: {2}",
                                                        dog.getName(), dog.getAge(), dog.getSex()));

        int benchmarkSize = 10000;
        measurePerformance(benchmarkSize, ComplexTypeConverter::convert);
        measurePerformance(benchmarkSize, CachedComplexTypeConverter::convert);
    }
}