import com.baristamatic.domain.Drink
import com.baristamatic.domain.InventoryEntry
import com.baristamatic.domain.RecipeEntry
import com.baristamatic.domain.RecipeItem


class BootStrap {

	def init = { servletContext ->
		//Create 9 ingredients
		def riCoffee = new RecipeItem(iname: "coffee", displayName: "Coffee").save()
		def riSugar = new RecipeItem(iname: "sugar", displayName: "Sugar").save()
		def riCream = new RecipeItem(iname: "cream", displayName: "Cream").save()
		def riDecafCoffee = new RecipeItem(iname: "decafCoffee", displayName: "DecafCoffee").save()
		def riCocoa = new RecipeItem(iname: "cocoa", displayName: "Cocoa").save()
		def riSteamedMilk = new RecipeItem(iname: "steamedMilk", displayName: "Steamed Milk").save()
		def riFoamedMilk = new RecipeItem(iname: "foamedMilk", displayName: "Foamed Milk").save()
		def riEspresso = new RecipeItem(iname: "espresso", displayName: "Espresso").save()
		def riWhippedCream = new RecipeItem(iname: "whippedCream", displayName: "Whipped Cream").save()

		//Create and InventoryEntry for each ingredient
		new InventoryEntry(cost: 0.75, invCount: 10, recipeItem: riCoffee).save()
		new InventoryEntry(cost: 0.25, invCount: 10, recipeItem: riSugar).save()
		new InventoryEntry(cost: 0.25, invCount: 10, recipeItem: riCream).save()
		new InventoryEntry(cost: 0.75, invCount: 10, recipeItem: riDecafCoffee).save()
		new InventoryEntry(cost: 0.90, invCount: 10, recipeItem: riCocoa).save()
		new InventoryEntry(cost: 0.35, invCount: 10, recipeItem: riSteamedMilk).save()
		new InventoryEntry(cost: 0.35, invCount: 10, recipeItem: riFoamedMilk).save()
		new InventoryEntry(cost: 1.10, invCount: 10, recipeItem: riEspresso).save()
		new InventoryEntry(cost: 1.00, invCount: 10, recipeItem: riWhippedCream).save()

		def coffee = new Drink(number: 1, dname: "coffee", displayName: "Coffee")
		coffee.save()
		coffee.addToRecipeEntries(new RecipeEntry(item: riCoffee, icount: 3))
		coffee.addToRecipeEntries(new RecipeEntry(item: riSugar, icount: 1))
		coffee.addToRecipeEntries(new RecipeEntry(item: riCream, icount: 1))

		def drinkDecaf = new Drink(number: 2, dname: "decafCoffee", displayName: "Decaf Coffee")
		drinkDecaf.save()
		drinkDecaf.addToRecipeEntries(new RecipeEntry(item: riDecafCoffee, icount: 3))
		drinkDecaf.addToRecipeEntries(new RecipeEntry(item: riSugar, icount: 1))
		drinkDecaf.addToRecipeEntries(new RecipeEntry(item: riCream, icount: 1))

		def caffeLatte = new Drink(number: 3, dname: "caffeLatte", displayName: "Caffe Latte")
		caffeLatte.save()
		caffeLatte.addToRecipeEntries(new RecipeEntry(item: riEspresso, icount: 2))
		caffeLatte.addToRecipeEntries(new RecipeEntry(item: riSteamedMilk, icount: 1))

		def caffeAmericano = new Drink(number: 4, dname: "caffeAmericano", displayName: "Caffe Americano")
		caffeAmericano.save()
		caffeAmericano.addToRecipeEntries(new RecipeEntry(item: riEspresso, icount: 3))

		def caffeMocha = new Drink(number: 5, dname: "caffeMocha", displayName: "Caffe Mocha")
		caffeMocha.save()
		caffeMocha.addToRecipeEntries(new RecipeEntry(item: riEspresso, icount: 1))
		caffeMocha.addToRecipeEntries(new RecipeEntry(item: riCocoa, icount: 1))
		caffeMocha.addToRecipeEntries(new RecipeEntry(item: riSteamedMilk, icount: 1))

		def cappuccino = new Drink(number: 6, dname: "cappuccino", displayName: "Cappuccino")
		cappuccino.save()
		cappuccino.addToRecipeEntries(new RecipeEntry(item: riEspresso, icount: 2))
		cappuccino.addToRecipeEntries(new RecipeEntry(item: riSteamedMilk, icount: 1))
		cappuccino.addToRecipeEntries(new RecipeEntry(item: riFoamedMilk, icount: 1))
	}
	def destroy = {
	}
}
