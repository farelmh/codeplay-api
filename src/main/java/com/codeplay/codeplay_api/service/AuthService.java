package com.codeplay.codeplay_api.service;

import com.codeplay.codeplay_api.dto.ChangePasswordRequestDto;
import com.codeplay.codeplay_api.dto.LoginRequestDto;
import com.codeplay.codeplay_api.dto.LoginResponseDto;
import com.codeplay.codeplay_api.dto.RegisterRequestDto;
import com.codeplay.codeplay_api.entity.User;
import com.codeplay.codeplay_api.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(RegisterRequestDto req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            return new ResponseEntity<>("Email sudah terdaftar", HttpStatus.BAD_REQUEST);
        }

        String encPassword = passwordEncoder.encode(req.getPassword());

        User newUser = new User();
        newUser.setIdUser(UUID.randomUUID().toString());
        newUser.setNama(req.getNama());
        newUser.setEmail(req.getEmail());
        newUser.setPassword(encPassword);
        newUser.setNoHp(req.getNoHp());
        newUser.setRole(User.Role.user);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setCurrentEnergy(100);
        newUser.setMaxEnergy(100);
        newUser.setLastEnergyUpdate(LocalDateTime.now());
        newUser.setIsPremium(false);

        userRepository.save(newUser);

        return new ResponseEntity<>("Registrasi berhasil, Silahkan Login", HttpStatus.CREATED);
    }

    public ResponseEntity<LoginResponseDto> login(LoginRequestDto req) {

        return userRepository.findByEmail(req.getEmail())
            .map(user -> {

                boolean passwordMatches = passwordEncoder.matches(
                        req.getPassword(),
                        user.getPassword()
                );

                if (!passwordMatches) {
                    return new ResponseEntity<>(
                            new LoginResponseDto(null, "Password salah", null),
                            HttpStatus.UNAUTHORIZED
                    );
                }

                String[] name = user.getNama().split(" ");
                String shortName = name[0];

                LoginResponseDto success = new LoginResponseDto(
                        user.getIdUser(),
                        "Login berhasil",
                        shortName
                );

                return new ResponseEntity<>(success, HttpStatus.OK);

            })
            .orElseGet(() ->
                new ResponseEntity<>(
                        new LoginResponseDto(null, "User tidak ditemukan", null),
                        HttpStatus.NOT_FOUND
                )
            );
    }

    public ResponseEntity<String> changePassword (ChangePasswordRequestDto dto) {
        return userRepository.findByEmail(dto.getEmail())
            .map(user -> {
                String encPassword = passwordEncoder.encode(dto.getNewPassword());
                user.setPassword(encPassword);
                userRepository.save(user);
                return new ResponseEntity<>("Password berhasil diubah", HttpStatus.OK);
            })
            .orElseGet(() ->
                new ResponseEntity<>(
                        "User tidak ditemukan",
                        HttpStatus.NOT_FOUND
                )
            );
    }
}
