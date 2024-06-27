package com.example.jpatest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class HomebookService {
    private final HomebookRepository homebookRepository;
    @Autowired
    public HomebookService(HomebookRepository homebookRepository) {
        this.homebookRepository = homebookRepository;
    }

    // Create
    public Homebook createHomebook(Homebook homebook) {
        return homebookRepository.save(homebook);
    }

    // Read
    public List<Homebook> getAllHomebooks() {
        return homebookRepository.findAll();
    }

    public Homebook getHomebookById(Long serialNo) {
        return homebookRepository.findById(serialNo)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found with id " + serialNo));
    }
    
    // Update
    public Homebook updateHomebook(Long serialNo, Homebook updatedHomebook) {
        if (homebookRepository.existsById(serialNo)) {
            updatedHomebook.setSerialNo(serialNo);
            return homebookRepository.save(updatedHomebook);
        } else {
            throw new IllegalArgumentException("Homebook with serialNo " + serialNo + " not found.");
        }
    }

    // Delete
    public void deleteHomebook(Long serialNo) {
        homebookRepository.deleteById(serialNo);
    }



}
