package com.baristamatic.domain

class Drink {
	//One-to-many relationship with RecipeEntry
	static hasMany = [recipeEntries: RecipeEntry]

	Set<RecipeEntry> recipeEntries
	int number
	String dname
	String displayName


	static constraints = {
		number(blank:false)
		dname(size: 3..20)
		displayName(size: 3..20)
	}

	static mapping = { recipeEntries lazy:false } 	//Load recipeEntries with Drink
}
