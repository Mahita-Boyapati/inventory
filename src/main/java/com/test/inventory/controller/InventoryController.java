package com.test.inventory.controller;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.inventory.constants.InventoryConstants;
import com.test.inventory.dto.InventoryDTO;
import com.test.inventory.entity.Inventory;
import com.test.inventory.service.InventoryService;



@RestController
@RequestMapping(InventoryConstants.INVENTORY_URL_IDENTIFIER)
public class InventoryController {
	
	private Logger logger = LoggerFactory.getLogger(InventoryController.class);
	
	@Autowired
	InventoryService inventoryService;
	
	@GetMapping(InventoryConstants.CHECK)
	public ResponseEntity<Inventory>  getInventoryByPartNumber(@RequestParam("partNumber") String partNumber) throws InterruptedException, ExecutionException
	{
		CompletableFuture<Inventory> inventory = inventoryService.getInventoryByPartNumber(partNumber);
		return new ResponseEntity<>(inventory.get(),HttpStatus.OK);
		
	}
	
	@PostMapping(InventoryConstants.STOCK_IN)
	public ResponseEntity<Inventory> addStockForPartNumber(@RequestBody InventoryDTO inventoryDTO) throws InterruptedException, ExecutionException
	{
		
		CompletableFuture<Inventory> inventoryAfterStockIn=inventoryService.addStockForPartNumber(inventoryDTO);
		return new ResponseEntity<>(inventoryAfterStockIn.get(),HttpStatus.OK);
	}
	
	@PostMapping(InventoryConstants.STOCK_OUT)
	public ResponseEntity<Inventory> deductStockForPartNumber(@RequestBody InventoryDTO inventoryDTO) throws InterruptedException, ExecutionException
	{
		
		CompletableFuture<Inventory> inventoryAfterStockOut=inventoryService.deductStockForPartNumber(inventoryDTO);
		return new ResponseEntity<>(inventoryAfterStockOut.get(),HttpStatus.OK);
	}
	

}
