package com.codeplay.codeplay_api.service;

import com.codeplay.codeplay_api.dto.EnergyResponseDto;
import com.codeplay.codeplay_api.entity.User;
import com.codeplay.codeplay_api.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnergyService {

    private static final java.time.ZoneId JAKARTA_ZONE = java.time.ZoneId.of("Asia/Jakarta");

    private final UserRepository userRepository;
    private static final int ENERGY_REGEN_MINUTES = 5;

    public User regenerateEnergy(User user) {
        // 1. Jika energi sudah full, update timestamp ke sekarang agar timer tidak "basi"
        // dan return user langsung.
        if (user.getCurrentEnergy() >= user.getMaxEnergy()) {
            user.setLastEnergyUpdate(LocalDateTime.now());
            return userRepository.save(user);
        }

        LocalDateTime last = user.getLastEnergyUpdate();
        LocalDateTime now = LocalDateTime.now(JAKARTA_ZONE);

        long minutesPassed = java.time.Duration.between(last, now).toMinutes();
        long energyToRegen = minutesPassed / ENERGY_REGEN_MINUTES;

        if (energyToRegen > 0) {
            int currentEnergy = user.getCurrentEnergy();
            int maxEnergy = user.getMaxEnergy();

            // Hitung energi baru tapi jangan melebihi max
            int newEnergy = Math.min(
                maxEnergy,
                currentEnergy + (int) energyToRegen
            );

            user.setCurrentEnergy(newEnergy);

            // --- PERBAIKAN LOGIKA WAKTU ---
            if (newEnergy >= maxEnergy) {
                // Jika setelah ditambah jadi penuh, reset waktu ke 'now'
                user.setLastEnergyUpdate(now);
            } else {
                // PENTING: Jangan set ke 'now'.
                // Tambahkan waktu 'last' dengan jumlah menit yang SUDAH dikonversi jadi energi.
                // Contoh: Lewat 7 menit. Dapat 1 energi (5 menit).
                // Waktu update maju 5 menit. Sisa 2 menit tetap berjalan untuk energi berikutnya.
                long minutesUsed = energyToRegen * ENERGY_REGEN_MINUTES;
                user.setLastEnergyUpdate(last.plusMinutes(minutesUsed));
            }

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
