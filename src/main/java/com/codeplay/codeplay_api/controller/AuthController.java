package com.codeplay.codeplay_api.controller;

import com.codeplay.codeplay_api.dto.LoginResponseDto;
import com.codeplay.codeplay_api.entity.User;
import com.codeplay.codeplay_api.payload.RegisterRequest;
import com.codeplay.codeplay_api.payload.LoginRequest;
import com.codeplay.codeplay_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // endpoint: POST https://yourdomain.com/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Cek apakah email sudah terdaftar
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Email sudah terdaftar", HttpStatus.BAD_REQUEST);
        }

        String encPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Buat user baru
        User newUser = new User();
        newUser.setIdUser(UUID.randomUUID().toString());
        newUser.setNama(registerRequest.getNama());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(encPassword);
        newUser.setNoHp(registerRequest.getNoHp());
        newUser.setRole(User.Role.user);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setCurrentEnergy(100);
        newUser.setMaxEnergy(100);
        newUser.setLastEnergyUpdate(LocalDateTime.now());
        newUser.setIsPremium(false);

        userRepository.save(newUser);

        return new ResponseEntity<>("Registrasi berhasil, Silahkan Login", HttpStatus.CREATED);
    }

    @PostMapping("/login")
public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequest loginRequest) {
    
    return userRepository.findByEmail(loginRequest.getEmail())
        .map(user -> {
            boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            
            if (passwordMatches) {
                LoginResponseDto successResponse = new LoginResponseDto(
                    user.getIdUser(),
                    "Login berhasil"
                );
                return new ResponseEntity<>(successResponse, HttpStatus.OK);
                
            } else {
                LoginResponseDto errorResponse = new LoginResponseDto(null, "Password salah");
                return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
            }
        })
        .orElseGet(() -> {
            LoginResponseDto notFoundResponse = new LoginResponseDto(null, "User tidak ditemukan");
            return new ResponseEntity<>(notFoundResponse, HttpStatus.NOT_FOUND);
        });
    }
}