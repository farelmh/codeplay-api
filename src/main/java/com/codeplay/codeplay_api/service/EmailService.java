package com.codeplay.codeplay_api.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.codeplay.codeplay_api.repository.UserRepository;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository repo;

    private Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public ResponseEntity<String> sendOtpEmail(String toEmail, String otp) {
        if (!repo.existsByEmail(toEmail)) {
            return new ResponseEntity<>("Email tidak terdaftar", HttpStatus.BAD_REQUEST);
        }

        otpStorage.put(toEmail, otp);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("farelhabibie01@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Kode OTP Reset Password");
        message.setText("Jangan Bagikan Kode ini ke orang lain:\n\n" + otp);
        mailSender.send(message);
        return new ResponseEntity<>("OTP telah dikirim ke email", HttpStatus.OK);
    }

    public ResponseEntity<String> verifyOtp(String email, String inputOtp) {
        if (!otpStorage.containsKey(email)) {
            return new ResponseEntity<>("OTP tidak ditemukan untuk email ini", HttpStatus.BAD_REQUEST);
        }
        String storedOtp = otpStorage.get(email);
        if (storedOtp.equals(inputOtp)) {
            otpStorage.remove(email);
            return new ResponseEntity<>("OTP valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("OTP tidak valid", HttpStatus.BAD_REQUEST);
        }
    }
}
