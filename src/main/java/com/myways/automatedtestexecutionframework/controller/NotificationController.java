package com.myways.automatedtestexecutionframework.controller;
import com.myways.automatedtestexecutionframework.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody Map<String, String> body) {

        String subject = body.getOrDefault("subject", "Automated Notification");
        String message = body.getOrDefault("message", "");

        emailService.sendSimpleMail(subject, message);

        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "SENT");
        resp.put("timestamp", Instant.now().toString());

        return ResponseEntity.ok(resp);
    }
}

