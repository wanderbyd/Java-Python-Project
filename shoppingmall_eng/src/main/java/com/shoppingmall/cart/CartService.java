package com.shoppingmall.cart;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingmall.item.Item;
import com.shoppingmall.item.ItemService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ItemService itemService;

	public void createCart(Long usersid) {
		if (usersid == null)
			throw new NullPointerException("usersid should not be null");
		cartRepository.save(new Cart(usersid));
	}

	public boolean hasUserCart(Long usersid) {
		if (usersid == null)
			throw new NullPointerException("usersid should not be null");
		for (Cart c : cartRepository.findAll()) {
			if (c.getUsersid().equals(usersid))
				return true;
		}
		return false;
	}

	public Optional<Cart> getUserCart(Long usersid) {
		if (usersid == null)
			throw new NullPointerException("UserId should not be null");
		return cartRepository.findById(usersid);
	}

	public void addItemToCart(Long usersid, Long itemid, Long quantity, HttpServletRequest request,
			HttpServletResponse response) {
		if (request == null) {
			throw new IllegalArgumentException("HttpServletRequest should not be null");
		}

		String contextPath = request.getContextPath();

		if (usersid == null) {
			throw new NullPointerException("usersid should not be null");
		}
		if (itemid == null) {
			throw new NullPointerException("itemid should not be null");
		}

		Optional<Cart> cartOptional = getUserCart(usersid);
		if (cartOptional.isEmpty()) {
			
			try {
				response.sendRedirect(contextPath + "/login"); 
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			return;
		}

		Cart cart = cartOptional.get();
		cart.getCartitem().put(itemid, quantity);
		cartRepository.save(cart);
	}


	public void removeItemFromCart(Long usersid, Long itemid, HttpServletRequest request,
			HttpServletResponse response) {
		if (request == null) {
			throw new IllegalArgumentException("HttpServletRequest should not be null");
		}

		String contextPath = request.getContextPath();

		if (usersid == null)
			throw new NullPointerException("UserId should not be null");
		if (itemid == null)
			throw new NullPointerException("itemId should not be null");

		Optional<Cart> cartOptional = getUserCart(usersid);
		if (cartOptional.isEmpty())
			throw new NullPointerException("Cart is empty");
		Cart cart = cartOptional.get();

		cart.getCartitem().remove(itemid);
		cartRepository.save(cart);
	}

	public void removeItemFromStock(Long cartid) {
		if (cartid == null)
			throw new NullPointerException("cartId should not be null");

		Map<Item, Long> cartitem = getItemsFromCart(cartid);
		for (Item i : cartitem.keySet()) {
			i.setStock((int) (i.getStock() - cartitem.get(i)));
			itemService.save(i);
		}
		resetCart(cartid);
	}

	
	public void resetCart(Long cartid) {
		if (cartid == null)
			throw new NullPointerException("cartId should not be null");
		Optional<Cart> cartOptional = cartRepository.findById(cartid);
		Cart cart = cartOptional.get();
		cart.setCartitem(new HashMap<>());
		 System.out.println("reset");
		cartRepository.save(cart);
	}


	@Transactional
	public Map<Item, Long> getItemsFromCart(Long cartid) {
		if (cartid == null)
			throw new NullPointerException("cartId should not be null");
		Map<Item, Long> cartitem = new HashMap<>();

		Optional<Cart> cartOptional = cartRepository.findById(cartid);
		if (cartOptional.isEmpty()) {
			throw new NullPointerException("CartOptional should not be null");
		}
		Cart cart = cartOptional.get();
		for (Long id : cart.getCartitem().keySet()) {
			Optional<Item> itemOptional = itemService.findItemById(id);
			if (itemOptional.isPresent()) {
				Item item = itemOptional.get();
				cartitem.put(item, cart.getCartitem().get(id));
			} else {
				
			}
		}

		return cartitem;
	}
	
	
	
	

	public Long calculateTotalPrice(Map<Item, Long> cartitem) {
		long price = 0L;
		for (Item i : cartitem.keySet()) {
			price += i.getPrice() * cartitem.get(i);
		}
		return price;
	}

	public void updateCartItemQuantity(Long cartid, Long itemid, Long newQuantity) {

		System.out.println("++++++++++++++++++++++++++++++++++++++++++ " + cartid + "," + itemid + "," + newQuantity);

		// Load the cart by cartId from the repository
		Optional<Cart> cartOptional = cartRepository.findById(cartid);

		cartOptional.ifPresent(cart -> {
			Map<Long, Long> cartitem = cart.getCartitem();

			if (cartitem.containsKey(itemid)) {
				cartitem.put(itemid, newQuantity);
				cart.setCartitem(cartitem);
				System.out.println(cart.toString());
				cartRepository.save(cart);// 디비갱신
			}
		});
	}
	

	public Cart getCartByCartid(Long cartid) {
		return null;
	}
}