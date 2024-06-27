package com.vision.haksa.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.haksa.entitys.Workplace;
import com.vision.haksa.entitys.WorkplaceRepository;

import java.util.List;

@Service
public class WorkplaceService {
    @Autowired
    private WorkplaceRepository workplaceRepository;

    public Workplace saveWorkplace(Workplace workplace) {
        return workplaceRepository.save(workplace);
    }

    public List<Workplace> getAllWorkplaces() {
        return workplaceRepository.findAll();
    }

    public Workplace getWorkplaceById(String id) {
        return workplaceRepository.findById(id).orElse(null);
    }

    public void deleteWorkplace(String id) {
        workplaceRepository.deleteById(id);
    }
}