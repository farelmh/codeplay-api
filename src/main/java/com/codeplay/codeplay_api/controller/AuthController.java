package com.codeplay.codeplay_api.controller;

import com.codeplay.codeplay_api.entity.User;
import com.codeplay.codeplay_api.payload.RegisterRequest;
import com.codeplay.codeplay_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    // endpoint: POST https://yourdomain.com/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Cek apakah email sudah terdaftar
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Email sudah terdaftar", HttpStatus.BAD_REQUEST);
        }

        // Buat user baru
        User newUser = new User();
        newUser.setIdUser(UUID.randomUUID().toString());
        newUser.setNama(registerRequest.getNama());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(registerRequest.getPassword()); // Di aplikasi nyata, jangan lupa untuk mengenkripsi password!
        newUser.setNoHp(registerRequest.getNoHp());
        newUser.setRole("user");
        newUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(newUser);

        return new ResponseEntity<>("Registrasi berhasil, Silahkan Login", HttpStatus.CREATED);
    }
}