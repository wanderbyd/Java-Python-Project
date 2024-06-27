package com.vision.haksa.controller;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vision.haksa.entitys.LectureRoom;
import com.vision.haksa.services.LectureRoomService;

@Controller
@RequestMapping("/lecturerooms")
public class LectureRoomController {

    @Autowired
    private LectureRoomService lectureRoomService;

    @GetMapping("")
    public String listLectureRooms(Model model, @RequestParam(name = "sortBy", required = false) String sortBy) {
        List<LectureRoom> lectureRooms = lectureRoomService.getAllLectureRooms();
      
        if (sortBy == null || sortBy.isEmpty()) {
             model.addAttribute("lectureRooms", lectureRooms);
             return "lectureroom/list"; // list.html과 연동
           }
        
        Comparator<LectureRoom> comparator = null;

	    switch (sortBy) {
	        case "roomnumber":
	            comparator = Comparator.comparing(lectureroom -> lectureroom.getRoomnumber(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "roomname":
	            comparator = Comparator.comparing(lectureroom -> lectureroom.getRoomname(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "seatcount":
	            comparator = Comparator.comparing(lectureroom -> lectureroom.getSeatcount(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        default:
	            // No sorting, use the original order
	            break;
	    }

	    if (comparator != null) {
	        lectureRooms = lectureRooms.stream()
	            .sorted(comparator)
	            .collect(Collectors.toList());
	    }

	    model.addAttribute("lectureRooms", lectureRooms);
	    return "lectureroom/list";
    }

    @GetMapping("/detail/{roomnumber}")
    public String viewLectureRoom(@PathVariable String roomnumber, Model model) {
        LectureRoom lectureRoom = lectureRoomService.getLectureRoomById(roomnumber);
        if (lectureRoom == null) {
            return "error"; // 에러 페이지로 리다이렉트 또는 처리
        }
        model.addAttribute("lectureRoom", lectureRoom);
        return "lectureroom/detail"; // detail.html과 연동
    }

    @GetMapping("/edit/{roomnumber}")
    public String editLectureRoomForm(@PathVariable String roomnumber, Model model) {
        LectureRoom lectureRoom = lectureRoomService.getLectureRoomById(roomnumber);
        if (lectureRoom == null) {
            return "error"; // 에러 페이지로 리다이렉트 또는 처리
        }
        model.addAttribute("lectureRoom", lectureRoom);
        return "lectureroom/edit"; // edit.html과 연동
    }
    @PostMapping("/update/{roomnumber}")
    public String updateLectureRoom(@PathVariable String roomnumber, @ModelAttribute LectureRoom updatedLectureRoom,Model model) {
     
	    try {
	    	   LectureRoom existingLectureRoom = lectureRoomService.getLectureRoomById(roomnumber);
	           if (existingLectureRoom == null) {
	               return "error"; // 에러 페이지로 리다이렉트 또는 처리
	           }
	           // 기존 강의실 정보를 업데이트
	           existingLectureRoom.setRoomname(updatedLectureRoom.getRoomname());
	           existingLectureRoom.setSeatcount(updatedLectureRoom.getSeatcount());
	           // 다른 필드도 필요한 대로 업데이트
	           lectureRoomService.saveLectureRoom(existingLectureRoom); // 업데이트된 정보를 저장
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success";  
	}
    

    @GetMapping("/create")
    public String createLectureRoomForm(Model model) {
        model.addAttribute("lectureRoom", new LectureRoom());
        return "lectureroom/form"; // form.html과 연동
    }

    @PostMapping("/create")
    public String saveLectureRoom(@ModelAttribute LectureRoom lectureRoom,Model model) {
       
	    try {
	    	 lectureRoomService.saveLectureRoom(lectureRoom);
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success";  
	}
    
    
    @PostMapping("/delete/{roomnumber}")
    public String deleteLectureroom(@PathVariable String roomnumber,Model model) {
    	
	    try {
	    	lectureRoomService.deleteLectureRoom(roomnumber);
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success";  
	}
    
    
}