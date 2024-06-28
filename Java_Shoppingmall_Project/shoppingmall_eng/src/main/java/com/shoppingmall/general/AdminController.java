package com.shoppingmall.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingmall.item.Category;
import com.shoppingmall.item.Item;
import com.shoppingmall.item.ItemService;
import com.shoppingmall.users.Users;

@Controller
public class AdminController {
	@Autowired
	private ItemService itemService;

	@GetMapping("/addItem") 
	public String addItemForm(Model model, Users users) {		
		Category[] category = Category.values(); 
		model.addAttribute("category", category); 
		model.addAttribute("users", users);
		model.addAttribute("item", new Item());
		return "item/itemcreate";
	}

	@GetMapping("/deleteItem")
	public String deleteItem(@RequestParam("itemid") Optional<Long> itemid, Users users) {
		if (itemid.isEmpty())
			return "redirect:/shop";

		Optional<Item> item = itemService.findItemById(itemid.get());

		if (item.isEmpty())
			return "redirect:/shop";
		itemService.deleteItem(itemid.get());
		return "redirect:/shop";

	}

	@PostMapping("/addItem") 
	public String addItem(Model model, @ModelAttribute("item") Item formItem,
			@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("imageFile2") MultipartFile imageFile2) {
		try {
			if (imageFile.isEmpty() || imageFile2.isEmpty()) {
				throw new IllegalArgumentException("Choose File");
			}

			String uploadDir = "src/main/resources/static/images/imgfile/";

			String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
			String imagePath = uploadDir + imageName;
			Files.copy(imageFile.getInputStream(), Paths.get(imagePath));

			String imageName2 = UUID.randomUUID().toString() + "_" + imageFile2.getOriginalFilename();
			String imagePath2 = uploadDir + imageName2;
			Files.copy(imageFile2.getInputStream(), Paths.get(imagePath2));

			formItem.setImagePath("/images/imgfile/" + imageName);
			formItem.setImagePath2("/images/imgfile/" + imageName2);

			itemService.saveItem(formItem); 

			return "redirect:/shop";
		} catch (IOException e) {
			e.printStackTrace();
			return "errorPage";
		}
	}


	@GetMapping({ "/editItemForm", "/editItem" }) 

	public String editItemForm(@RequestParam("itemid") Long itemid, Model model, Users users) {

		Optional<Item> item = itemService.findItemById(itemid);

		if (item.isEmpty()) {

			return "redirect:/shop";

		}

		model.addAttribute("users", users);

		model.addAttribute("item", item.get());

		Category[] category = Category.values();

		model.addAttribute("category", category);

		return "item/itemedit";

	}

	@PostMapping("/editItem") 

	public String editItem(@ModelAttribute("item") Item formItem, @RequestParam("imageFile") MultipartFile imageFile,

			@RequestParam("imageFile2") MultipartFile imageFile2) {

		return processItemForm(null, formItem, imageFile, imageFile2);

	}



	private String processItemForm(Model model, Item formItem, MultipartFile imageFile, MultipartFile imageFile2) {

		try {

			if (imageFile.isEmpty() || imageFile2.isEmpty()) {

				throw new IllegalArgumentException("Choose File");

			}

			String uploadDir = "src/main/resources/static/images/imgfile/";

			// 이미지 업로드

			String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

			String imagePath = uploadDir + imageName;

			Files.copy(imageFile.getInputStream(), Paths.get(imagePath));

			formItem.setImagePath("/images/imgfile/" + imageName);

			String imageName2 = UUID.randomUUID().toString() + "_" + imageFile2.getOriginalFilename();

			String imagePath2 = uploadDir + imageName2;

			Files.copy(imageFile2.getInputStream(), Paths.get(imagePath2));

			formItem.setImagePath2("/images/imgfile/" + imageName2);

			itemService.saveItem(formItem); 

			return (model != null) ? "redirect:/shop" : "redirect:/detail?id=" + formItem.getItemid();

		} catch (IOException e) {

			e.printStackTrace();

			return "errorPage";

		}

	}

}
