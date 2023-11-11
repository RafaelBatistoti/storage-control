package com.projeto.estoque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.estoque.dto.Item;
import com.projeto.estoque.interfaces.ItemRepo;
import com.projeto.estoque.interfaces.ItemService;

@Service
public class ItemServ implements ItemService{

	@Autowired
	private ItemRepo itemRepo;
	
	@Override
	public List<Item> getAllItems() {
		return itemRepo.findAll();
	}

	@Override
	public void saveItem(Item item) {
		this.itemRepo.save(item);
	}

	@Override
	public Item  getById(String code) {
		Optional<Item> optional = itemRepo.findById(code);
		Item employee = null;
        if (optional.isPresent())
            employee = optional.get();
        else
            throw new RuntimeException("Employee not found for id");
        return employee;
	}


			
}
