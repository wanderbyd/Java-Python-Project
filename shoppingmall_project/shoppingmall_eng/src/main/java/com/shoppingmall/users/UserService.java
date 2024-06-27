package com.shoppingmall.users;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingmall.cart.CartService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private CartService cartService;


	public Users createUser2(Users users) {
	    Users savedUser = userRepository.save(users);	    
	    cartService.createCart(savedUser.getUsersid());
	    return savedUser;
	}



	public Optional<Users> findUserById(Long usersid) {
		return userRepository.findByUsersid(usersid);
	}

	public List<Users> findAllUsers() {
		return userRepository.findAll();
	}

	public Users updateUser(Users users) {
		return userRepository.save(users);
	}

	public void deleteUser(Long usersid) {
		userRepository.deleteById(usersid);
	}

	public Optional<Users> findUserByEmail(String email) {
		if (email == null)
			throw new NullPointerException("EMail must not be null.");
		if (email.isEmpty())
			throw new NullPointerException("EMail must not be empty.");
		return userRepository.findFirstByEmail(email.toLowerCase().trim());
	}

	public boolean doesEmailAlreadyExists(final String email) {
		if (email == null)
			throw new NullPointerException("Email must not be null.");
		if (email.isEmpty())
			throw new NullPointerException("Email must not be empty.");
		return findUserByEmail(email).isPresent();
	}


	public static String hashPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Password cannot be null");
		}
		try {

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder(2 * hashedBytes.length);
			for (byte b : hashedBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			System.out.println(hexString);
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;

		}

	}


	public void updatePassword(final String newPassword, final Users users) {
		if (newPassword == null)
			throw new NullPointerException("NewPassword must not be null.");
		if (newPassword.isEmpty())
			throw new IllegalArgumentException("NewPassword must not be empty.");
		if (users == null)
			throw new NullPointerException("User must not be null.");
		String hashedPassword = hashPassword(newPassword);
		if (hashedPassword != null) {
			users.setHashedpassword(hashedPassword);
			saveUser(users);

		} else {

	

		}

	}



	public void rehashPassword(final String password, final Users users) {
		this.updatePassword(password, users);
	}
	public void saveUser(final Users users) {
		if (users == null)
			throw new NullPointerException("User must not be null");
		userRepository.save(users);

	}

}