package com.example.vending_machine.service.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrinkDTO {

    Long id;

    String name;

    Integer quantity;

}
