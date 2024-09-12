package com.test.inventory.serviceImpl;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.test.inventory.dto.InventoryDTO;
import com.test.inventory.entity.Inventory;
import com.test.inventory.exceptionhandler.InventoryNotSufficientException;
import com.test.inventory.exceptionhandler.PartNumberDontExistException;
import com.test.inventory.helper.InventoryConvertor;
import com.test.inventory.helper.InventoryValidator;
import com.test.inventory.repo.InventoryJPARepository;
import com.test.inventory.service.InventoryService;

import jakarta.transaction.Transactional;

@Service
public class InventoryServiceImpl  implements InventoryService{
	
	private Logger logger = LoggerFactory.getLogger(InventoryService.class);
	
	@Autowired
	InventoryJPARepository inventoryRepo;
	
	@Autowired
	InventoryConvertor inventoryConvertor;
	
	@Autowired
	InventoryValidator inventoryValidator;


	@Override
	@Async
	@Transactional
	public CompletableFuture<Inventory> getInventoryByPartNumber(String partNumber) throws RuntimeException{
		Inventory inventory=inventoryRepo.findByPartNumber(partNumber);
		if(inventory==null) {
			throw new PartNumberDontExistException("404 Error: No Data found with the Part Number: " + partNumber);
		}
		return CompletableFuture.completedFuture(inventory);
	}

	@Override
	@Async
	@Transactional
	public CompletableFuture<Inventory> addStockForPartNumber(InventoryDTO inventoryDTO) throws RuntimeException{
		
		inventoryValidator.validateInventory(inventoryDTO);
		String partNumber = inventoryDTO.getPartNumber();
		int inventoryQuantity = inventoryDTO.getInventoryQuantity();
		
		Inventory inventory = inventoryRepo.findByPartNumber(partNumber);
		
		if(inventory == null) {
		inventory = inventoryConvertor.convertDTOtoEntity(inventoryDTO);
		}
		
		else {
		    int invQty  = inventory.getInventoryQuantity();
		    inventory.setInventoryQuantity(invQty+inventoryQuantity);
		}
		return CompletableFuture.completedFuture(inventoryRepo.save(inventory));
	}

	@Override
	@Async
	@Transactional
	public CompletableFuture<Inventory> deductStockForPartNumber(InventoryDTO inventoryDTO) throws RuntimeException{
		
		inventoryValidator.validateInventory(inventoryDTO);
		String partNumber = inventoryDTO.getPartNumber();
		int inventoryQuantity = inventoryDTO.getInventoryQuantity();
		
		Inventory inventory = inventoryRepo.findByPartNumber(partNumber);
		
		if(inventory == null) {
		      throw new PartNumberDontExistException("404 Error: No Data found with the Part Number: " + partNumber);
		}
		else {
		    int invQty  = inventory.getInventoryQuantity();
		    if(invQty>=inventoryQuantity) {
		    inventory.setInventoryQuantity(invQty-inventoryQuantity);
		    }
		    else {
		    	throw new InventoryNotSufficientException("500 Internal Server Error: Not enough Inventory for Stock out operation with the Part Number: " + partNumber);
		    }
		}
		return CompletableFuture.completedFuture(inventoryRepo.save(inventory));
		
	}
	
	
	

}
