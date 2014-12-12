package com.baristamatic.services

import java.util.Set

import org.springframework.flex.remoting.RemotingDestination
import org.springframework.flex.remoting.RemotingInclude

import com.baristamatic.domain.Drink
import com.baristamatic.domain.InventoryEntry
import com.baristamatic.domain.RecipeEntry


@RemotingDestination
class BaristaService {

	static transactional = true

	@RemotingInclude
	List<Drink> getDrinks() {
		log.debug("getDrinks: called")
		List<Drink> drinks =  Drink.findAll()
		return drinks
	}

	@RemotingInclude
	List<InventoryEntry> getInventory() {
		log.debug("getInventory: called")
		return InventoryEntry.findAll()
	}

	@RemotingInclude
	BigDecimal getDrinkCost(String inputName) {
		log.debug("getDrinkCost: called for " + inputName)
		Drink drink = Drink.findWhere(dname: inputName)
		return costForDrink(drink)
	}

	static BigDecimal costForDrink(Drink drink) {
		Set<RecipeEntry> recipeEntries = drink.recipeEntries
		BigDecimal totalCost = 0.0
		recipeEntries.each() {
			String ingredient = it.item.iname
			Integer lcount = it.icount
			BigDecimal cost = InventoryEntry.find("from InventoryEntry as ie where ie.recipeItem.iname=?", ingredient).cost
			totalCost += cost*lcount
		}

		return totalCost
	}
}
