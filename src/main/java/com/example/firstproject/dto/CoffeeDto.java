package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
//NoArgsConstructor 넣으면 post할떄 null값들어감
@ToString
public class CoffeeDto {
    private Long id;
    private String name;
    private Long price;
    public Coffee toEntity() {
        return new Coffee(id, name, price);
    }
}
