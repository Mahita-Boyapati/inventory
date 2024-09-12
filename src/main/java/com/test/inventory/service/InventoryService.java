package com.test.inventory.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.test.inventory.dto.InventoryDTO;
import com.test.inventory.entity.Inventory;

@Service
public interface InventoryService {
	
	public CompletableFuture<Inventory> getInventoryByPartNumber(String partNumber);
	public CompletableFuture<Inventory> addStockForPartNumber(InventoryDTO inventoryDTO);
	public CompletableFuture<Inventory> deductStockForPartNumber(InventoryDTO inventoryDTO);

}
