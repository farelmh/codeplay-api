package com.codeplay.codeplay_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeplay.codeplay_api.dto.SubscriptionPlansDto;
import com.codeplay.codeplay_api.repository.SubscriptionPlansRepository;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/premium")
public class PremiumController {
    @Autowired
    private SubscriptionPlansRepository repo;

    @GetMapping("/list")
    public ResponseEntity<List<SubscriptionPlansDto>> getPremiumList() {
        List<SubscriptionPlansDto> plans = repo.findAll().stream().map(plan -> {
            SubscriptionPlansDto dto = new SubscriptionPlansDto();
            dto.setIdPlan(plan.getIdPlan());
            dto.setNama(plan.getNama());
            dto.setDeskripsi(plan.getDeskripsi());
            dto.setDurasiBulan(plan.getDurasiBulan());
            dto.setHarga(plan.getHarga());
            return dto;
        }).toList();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
