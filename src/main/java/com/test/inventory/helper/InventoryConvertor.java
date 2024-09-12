package com.test.inventory.helper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.test.inventory.dto.InventoryDTO;
import com.test.inventory.entity.Inventory;

@Service
public class InventoryConvertor {
	
	public Inventory convertDTOtoEntity(InventoryDTO inventoryDTO) {
		
		ModelMapper modelMapper = new ModelMapper();
		Inventory inventory = modelMapper.map(inventoryDTO,Inventory.class);
		return inventory;
	}
	
public InventoryDTO convertEntitytoDTO(Inventory inventory) {
		
		ModelMapper modelMapper = new ModelMapper();
		InventoryDTO inventoryDTO = modelMapper.map(inventory,InventoryDTO.class);
		return inventoryDTO;
	}

}
