package com.example.vending_machine.controller;

import com.example.vending_machine.service.DrinkService;
import com.example.vending_machine.service.dto.DrinkDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class DrinkController {

    public final DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    /**
     * create drink  with name and quantity = 0
     *
     * @param dto - object to create drink
     * @return result of creation
     */
    @PostMapping
    public String createDrink(@RequestBody DrinkDTO dto) {
        log.info("Create drink : {}", dto);
        if (dto.getId() != null) {
            return "Id is not null";
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            drinkService.create(dto);
            return "Drink with name \"" + dto.getName() + "\" was saved successfully!";
        } else {
            return "Invalid name of drink";
        }
    }

    /**
     * edit drink's name and quantity
     *
     * @param dto - object with new information about drink
     * @return result of editing
     */
    @PutMapping
    public String updateDrink(@RequestBody DrinkDTO dto) {
        log.info("Update drink : {}", dto);
        if (dto.getId() == null) {
            return "Id is  null";
        }
        if (dto.getName() != null && !dto.getName().isEmpty() && dto.getQuantity() != null) {
            drinkService.update(dto);
            return "Drink with name \"" + dto.getName() + "\" was updated successfully!";
        } else {
            return "Invalid data of drink";
        }
    }

    /**
     * Delete drink with id
     *
     * @param id - identifier of the drink
     */
    @DeleteMapping("/{id}")
    public void deleteDrink(@PathVariable Long id) {
        log.info("Delete drink with id : {}", id);
        drinkService.delete(id);
    }

    /**
     * Get page with drinks
     *
     * @param pageable -  parameters of pagination
     * @return page of drinks
     */
    @GetMapping
    public Page<DrinkDTO> getDrinks(Pageable pageable) {
        log.info("Get drinks with page parameters : {}", pageable);

        return drinkService.getPage(pageable);
    }

    /**
     * Get quantity of drink with id
     *
     * @param id -  identifier of the drink
     * @return quantity of drink with id
     */
    @GetMapping("/quantity/{id}")
    public Integer getDrinks(@PathVariable Long id) {
        log.info("Get quantity of drinks with id : {}", id);

        return drinkService.getQuantityById(id);
    }

    /**
     * Decrement quantity of drink with id
     *
     * @param id -  identifier of the drink
     */
    @GetMapping("/takeOne/{id}")
    public void takeOneDrink(@PathVariable Long id) {
        log.info("Take one drink by id : {}", id);
        drinkService.takeOne(id);
    }

    /**
     * Add drinks with id
     *
     * @param id       -  identifier of the drink
     * @param quantity -  identifier of the drink
     */
    @GetMapping("/putOne/{id}/{quantity}")
    public void putOneDrink(@PathVariable Long id, @PathVariable Integer quantity) {
        if (quantity > 0) {
            log.info("Put {} drinks by id : {}", quantity, id);
            drinkService.putById(id, quantity);
        } else {
            throw new RuntimeException("Invalid quantity");
        }
    }
}
