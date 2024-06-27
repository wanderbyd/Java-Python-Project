package com.shoppingmall.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public Item createItem(Item item) {
		return itemRepository.save(item);
	}

	public Optional<Item> findItemById(Long itemid) {
		return itemRepository.findByItemid(itemid);
	}


	public List<Item> findAllItems() {
		return itemRepository.findAll();
	}


	public Item updateItem(Item item) {
		return itemRepository.save(item);
	}


	public void deleteItem(Long itemid) {
		itemRepository.deleteById(itemid);
	}


	public List<Item> findItemsByPriceRange(Long min, Long max) {
		return itemRepository.findByPriceBetween(min, max);
	}


	public List<Item> searchItemsByKeyword(String keyword) {
		return itemRepository.findByKeywordLike(keyword);
	}


	public List<Item> searchItemsByCategory(Category category) {
		return itemRepository.findByCategory(category);
	}

	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public Item save(Item item) {
		return itemRepository.save(item);
	}


	public void updateItemWithImage(Item item, String imagePath) {
		item.setImagePath(imagePath);
		updateItem(item);
	}


	public Item saveItem(Item item) {
		return itemRepository.save(item);
	}

}