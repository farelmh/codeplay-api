package com.codeplay.codeplay_api.controller;

import com.codeplay.codeplay_api.dto.LoginRequestDto;
import com.codeplay.codeplay_api.dto.LoginResponseDto;
import com.codeplay.codeplay_api.dto.RegisterRequestDto;
import com.codeplay.codeplay_api.dto.VerifyOtpRequestDto;
import com.codeplay.codeplay_api.dto.ChangePasswordRequestDto;
import com.codeplay.codeplay_api.service.AuthService;
import com.codeplay.codeplay_api.service.EmailService;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequest) {
        return authService.changePassword(changePasswordRequest);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestHeader("email") String email) {
        String otp = String.valueOf(new Random().nextInt(999999));
        return emailService.sendOtpEmail(email, otp);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequestDto dto) {
        return emailService.verifyOtp(dto.getEmail(), dto.getInputOtp());
    }
}
