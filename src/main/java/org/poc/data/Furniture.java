package org.poc.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Furniture {
    private String name;
    private String type;
    private String color;
    private String material;
    private int price;
}
