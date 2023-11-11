package com.projeto.estoque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.estoque.dto.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, String> {
	
}
