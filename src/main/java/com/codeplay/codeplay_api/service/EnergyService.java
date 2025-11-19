package com.codeplay.codeplay_api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.codeplay.codeplay_api.dto.EnergyResponseDto;
import com.codeplay.codeplay_api.entity.User;
import com.codeplay.codeplay_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnergyService {
    private final UserRepository userRepository;
    private static final int ENERGY_REGEN_MINUTES = 5;

    public User regenerateEnergy(User user) {
        LocalDateTime last = user.getLastEnergyUpdate();
        LocalDateTime now = LocalDateTime.now();

        long minutePassed = java.time.Duration.between(last, now).toMinutes();
        long energyToRegen = minutePassed / ENERGY_REGEN_MINUTES;

        if (energyToRegen > 0) {
            int newEnergy = Math.min(
                user.getMaxEnergy(),
                user.getCurrentEnergy() + (int) energyToRegen
            );
            user.setCurrentEnergy(newEnergy);
            user.setLastEnergyUpdate(now);
            userRepository.save(user);
        }
        
        return user;
    }

    public EnergyResponseDto consumeEnergy(String idUser) {

        int amount = 10;

        User user = userRepository.findById(idUser).orElseThrow();
        regenerateEnergy(user);
        
        if (Boolean.TRUE.equals(user.getIsPremium())) {
            return new EnergyResponseDto(
                true,
                "PREMIUM_USER_NO_ENERGY_CONSUMPTION",
                user.getCurrentEnergy(),
                user.getMaxEnergy(),
                true
            );
        }

        if (user.getCurrentEnergy() < amount) {
        return new EnergyResponseDto(
                false,
                "ENERGY_NOT_ENOUGH",
                user.getCurrentEnergy(),
                user.getMaxEnergy(),
                false
        );
    }

        user.setCurrentEnergy(user.getCurrentEnergy() - amount);
        userRepository.save(user);

        return new EnergyResponseDto(
                true,
                "ENERGY_CONSUMED",
                user.getCurrentEnergy(),
                user.getMaxEnergy(),
                false
        );
    }
}
