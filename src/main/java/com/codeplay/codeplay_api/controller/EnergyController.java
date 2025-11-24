package com.codeplay.codeplay_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeplay.codeplay_api.dto.EnergyResponseDto;
import com.codeplay.codeplay_api.service.EnergyService;
import com.codeplay.codeplay_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/energy")
@RequiredArgsConstructor
public class EnergyController {
    private final UserRepository userRepository;
    private final EnergyService energyService;

    @PostMapping("/consume")
    public ResponseEntity<EnergyResponseDto> consumeEnergy(
        @RequestHeader("idUser") String idUser
    ) {
        return ResponseEntity.ok(energyService.consumeEnergy(idUser));
    }
    
    @PostMapping("/regenerate")
    public ResponseEntity<EnergyResponseDto> regenerate(
            @RequestHeader("idUser") String idUser
    ) {
        var user = userRepository.findById(idUser).orElseThrow();

        var updated = energyService.regenerateEnergy(user);

        return ResponseEntity.ok(
            new EnergyResponseDto(
                true,
                "ENERGY_REGENERATED",
                updated.getCurrentEnergy(),
                updated.getMaxEnergy(),
                updated.getIsPremium(),
                updated.getLastEnergyUpdate()
            )
        );
    }

}
