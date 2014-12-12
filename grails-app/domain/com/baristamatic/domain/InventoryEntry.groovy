package com.baristamatic.domain

class InventoryEntry {

	BigDecimal cost	//Use BigDecimal for currency
	int invCount
	final static int START_INVENTORY_COUNT = 10

	//One-to-one relation with recipeItem
	//Can get to a recipeItem from an InventoryEntry but not vis-versa
	RecipeItem recipeItem

	static constraints = {
		cost(nullable: false)
		invCount(nullable:false)
		recipeItem(nullable:false)
	}

	static mapping = { recipeItem lazy:false } 	//Load recipeItem with InventoryEntry

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "InventoryEntry for " + recipeItem.iname;
	}
}
