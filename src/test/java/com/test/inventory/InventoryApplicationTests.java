package com.test.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.inventory.dto.InventoryDTO;
import com.test.inventory.entity.Inventory;
import com.test.inventory.exceptionhandler.InventoryNotSufficientException;
import com.test.inventory.exceptionhandler.ValidationException;
import com.test.inventory.service.InventoryService;

@SpringBootTest
class InventoryApplicationTests {

	@Autowired
	private InventoryService inventoryService;

	@Test
	void checkTest1() throws InterruptedException, ExecutionException {
		InventoryDTO inventoryOneDTO = new InventoryDTO("PART001", 10);
		Inventory inventoryOne = inventoryService.addStockForPartNumber(inventoryOneDTO).get();
		inventoryOne = inventoryService.getInventoryByPartNumber(inventoryOneDTO.getPartNumber()).get();
		assertThat(inventoryOne.getInventoryQuantity()).isEqualTo(10);
		
	}

	@Test
	void checkTest2() throws InterruptedException, ExecutionException {
		InventoryDTO inventoryTwoDTO = new InventoryDTO("PART002", 5);
		Inventory inventoryTwo = inventoryService.addStockForPartNumber(inventoryTwoDTO).get();
		inventoryTwo = inventoryService.getInventoryByPartNumber(inventoryTwoDTO.getPartNumber()).get();
		assertThat(inventoryTwo.getInventoryQuantity()).isEqualTo(5);
	}

	@Test
	void stockInTest1() throws InterruptedException, ExecutionException {
		InventoryDTO inventoryOneDTO = new InventoryDTO("PART001", 10);
		Inventory inventoryOne = inventoryService.addStockForPartNumber(inventoryOneDTO).get();
		assertThat(inventoryOne.getInventoryQuantity()).isEqualTo(20);
	}

	@Test
	void stockInTest2() throws InterruptedException, ExecutionException {
		InventoryDTO inventoryTwoDTO = new InventoryDTO("PART002", 5);
		Inventory inventoryTwo = inventoryService.addStockForPartNumber(inventoryTwoDTO).get();
		assertThat(inventoryTwo.getInventoryQuantity()).isEqualTo(10);
	}

	@Test
	void stockOutTest1() throws InterruptedException, ExecutionException {
		InventoryDTO inventoryOneDTO = new InventoryDTO("PART003", 10);
		Inventory inventoryOne = inventoryService.addStockForPartNumber(inventoryOneDTO).get();
		inventoryOne = inventoryService.deductStockForPartNumber(inventoryOneDTO).get();
		assertThat(inventoryOne.getInventoryQuantity()).isEqualTo(0);
	}

	@Test
	void stockOutTest2() throws InterruptedException, ExecutionException {
		InventoryDTO inventoryTwoDTO = new InventoryDTO("PART004", 5);
		Inventory inventoryTwo = inventoryService.addStockForPartNumber(inventoryTwoDTO).get();
		inventoryTwo = inventoryService.deductStockForPartNumber(inventoryTwoDTO).get();
		assertThat(inventoryTwo.getInventoryQuantity()).isEqualTo(0);
	}
	
    @Test
	void partNumberValidationExceptionTest() throws InterruptedException, ExecutionException {
		InventoryDTO inventoryOneDTO = new InventoryDTO("", 10);
		Throwable exception = assertThrows(ExecutionException.class, () -> inventoryService.addStockForPartNumber(inventoryOneDTO).get());
		assertEquals("com.test.inventory.exceptionhandler.ValidationException: 400 Bad Request:  Part Number should not be blank.", exception.getMessage());
	}
    
    @Test
	void inventoryQuantityValidationExceptionTest() throws InterruptedException, ExecutionException {
		InventoryDTO inventoryOneDTO = new InventoryDTO("PART005", -1);
		Throwable exception = assertThrows(ExecutionException.class, () -> inventoryService.addStockForPartNumber(inventoryOneDTO).get());
		assertEquals("com.test.inventory.exceptionhandler.ValidationException: 400 Bad Request:  Inventory Quantity should be greater than 0.", exception.getMessage());
	}
    
    @Test
   	void partNumberDontExistExceptionTest() throws InterruptedException, ExecutionException {
   		InventoryDTO inventoryOneDTO = new InventoryDTO("PART005", 10);
   		Throwable exception = assertThrows(ExecutionException.class, () -> inventoryService.deductStockForPartNumber(inventoryOneDTO).get());
   		assertEquals("com.test.inventory.exceptionhandler.PartNumberDontExistException: 404 Error: No Data found with the Part Number: PART005", exception.getMessage());
   	}
    
    @Test
   	void inventoryNotSufficientExceptionTest() throws InterruptedException, ExecutionException {
   		InventoryDTO inventoryOneDTO = new InventoryDTO("PART005", 10);
   		Inventory inventory = inventoryService.addStockForPartNumber(inventoryOneDTO).get();
  		InventoryDTO inventoryTwoDTO = new InventoryDTO("PART005", 15);
   		Throwable exception = assertThrows(ExecutionException.class, () -> inventoryService.deductStockForPartNumber(inventoryTwoDTO).get());
   		assertEquals("com.test.inventory.exceptionhandler.InventoryNotSufficientException: 500 Internal Server Error: Not enough Inventory for Stock out operation with the Part Number: PART005", exception.getMessage());
   	}

}
