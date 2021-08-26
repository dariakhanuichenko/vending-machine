package com.example.vending_machine.mapper;


import com.example.vending_machine.domain.Drink;
import com.example.vending_machine.service.dto.DrinkDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DrinkMapper {

    public static DrinkDTO toDTO(Drink entity) {
        return DrinkDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .quantity(entity.getQuantity())
                .build();
    }

    public static List<DrinkDTO> toDTOList(List<Drink> entities) {
        return entities.stream().map(DrinkMapper::toDTO).collect(Collectors.toList());
    }
}
