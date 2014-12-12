package com.baristamatic.domain

//Description of item that makes a Drink
class RecipeItem {
	static belongsTo = [RecipeEntry, InventoryEntry]

	String iname
	String displayName

	static constraints = {
		displayName(size: 3..20)
		iname(size: 3..20)
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "RecipeItem for " + iname;
	}
}
