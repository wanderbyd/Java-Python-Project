package com.vision.haksa.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.haksa.entitys.LectureRoom;
import com.vision.haksa.entitys.LectureRoomRepository;

import java.util.List;

@Service
public class LectureRoomService {

    @Autowired
    private LectureRoomRepository lectureRoomRepository;

    public LectureRoom saveLectureRoom(LectureRoom lectureRoom) {
        return lectureRoomRepository.save(lectureRoom);
    }

    public List<LectureRoom> getAllLectureRooms() {
        return lectureRoomRepository.findAll();
    }

    public LectureRoom getLectureRoomById(String roomNumber) {
        return lectureRoomRepository.findById(roomNumber).orElse(null);
    }

    public void deleteLectureRoom(String roomNumber) {
        lectureRoomRepository.deleteById(roomNumber);
    }
}