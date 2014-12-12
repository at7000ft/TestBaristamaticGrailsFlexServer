package com.baristamatic.dto

import com.baristamatic.domain.InventoryEntry

/**
 * <p> Title: InventoryEntryDto.java </p>
 * <p> Description:   
 *
 * </p>
 * <p> Jan 20, 2011</p>
 * @author RHOLLAND
 * DRS Technologies Inc.
 *
 *
 */
class InventoryEntryDto {
	String displayName
	BigDecimal cost
	Integer invCount

	InventoryEntryDto() {
	}

	InventoryEntryDto(InventoryEntry invEntry) {
		this.displayName = invEntry.recipeItem.displayName
		this.cost = invEntry.cost
		this.invCount = invEntry.invCount
	}
}
