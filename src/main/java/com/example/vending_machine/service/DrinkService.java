package com.example.vending_machine.service;


import com.example.vending_machine.domain.Drink;
import com.example.vending_machine.mapper.DrinkMapper;
import com.example.vending_machine.repository.DrinkRepository;
import com.example.vending_machine.service.dto.DrinkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public void create(DrinkDTO drinkDTO) {

        Drink drink = Drink.builder()
                .name(drinkDTO.getName())
                .quantity(0)
                .build();

        drinkRepository.save(drink);
    }

    public void update(DrinkDTO drinkDTO) {

        Drink drink = Drink.builder()
                .name(drinkDTO.getName())
                .quantity(drinkDTO.getQuantity())
                .build();

        drinkRepository.save(drink);
    }

    public Page<DrinkDTO> getPage(Pageable pageable) {
        Page<Drink> drinks = drinkRepository.findAll(pageable);
        return new PageImpl<>(DrinkMapper.toDTOList(drinks.getContent()), pageable, drinks.getTotalElements());
    }

    public void delete(Long id) {
        drinkRepository.deleteById(id);
    }

    public Integer getQuantityById(Long id) {

        return getById(id).map(Drink::getQuantity).orElse(null);
    }

    public void takeOne(Long id) {

        Optional<Drink> optionalDrink = getById(id);

        if (optionalDrink.isPresent()) {
            Drink drink = optionalDrink.get();
            if (drink.getQuantity() > 0) {
                drink.setQuantity(drink.getQuantity() - 1);
                drinkRepository.save(drink);
            } else throw new RuntimeException("Quantity is smaller than 1");
        } else {
            throw new RuntimeException("Drink with id not found");
        }
    }

    public void putById(Long id, Integer quantity) {

        Optional<Drink> optionalDrink = getById(id);

        if (optionalDrink.isPresent()) {
            Drink drink = optionalDrink.get();
            drink.setQuantity(drink.getQuantity() + quantity);
            drinkRepository.save(drink);
        }
    }

    public Optional<Drink> getById(Long id) {
        return drinkRepository.findById(id);
    }
}
