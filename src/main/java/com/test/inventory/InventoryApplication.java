package com.test.inventory;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableAsync
public class InventoryApplication extends AsyncConfigurerSupport{

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
	
	@Override
	public Executor getAsyncExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(10);
	    executor.setMaxPoolSize(20);
	    executor.setQueueCapacity(10);
	    executor.setThreadNamePrefix("InventoryManagement-");
	    executor.initialize();
	    return executor;
	}

}
