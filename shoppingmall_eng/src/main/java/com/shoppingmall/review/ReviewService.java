package com.shoppingmall.review;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingmall.item.Item;
import com.shoppingmall.item.ItemRepository;
import com.shoppingmall.item.ItemService;
import com.shoppingmall.users.UserRepository;
import com.shoppingmall.users.Users;

import jakarta.servlet.http.HttpSession;

@Service
@Transactional
public class ReviewService {
	@Autowired
	private final ReviewRepository reviewRepository;
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final ItemRepository itemRepository;

	@Autowired
	private ItemService itemService;

	public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
			ItemRepository itemRepository) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;

	}

	@Transactional
	public Review addReview(Users users, Long itemid, String contents, HttpSession session, byte[] reviewImage) {
	    if (users == null) {	      
	        return null;
	    }

	    Optional<Item> item = itemRepository.findByItemid(itemid);
	    if (item.isEmpty()) {
	        return null;
	    }

	    if (contents == null || contents.trim().isEmpty() || reviewImage == null || reviewImage.length == 0) { 
	        return null;
	    }

	    try {
	        Review newReview = new Review();
	        newReview.setUsers(users);
	        newReview.setItem(item.get());
	        newReview.setContents(contents);
	        newReview.setReviewImage(reviewImage);

	        return reviewRepository.save(newReview);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error adding review", e);
	    }
	}


	@Autowired
	public List<Review> getAllReviews() {
		return reviewRepository.findAll();
	}

	public List<Review> getReviewsByItem(Optional<Item> item) {
	    if (item.isPresent()) {
	        Long itemid = item.get().getItemid();
	        return reviewRepository.findByItemOrderByRvidAsc(item);
	    } else {
	        return Collections.emptyList(); 
	    }
	}

	public List<Review> getReviewsByUsers(Users users) {
		return reviewRepository.findByUsers(users);
	}

	@Transactional
	public void deleteReview(Long rvid) {
		reviewRepository.deleteById(rvid);
	}
	@Transactional
	public Review getReviewById(Long rvid) {
		return reviewRepository.findById(rvid).orElse(null);
	}
	
	@Transactional
	public void updateReview(Review review) {
		if (reviewRepository.existsById(review.getRvid())) {
			reviewRepository.save(review);
		} else {
			
		}
	}
	
	
	public Long getReviewUserIdByReviewId(Long rvid) {
		 return reviewRepository.findById(rvid)
	                .map(review -> review.getUsers().getUsersid())
	                .orElse(null);
	}
	
	
	@Transactional
	public void updateDisplayNumbersByItemId(Optional<Item> item) {
	    List<Review> reviews = reviewRepository.findByItemOrderByRvidAsc(item);

	    for (int i = 0; i < reviews.size(); i++) {
	        reviewRepository.updateDisplayNumber(reviews.get(i).getRvid(), i + 1);
	    }
	}

	public void saveReview(Review newReview) {
		
		
	}
	

	public void increaseLikes(Long rvid) {
        Review review = reviewRepository.findById(rvid)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + rvid));

        review.setLikes(review.getLikes() + 1);
        reviewRepository.save(review);
    }
	
	
}
