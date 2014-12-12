package com.baristamatic.dto

import com.baristamatic.domain.Drink
import com.baristamatic.services.BaristaService

/**
 * <p> Title: DrinkDto.java </p>
 * <p> Description:   
 *
 * </p>
 * <p> Jan 20, 2011</p>
 * @author RHOLLAND
 * DRS Technologies Inc.
 *
 *
 */
class DrinkDto {
	Integer number
	String displayName
	BigDecimal cost
	String[] recipe
	Boolean inStock

	DrinkDto() {
	}

	DrinkDto(Drink drink) {
		this.number = drink.number
		this.displayName = drink.displayName
		this.cost = BaristaService.costForDrink(drink)
		this.recipe = new String[drink.recipeEntries.size()]
		drink.recipeEntries.eachWithIndex() { entry, i ->
			recipe[i] = entry.item.displayName + ":" + entry.icount
		}
		this.inStock = BaristaService.areIngredientsAvailable(drink)
	}
}
