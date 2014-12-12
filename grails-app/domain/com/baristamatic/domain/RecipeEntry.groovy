package com.baristamatic.domain

class RecipeEntry {

	static belongsTo = Drink

	RecipeItem item
	Integer icount

	static constraints = {
	}

	static mapping = { item lazy:false } 	//Load RecipeItem with RecipeEntry

	/* 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "RecipeEntry for " + item.iname;
	}
}
