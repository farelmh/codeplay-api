package com.codeplay.codeplay_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TimeCheckController {

    @GetMapping("/api/check-time")
    public Map<String, String> checkTime() {
        Map<String, String> response = new HashMap<>();
        
        // Waktu Server Default (System)
        response.put("system_default_time", LocalDateTime.now().toString());
        response.put("system_timezone", ZoneId.systemDefault().toString());
        
        // Waktu Jakarta (Yang kita paksa)
        response.put("jakarta_time_now", LocalDateTime.now(ZoneId.of("Asia/Jakarta")).toString());
        
        return response;
    }
}