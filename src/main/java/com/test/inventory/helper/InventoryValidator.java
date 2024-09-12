package com.test.inventory.helper;

import org.springframework.stereotype.Service;

import com.test.inventory.dto.InventoryDTO;
import com.test.inventory.exceptionhandler.ValidationException;

@Service
public class InventoryValidator {

	public void validateInventory(InventoryDTO inventoryDTO) {
		String error="";
		String partNumber = inventoryDTO.getPartNumber();
		int inventoryQuantity = inventoryDTO.getInventoryQuantity();
		
		if(partNumber==null || partNumber.isBlank() || partNumber.isEmpty()) {
			error += " Part Number should not be blank.";
		}
		if(inventoryQuantity<=0) {
			error += " Inventory Quantity should be greater than 0.";
		}
		if(!error.isEmpty())
			throw new ValidationException("400 Bad Request: "+error);
	}
}
