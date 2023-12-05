package com.shoppingmall.review;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shoppingmall.item.Item;
import com.shoppingmall.item.ItemService;
import com.shoppingmall.users.UserService;
import com.shoppingmall.users.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@Validated
//@RestController
//@RequestMapping("/reviews")
public class ReviewController {

  @Autowired
  private ReviewService reviewService; 
  @Autowired
  private UserService userService;
  @Autowired
  private ItemService itemService;

	@Autowired
	private final ReviewRepository reviewRepository;

  public ReviewController(ReviewService reviewService,UserService userService,ItemService itemService,ReviewRepository reviewRepository) {
          this.reviewService = reviewService;
          this.userService =userService;
          this.itemService =itemService;
          this.reviewRepository =reviewRepository;
      }


  @PostMapping("/addReview")
  public String addReview(Model model,
                          @RequestParam(name = "itemid") String itemid,
                          @RequestParam(name = "contents") String contents,
                          @RequestParam("reviewImage") MultipartFile file,
                          RedirectAttributes attributes,
                          HttpSession session,
                          HttpServletRequest request,
                          HttpServletResponse response) {

      Users loggedInUser = (Users) session.getAttribute("loggedInUser");
      model.addAttribute("loggedInUser", loggedInUser);

      if (loggedInUser == null) {
       
          return "redirect:/login";
      }

      if (contents.isEmpty() || file.isEmpty()) {      
          attributes.addFlashAttribute("error", "Please provide both content and an image.");
          return "redirect:" + request.getHeader("Referer");
      }

      try {
          byte[] reviewImageBytes = file.getBytes();        
          Review newReview = reviewService.addReview(loggedInUser, Long.parseLong(itemid), contents, session, reviewImageBytes);
          session.setAttribute("itemid", itemid);
          System.out.println("저장할 객체 정보:" + newReview);

      } catch (NumberFormatException e) {      
          e.printStackTrace();
      } catch (Exception e) {    
          e.printStackTrace();
      }

      String referrer = request.getHeader("Referer");
      session.setAttribute("referrer", referrer);

      if (referrer != null && !referrer.isEmpty()) {
          try {
              response.sendRedirect(referrer);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }

      return "item/itemdetail :: #reviewListContainer";
  }

  @GetMapping("/reviews") 
  public String getAllReviews(Model model, HttpSession session) {
      Users loggedInUser = (Users) session.getAttribute("loggedInUser");

      if (loggedInUser != null && "admin@admin.com".equals(loggedInUser.getEmail())) {
          List<Review> reviews = reviewService.getAllReviews();
          model.addAttribute("reviews", reviews);

          return "item/itemdetail :: #reviewListContainer";
      } else {
          return "redirect:/access-denied"; 
      }
  }

  @GetMapping("/reviews/{itemid}") 
  public String getReviewsByItemId(@PathVariable Long itemid, Model model, HttpSession session, MultipartFile file) {
      Optional<Item> item = itemService.findItemById(itemid);
      List<Review> reviews = reviewService.getReviewsByItem(item);
      reviewService.updateDisplayNumbersByItemId(item);
      for (int i = 0; i < reviews.size(); i++) {
          reviews.get(i).setDisplayNumber(i + 1);
      }

      
      model.addAttribute("reviews", reviews);
      Users loggedInUser = (Users) session.getAttribute("loggedInUser");
      model.addAttribute("loggedInUser", loggedInUser);
      session.setAttribute("usersid", loggedInUser);
      return "item/itemdetail :: #reviewListContainer";
  }


  @GetMapping("/getImage/{rvid}")
  @ResponseBody
  public ResponseEntity<byte[]> getImage(@PathVariable Long rvid) {
	  Optional<Review> optionalReview = reviewRepository.findById(rvid);

      if (optionalReview.isPresent()) {
          Review review = optionalReview.get();
          byte[] imageBytes = review.getReviewImage();
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.IMAGE_PNG);
          headers.setContentType(MediaType.IMAGE_GIF);
          headers.setContentType(MediaType.IMAGE_JPEG); 
          return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
      } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }

  

@GetMapping("/updateReview/{rvid}")
public String showUpdateReviewForm(@PathVariable Long rvid, Model model, HttpSession session, MultipartFile file) {
   Review review = reviewService.getReviewById(rvid);
   if (review == null) {
		return "error"; 
	}
   model.addAttribute("review", review);
   
   Users loggedInUser = (Users) session.getAttribute("loggedInUser");
   session.setAttribute("usersid", loggedInUser);
   model.addAttribute("loggedInUser", loggedInUser);
   
  System.out.println(review);
   return "item/itemdetail :: #reviewListContainer"; 
}



@PostMapping("/updateReview")
public String updateReview(
        HttpSession session,
        @RequestParam Long rvid,
        @RequestParam Item itemid,
        @RequestParam Users usersid,
        @ModelAttribute("review") Review review,
        Model model,
        @RequestHeader(value = "referer", defaultValue = "/") String referer) {


    review.setRvid(rvid); 
    review.setItem(itemid); 
    review.setUsers(usersid); 

  
    Review existingReview = reviewService.getReviewById(rvid);
    if (existingReview != null && review.getReviewImage() == null) {
        review.setReviewImage(existingReview.getReviewImage());
    }

    Users loggedInUser = (Users) session.getAttribute("loggedInUser");
    model.addAttribute("loggedInUser", loggedInUser);
    session.setAttribute("usersid", loggedInUser);

    reviewService.updateReview(review);
    return "redirect:" + referer;
}



@GetMapping("/deleteReview/{rvid}")
public String deleteReview(Model model,
@PathVariable Long rvid,
 HttpSession session,
@RequestHeader(value = "referer", defaultValue = "/") String referer
) {
reviewService.deleteReview(rvid);
Users loggedInUser = (Users) session.getAttribute("loggedInUser");
model.addAttribute("loggedInUser", loggedInUser);
session.setAttribute("usersid", loggedInUser);


return "redirect:" + referer;
}



@PostMapping("/likeReview/{rvid}")
public String likeReview(@PathVariable Long rvid, HttpSession session) {
  Users loggedInUser = (Users) session.getAttribute("loggedInUser");
  if (loggedInUser != null) {
      Review review = reviewService.getReviewById(rvid);
      if (review != null) {
     
          review.setLikes(review.getLikes() + 1);
          reviewService.saveReview(review);
      }
  }

  return "redirect:/reviews"; 
}




@PostMapping("/updateLikes")
public ResponseEntity<String> updateLikes(@RequestParam Long rvid) {
    return ResponseEntity.ok("Likes updated successfully");
}




}