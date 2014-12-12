package com.baristamatic.domain

import grails.test.*

import com.baristamatic.dto.DrinkDto

class DrinkIntegrationTests extends GroovyTestCase {
	protected void setUp() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testDrinkSave() {
		def drink = new Drink(number: 1, dname: "coffee", displayName: "Coffee")
		assertNotNull drink.save()	//Check save succeded, returns false on failure
		assertNotNull drink.id		//Check save set the id

		//After drink is saved any objects added to it are persisted without additional saves
		def rEntryCoffee = new RecipeEntry(item: new RecipeItem(iname: "coffee", displayName: "Coffee"), icount: 3)
		drink.addToRecipeEntries(rEntryCoffee)
		def rEntrySugar = new RecipeEntry(item: new RecipeItem(iname: "sugar", displayName: "Sugar"), icount: 1)
		drink.addToRecipeEntries(rEntrySugar)
		def rEntryCream = new RecipeEntry(item: new RecipeItem(iname: "cream", displayName: "Cream"), icount: 1)
		drink.addToRecipeEntries(rEntryCream)

		assertEquals 3, drink.recipeEntries.size()

		def foundDrink = Drink.get(drink.id)
		assertEquals "coffee", foundDrink.dname


		def drink2 = new Drink(number: 1, dname: "decafCoffee", displayName: "DecafCoffee").save()
		assertNotNull drink2 //Check save succeded
		assertNotNull drink2.id		//Check save set the id
		drink2.addToRecipeEntries(new RecipeEntry(item: new RecipeItem(iname: "decafCoffee", displayName: "DecafCoffee"), icount: 3))
		drink2.addToRecipeEntries(new RecipeEntry(item: new RecipeItem(iname: "sugar", displayName: "Sugar"), icount: 1))
		drink2.addToRecipeEntries(new RecipeEntry(item: new RecipeItem(iname: "cream", displayName: "Cream"), icount: 1))

	}

	void testDrinkSave2() {
		def riCoffee = new RecipeItem(iname: "coffee", displayName: "Coffee")
		def riSugar = new RecipeItem(iname: "sugar", displayName: "Sugar")
		def riCream = new RecipeItem(iname: "cream", displayName: "Cream")
		def riDecafCoffee = new RecipeItem(iname: "decafCoffee", displayName: "DecafCoffee")
		def riCocoa = new RecipeItem(iname: "cocoa", displayName: "Cocoa")
		def riSteamedMilk = new RecipeItem(iname: "steamedMilk", displayName: "Steamed Milk")
		def riFoamedMilk = new RecipeItem(iname: "foamedMilk", displayName: "Foamed Milk")
		def riEspresso = new RecipeItem(iname: "espresso", displayName: "Espresso")
		def riWhippedCream = new RecipeItem(iname: "whippedCream", displayName: "Whipped Cream")

		def drink = new Drink(number: 1, dname: "coffee", displayName: "Coffee").save()
		drink.addToRecipeEntries(new RecipeEntry(item: riCoffee, icount: 3))
		drink.addToRecipeEntries(new RecipeEntry(item: riSugar, icount: 1))
		drink.addToRecipeEntries(new RecipeEntry(item: riCream, icount: 1))
	}

	void testDrinkSaveAndUpdate() {
		def drink = new Drink(number: 1, dname: "coffee", displayName: "Coffee")
		assertNotNull drink.save()	//Check save succeded
		assertNotNull drink.id		//Check save set the id

		def foundDrink = Drink.get(drink.id)
		foundDrink.dname = "coffee2"
		foundDrink.save()

		def updatedDrink = Drink.get(drink.id)
		assertEquals "coffee2", updatedDrink.dname

	}

	void testDrinkSaveAndDelete() {
		def drink = new Drink(number: 1, dname: "coffee", displayName: "Coffee")
		assertNotNull drink.save()	//Check save succeded

		def foundDrink = Drink.get(drink.id)
		foundDrink.delete()			//Delete foundDrink

		assertFalse Drink.exists(foundDrink.id)

	}

	void testNegativeDrinkSave() {
		def drink = new Drink(number: 1, dname: "co", displayName: "Coffee")
		assertFalse drink.validate()
		assertTrue drink.hasErrors()

		def errors = drink.errors
		assertEquals "size.toosmall", errors.getFieldError("dname").code
		assertEquals "co", errors.getFieldError("dname").rejectedValue

		assertNull errors.getFieldError("number")		//Verify that number field has no error
	}

	void testDrinkDto() {
		println "testDrinkDto: starting"
		def riCoffee = new RecipeItem(iname: "coffee", displayName: "Coffee")
		def riSugar = new RecipeItem(iname: "sugar", displayName: "Sugar")
		def riCream = new RecipeItem(iname: "cream", displayName: "Cream")
		//
		//		def ieCoffee = new InventoryEntry(cost: 0.75, invCount: 1, recipeItem: riCoffee).save()
		//		def ieSugar = new InventoryEntry(cost: 0.25, invCount: 10, recipeItem: riSugar).save()
		//		def ieCream = new InventoryEntry(cost: 0.25, invCount: 10, recipeItem: riCream).save()

		InventoryEntry ie = InventoryEntry.find("from InventoryEntry as ie where ie.recipeItem.iname=?", "coffee")
		ie.invCount = 1

		def drink = new Drink(number: 1, dname: "coffee", displayName: "Coffee").save()
		drink.addToRecipeEntries(new RecipeEntry(item: riCoffee, icount: 3))
		drink.addToRecipeEntries(new RecipeEntry(item: riSugar, icount: 1))
		drink.addToRecipeEntries(new RecipeEntry(item: riCream, icount: 1))
		DrinkDto dto = new DrinkDto(drink)
		assertEquals "Coffee", dto.displayName
		assertFalse dto.inStock
	}
}
