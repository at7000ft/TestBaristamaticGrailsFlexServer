package com.baristamatic.domain

import com.baristamatic.dto.InventoryEntryDto
import grails.test.*

class InventoryIntegrationTests extends GroovyTestCase {


	void testInventoryEntry() {

		def invEnt = new InventoryEntry(cost: 0.75, invCount: 10, recipeItem: new RecipeItem(iname: "decafCoffee", displayName: "DecafCoffee"))
		assertNotNull invEnt.save()//Check save succeded
		assertNotNull invEnt.id		//Check save set the id


		def foundInvEnt = InventoryEntry.get(invEnt.id)
		assertEquals 0.75, foundInvEnt.cost
		assertEquals "decafCoffee", foundInvEnt.recipeItem.iname

		def ri2 = new RecipeItem(iname: "cream", displayName: "Cream")
		def invEnt3 = new InventoryEntry(cost: 0.25, invCount: 10, recipeItem: ri2)
		assertNotNull invEnt3.save()//Check save succeded
		assertNotNull invEnt3.id		//Check save set the id

	}
	void testAll() {
		def riCoffee = new RecipeItem(iname: "coffee", displayName: "Coffee")
		def riSugar = new RecipeItem(iname: "sugar", displayName: "Sugar")
		def riCream = new RecipeItem(iname: "cream", displayName: "Cream")

		def ieCoffee = new InventoryEntry(cost: 0.75, invCount: 10, recipeItem: riCoffee)
		def ieSugar = new InventoryEntry(cost: 0.25, invCount: 10, recipeItem: riSugar)
		def ieCream = new InventoryEntry(cost: 0.25, invCount: 10, recipeItem: riCream)

		Drink drink = new Drink(number: 1, dname: "coffee", displayName: "Coffee").save()
		drink.addToRecipeEntries(new RecipeEntry(item: riCoffee, icount: 3))
		drink.addToRecipeEntries(new RecipeEntry(item: riSugar, icount: 1))
		drink.addToRecipeEntries(new RecipeEntry(item: riCream, icount: 1))
		assertNotNull drink
		assertEquals "Coffee", drink.recipeEntries.find{it.item.iname == "coffee"}.item.displayName
	}

	void testInventoryEntryDto() {
		def riCoffee = new RecipeItem(iname: "coffee", displayName: "Coffee")


		def ieCoffee = new InventoryEntry(cost: 0.75, invCount: 10, recipeItem: riCoffee).save()
		InventoryEntryDto dto = new InventoryEntryDto(ieCoffee)
		assertEquals "Coffee", dto.displayName
	}
}
