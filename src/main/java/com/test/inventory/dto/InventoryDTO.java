package com.test.inventory.dto;

public class InventoryDTO {
    private long id;
	private String partNumber;
	private int inventoryQuantity;
	public InventoryDTO() {
	}
	
	public InventoryDTO(String partNumber, int inventoryQuantity) {
		super();
		this.partNumber = partNumber;
		this.inventoryQuantity = inventoryQuantity;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public int getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(int inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	
}
