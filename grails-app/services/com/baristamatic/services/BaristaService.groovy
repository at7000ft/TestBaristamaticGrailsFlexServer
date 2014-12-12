package com.baristamatic.services

import java.util.Set

import org.springframework.flex.remoting.RemotingDestination
import org.springframework.flex.remoting.RemotingInclude

import com.baristamatic.domain.Drink
import com.baristamatic.domain.InventoryEntry
import com.baristamatic.domain.RecipeEntry
import com.baristamatic.domain.RecipeItem
import com.baristamatic.dto.DrinkDto
import com.baristamatic.dto.InventoryEntryDto

import flex.messaging.MessageException;


@RemotingDestination
class BaristaService {

	static transactional = true

	@RemotingInclude
	List<DrinkDto> getDrinks() {
		log.debug("getDrinks: called")
		List<Drink> drinks =  Drink.findAll()
		List<DrinkDto> dtos = drinks.collect { new DrinkDto(it) }
		return dtos
	}

	@RemotingInclude
	List<InventoryEntryDto> getInventory() {
		log.debug("getInventory: called")
		List<InventoryEntry> ientries =  InventoryEntry.findAll()
		List<InventoryEntryDto> dtos = ientries.collect { new InventoryEntryDto(it) }
		log.debug("getInventory: returning ${dtos.size()} dtos")
		return dtos
	}

	@RemotingInclude
	void orderDrink(int drinkNumber) {
		log.debug("orderDrink: called for number ${drinkNumber}")
		Drink drink = Drink.findWhere(number: drinkNumber)
		Set<RecipeEntry> recipeEntries = drink.recipeEntries
		recipeEntries.each() {
			RecipeItem item = it.item
			InventoryEntry invItem = InventoryEntry.findWhere(recipeItem: item)
			invItem.invCount = (invItem.invCount - it.icount) < 0 ? 0 : invItem.invCount - it.icount
			log.debug("orderDrink: count remaining for ${invItem.recipeItem.iname} - ${invItem.invCount}")
		}
	}

	@RemotingInclude
	void reStockInventory() {
		log.debug("reStockInventory: called")
		InventoryEntry.findAll().each() {
			it.invCount = InventoryEntry.START_INVENTORY_COUNT
		}
	}

	@RemotingInclude
	void addNewIngredient(InventoryEntryDto dto) {
		log.debug("addNewIngredient: called")
		try {
			def rItem = new RecipeItem(iname: dto.displayName.toLowerCase().replaceAll(" ", ""), displayName: dto.displayName)
			def invEntry = new InventoryEntry(cost: dto.cost, invCount:  dto.invCount, recipeItem: rItem)
			invEntry.save()
		} catch (Exception e) {
			throw new MessageException(e.message())
		}
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

	static Boolean areIngredientsAvailable(Drink drink) {
		Set<RecipeEntry> recipeEntries = drink.recipeEntries
		for (each in recipeEntries) {
			def cntNeeded = each.icount
			def cntAvailable = InventoryEntry.find("from InventoryEntry as ie where ie.recipeItem.iname=?", each.item.iname).invCount
			//println "areIngredientsAvailable for ${each.item.iname} needs ${cntNeeded}  cntAvailable - ${cntAvailable}\n"
			Set<InventoryEntry> ies = InventoryEntry.findAll()
			if (cntNeeded > cntAvailable) {
				return false
			}
			if (1 > 10) {
				return false
			}
		}
		return true
	}
}
