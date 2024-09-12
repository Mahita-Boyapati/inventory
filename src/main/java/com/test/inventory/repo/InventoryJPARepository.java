package com.test.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.test.inventory.entity.Inventory;

import jakarta.persistence.LockModeType;

@Repository
public interface InventoryJPARepository  extends JpaRepository<Inventory,String>{
	@Lock(LockModeType.OPTIMISTIC)
	Inventory findByPartNumber(String partNumber);
}


