package org.poc.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poc.annotation.AttributeMapping;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private int age;

    @AttributeMapping(targetAttribute = "sex", targetClass = Animal.class)
    @AttributeMapping(targetAttribute = "type", targetClass = Furniture.class)
    private String gender;

    private String taxId;
    private String phoneNumber;
    private String email;
    private String country;
    private String city;
    private String postalCode;
    private String state;
    private String street;
}
