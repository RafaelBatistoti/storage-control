package com.projeto.estoque.interfaces;

import java.util.List;

import com.projeto.estoque.dto.Item;

public interface ItemService {
	List<Item> getAllItems();
	
	void saveItem(Item item);
	
	Item getById(String code);	
	
	
}
